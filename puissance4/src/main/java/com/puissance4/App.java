package com.puissance4;

import java.util.*;
public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;
    static int nbrOfPlayer = 2;

    static boolean isEndGame = false;

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {
        setProperties();
        round();
    }

    public static void setProperties() {
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
        }

        for (int i = 0; i < nbrOfPlayer; i++) {
            Player playerBuffer = new Player(ColorOfPieces.RED, "name"); //remplacer argument par variable qui est choisi avec l'interface.
            players.add(playerBuffer);
        }      
    }

    public static void round() {
        while(!isEndGame) {

            for(Player player : players) {
                // players.get(i) ?
                System.out.println("C'est au tour de " + player.name);
                player.toAddPiece();
                // Interface.display();
            }
        }
    }
}