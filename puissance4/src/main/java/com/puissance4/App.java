package com.puissance4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    static int nbrColumns = 8; //Nombre de colonnes
    static int nbrLines = 6; //Nombre de lignes
    static int nbrOfPlayer = 2; //Nombre de joueur
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static boolean isNetworking = false; //Pour savoir si on est en réseau.
    static Player myPlayer = null; //l'objet player qui correspond au joueur ayant lancé le programme. (pour le réseau)
    static String firstRoundState; //Variable ayant l'état du premier tour sous forme de chaîne de caractères.

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {

        setGrid(); //Mise en place du tableau.

        if(Interface.isNetwork()){

            //definir un communicator
            Communicator.comm = new Communicator();

            isNetworking = true;
            
            setupNetworkGame(); //Mise en place d'une partie en réseau
        
        } else {
            isNetworking = false;
            setPlayers(); //Mise en place du tableau de joueurs.
            setStartingPlayer(generateRandomNbr()); //Choix aléatoire du premier joueur.
            firstRoundState = "first round finished";
        }

        round(); // On commence à faire les tours.
    }

    public static void setupNetworkGame() {

        boolean isSameImplementation = Interface.isSameImplementation(); //Vérification de si c'est la même version/implémentation du jeu.
        
        if(Communicator.isServing()) {
            Communicator.comm.accept();

            if(isSameImplementation) { // Si c'est la même version/implémentation du jeu alors on peut demander le nom et la couleur.
                readPlayerProperties(); 
                writePlayerProperties();
            } else { //Sinon on met des valeurs par défauts.
                setPlayers("Joueur client", ColorOfPieces.GREEN);
                myPlayer = setPlayers("Joueur serveur (vous)", ColorOfPieces.RED);
            }

            networkStartingPlayer(generateRandomNbr()); //Choix aléatoire du premier joueur.

        } else {

            try {
                Communicator.comm.connect(Interface.getChoiceOfAdress()); //connexion à une adresse donnée.

                if(isSameImplementation) {
                    writePlayerProperties();
                    readPlayerProperties();
                } else {
                    myPlayer = setPlayers("Joueur client (vous)", ColorOfPieces.RED);
                    setPlayers("Joueur serveur", ColorOfPieces.GREEN);
                }

                firstRoundState = Communicator.comm.read();
                
                if (firstRoundState.equals("your turn")) { //Si c'est au tour du client alors
                    setStartingPlayer(0); //On met le premier joueur (le joueur Client)
                } else {
                    setStartingPlayer(1); //On met le premier joueur (le joueur Serveur)
                }

            } catch(IOException e) {
                System.err.println("error" + e.getMessage());
            } 
        }
    }
    
    //fonction permettant de mettre en place la grille de jeu
    public static void setGrid() {
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
        }
    }

    //fonction permettant de mettre en place le tableau de joueur
    public static void setPlayers() {
        for (int i = 0; i < nbrOfPlayer; i++) {
            Player firstPlayerBuffer = new Player(Interface.getChoiceOfName(),Interface.getChoiceOfColor()); //remplacer argument par variable qui est choisi avec l'interface.
            players.add(firstPlayerBuffer);
        }
    
    }

    //fonction permettant de mettre en place un joueur dans le tableau
    public static Player setPlayers(String name, ColorOfPieces color) {
        Player playerBuffer = new Player(name, color);
        players.add(playerBuffer);

        return playerBuffer;
    }

    //fonction permettant de choisir aléatoirement le premier joueur, et envoie un message au Client.
    public static void networkStartingPlayer(int randomNbr) {

        setStartingPlayer(randomNbr); //réorganise le tableau de joueur en fonction du joueur qui commence
        
        if(isNetworking) {
            try {
                if(randomNbr == 0) { //Si le joueur qui commence est le Client alors
                    Communicator.comm.write("your turn");
                } 
                
                // Pour le serveur, on lui met que le premier round est fini pour lui. Même s'il n'a pas encore réellement fini.
                firstRoundState = "first round finished"; 

            } catch(IOException e) {
                System.err.println("IOException : " + e.getMessage());
            } 
        }
    }

    //fonction qui réorganise le tableau de joueur en fonction du joueur qui commence
    public static void setStartingPlayer(int indexOfPlayer) {
        Collections.swap(players, indexOfPlayer, 0);
    }

    //fonction qui génère un nombre aléatoire
    public static int generateRandomNbr() {
        int randomNbr = (int)(Math.random() * (nbrOfPlayer));

        return randomNbr;
    }

    //fonction qui va lire les propriétés du joueur d'en face
    public static void readPlayerProperties() {

        String name = Communicator.comm.read();
        String color = Communicator.comm.read();

        ColorOfPieces colorOfTeam = ColorOfPieces.valueOf(color);


        setPlayers(name, colorOfTeam);
    }

    //fonction qui va envoyé les propriétés du joueur lançant l'implémentation.
    public static void writePlayerProperties() {

        String name = Interface.getChoiceOfName();
        ColorOfPieces color = Interface.getChoiceOfColor();

        try{
            Communicator.comm.write(name);
            Communicator.comm.write(color.toString());
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }
         
        myPlayer = setPlayers(name, color); //On définit myPlayer, variable de référence du joueur qui a lancé l'implémentation.
    }

    //fonction qui contient la boucle de lancement des tours.
    public static void round() {
        while(!isEndGame) { //tant que le jeu n'est pas fini.

            for(Player player : players) { //tour par tour pour les joueurs
                //affichage
                // Interface.display(); 
                System.out.println("C'est au tour de " + player.name);

                //si c'est le premier tour (ne rentrera que le joueur Client)
                if(!firstRoundState.equals("first round finished")) {
                    firstRound(player); //On ajoute une pièce qui sera choisi au premier tour.
                } else { //sinon
                    player.toAddPiece(player.getChoosenColumn()); //on ajoute une pièce qui sera choisi (par l'un ou par l'autre)
                }

                isEndGame = player.endGameTest();

                //Sortir de la boucle de joueurs.
                if(isEndGame) {
                    if(isNetworking) {
                        Communicator.comm.close(); //Fermé proprement la connexion.
                    }

                    break; //on arrête la boucle.
                }
            }
        }
    }

    //fonction permettant l'ajout d'une pièce avec une colonne choisi au premier tour.
    public static void firstRound(Player player) {
        if(!firstRoundState.equals("your turn") && !firstRoundState.equals("Your turn")) { //Si ce n'est pas ton tour, alors
            player.toAddPiece(firstRoundState); //ajout de la pièce choisi par le joueur Serveur
        } else {
            player.toAddPiece(player.getChoosenColumn()); //ajout d'une pièce choisi par le Client
        }

        firstRoundState = "first round finished"; //le premier tour est fini.
    }
    
}