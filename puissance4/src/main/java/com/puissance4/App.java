package com.puissance4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class App extends Application {

    static Stage mainWindow = null;
    static Stage confirmationStage = null;
    static Scene mainScene = null;

    static int nbrColumns = 8; //Nombre de colonnes
    static int nbrLines = 6; //Nombre de lignes
    static int nbrOfPlayer = 2; //Nombre de joueur
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static boolean isNetworking = false; //Pour savoir si on est en réseau.
    static boolean isSameImplementation;
    static Player myPlayer = null; //l'objet player qui correspond au joueur ayant lancé le programme. (pour le réseau)
    static String firstRoundState; //Variable ayant l'état du premier tour sous forme de chaîne de caractères.

    static String name;
    static ColorOfPieces color;

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {

        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        this.mainWindow = window;
        mainWindow.setTitle("Yssance4");

        mainWindow.centerOnScreen();

        setGrid(); //Mise en place du tableau.

        Interface.isNetwork();
    }

    
    public static void setupNetworkGame(boolean isServing) {
        
        if(isServing) {
            Communicator.comm.accept();

            App.serverSetup();

        } else {
            Interface.getChoiceOfAdress();
        }
    }

    public static void serverSetup() {
        if(isSameImplementation) { // Si c'est la même version/implémentation du jeu alors on peut demander le nom et la couleur.
            readPlayerProperties(); 
            writePlayerProperties();
        } else { //Sinon on met des valeurs par défauts.
            setPlayers("Joueur client", ColorOfPieces.GREEN);
            myPlayer = setPlayers("Joueur serveur (vous)", ColorOfPieces.RED);
        }

        networkStartingPlayer(generateRandomNbr()); //Choix aléatoire du premier joueur.
    }

    public static void clientSetup() {
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
    }

    //fonction permettant de mettre en place la grille de jeu
    public static void setGrid() {
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
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

        Interface.getChoicesOfPlayer();
            
        myPlayer = setPlayers(name, color); //On définit myPlayer, variable de référence du joueur qui a lancé l'implémentation.
    }

    public static int turnedPlayer(int indexOfPlayer) {
        if (indexOfPlayer == 0) {
            return 1;
        } else {
            return 0;
        }
    }   
}