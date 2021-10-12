package com.puissance4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;
    static int nbrOfPlayer = 2;
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static boolean isNetworking = false; //Pour savoir si on est en réseau.
    static Player myPlayer = null; //l'objet player qui correspond au joueur ayant lancé le programme. (pour le réseau)

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {

        setGrid();

        if(Interface.isNetwork()){

            //definir un communicator
            Communicator.comm = new Communicator();

            isNetworking = true;
            
            setupNetworkGame();
        
        } else {
            isNetworking = false;
            setPlayers();
            randomStartingPlayer(generateRandomNbr());
        }

        round();
    }

    public static void setupNetworkGame() {

        boolean isSameImplementation = Interface.isSameImplementation();
        
        if(Communicator.isServing()) {
            Communicator.comm.accept();

            if(isSameImplementation) {
                readPlayerProperties();
                writePlayerProperties();
            } else {
                setPlayers("Joueur client", ColorOfPieces.GREEN);
                myPlayer = setPlayers("Joueur serveur (vous)", ColorOfPieces.RED);
            }

            randomStartingPlayer(generateRandomNbr());

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

                String forRandomStarting = Communicator.comm.read();

                if(!forRandomStarting.equals("your turn") && !forRandomStarting.equals("Your turn")) {
                    players.get(1).toAddPiece(forRandomStarting);
                }

                // //vérification que c'est bien un nombre
                // int randomNbr = Integer.parseInt(Communicator.comm.read()); 

                // randomStartingPlayer(randomNbr);

            } catch(IOException e) {
                System.err.println("error" + e.getMessage());
            } 
            // catch(NumberFormatException e) {
            //     System.err.println("error" + e.getMessage());
            // }
        }
    }
    
    public static void setGrid() {
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
        }
    }

    public static void setPlayers() {
        for (int i = 0; i < nbrOfPlayer; i++) {
            Player firstPlayerBuffer = new Player(Interface.getChoiceOfName(),Interface.getChoiceOfColor()); //remplacer argument par variable qui est choisi avec l'interface.
            players.add(firstPlayerBuffer);
        }
    
    }

    public static Player setPlayers(String name, ColorOfPieces color) {
        Player playerBuffer = new Player(name, color);
        players.add(playerBuffer);

        return playerBuffer;
    }

    public static void randomStartingPlayer(int randomNbr) {

        Collections.swap(players, randomNbr, 0);
        
        if(isNetworking) {
            try {
                if(randomNbr == 0) {
                    Communicator.comm.write("your turn");
                } else {
                    players.get(1).toAddPiece(players.get(1).getChoosenColumn());
                }

            } catch(IOException e) {
                System.err.println("IOException : " + e.getMessage());
            } 
        }
    }

    public static int generateRandomNbr() {
        int randomNbr = (int)(Math.random() * (nbrOfPlayer));

        return randomNbr;
    }

    public static void readPlayerProperties() {

        String name = Communicator.comm.read();
        String color = Communicator.comm.read();

        ColorOfPieces colorOfTeam = ColorOfPieces.valueOf(color);


        setPlayers(name, colorOfTeam);
    }

    public static void writePlayerProperties() {

        String name = Interface.getChoiceOfName();
        ColorOfPieces color = Interface.getChoiceOfColor();

        try{
            Communicator.comm.write(name);
            Communicator.comm.write(color.toString());
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }
         
        myPlayer = setPlayers(name, color);
    }

    public static void round() {
        while(!isEndGame) {

            for(Player player : players) {
                // players.get(i) ?
                Interface.display();
                System.out.println("C'est au tour de " + player.name);
                player.toAddPiece(player.getChoosenColumn());

                isEndGame = player.endGameTest();

                if(isEndGame) {
                    // Interface.display();
                    Communicator.comm.close();
                    break;
                }
            }
        }
        //quand on sort de la boucle, il faut regarder qui a gagné (ou s'il y a égalité)
    }
    
}