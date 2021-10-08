package com.puissance4;

import java.io.IOException;
import java.util.ArrayList;

public class App {

    static int nbrColumns = 4;
    static int nbrLines = 2;
    static int nbrOfPlayer = 2;
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static boolean isNetworking = false; //Pour savoir si on est en réseau.
    static Player myPlayer = null; //l'objet player qui correspond au joueur ayant lancé le programme. (pour le réseau)

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {

        if(Interface.isNetwork()){

            //definir un communicator
            Communicator.comm = new Communicator();

            isNetworking = true;
            
            setupNetworkGame();
        
        } else {
            isNetworking = false;
            setPlayers();
        }

        // setProperties();
        setGrid();
        // Interface.display();
        round();
    }

    public static void setupNetworkGame() {
        
        if(Communicator.isServing()) {
            Communicator.comm.accept();
            readPlayerProperties();
            writePlayerProperties();
        } else {

            try {
                Communicator.comm.connect(Interface.getChoiceOfAdress()); //connexion à une adresse donnée.
                writePlayerProperties();
                readPlayerProperties();

            } catch(IOException e) {
                System.err.println("error" + e.getMessage());
            }
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
                player.toAddPiece();

                isEndGame = player.endGameTest();

                if(isEndGame) {
                    break;
                }
            }
        }
        //quand on sort de la boucle, il faut regarder qui a gagné (ou s'il y a égalité)
    }
}