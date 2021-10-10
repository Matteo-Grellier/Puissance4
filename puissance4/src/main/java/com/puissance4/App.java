package com.puissance4;

import java.util.ArrayList;

public class App {

    static int nbrColumns = 6;
    static int nbrLines = 4;
    static int nbrOfPlayer = 2;
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

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
            Player firstPlayerBuffer = new Player(Interface.getChoiceOfName(),Interface.getChoiceOfColor()); //remplacer argument par variable qui est choisi avec l'interface.
            players.add(firstPlayerBuffer);
            // Player secondPlayerBuffer = new Player(ColorOfPieces.GREEN, "Math"); //remplacer argument par variable qui est choisi avec l'interface.
            // players.add(secondPlayerBuffer);
        } 
    
    }

    public static void round() {
        while(!isEndGame) {

            for(Player player : players) {
                // players.get(i) ?
                System.out.println("C'est au tour de " + player.name);
                // player.toAddPiece();
                
                isEndGame = player.endGameTest();

                if(isEndGame) {
                    break;
                }
            }
        }
        //quand on sort de la boucle, il faut regarder qui a gagné (ou s'il y a égalité)
    }
}