package com.puissance4;

import java.util.ArrayList;

public class App {

    static int nbrColumns = 8; //Nombre de colonnes
    static int nbrLines = 6; //Nombre de lignes
    static int nbrOfPlayer = 2; //Nombre de joueur
    static int nbrToWin = 4; //nombre de pièces alignés pour gagner

    static boolean isEndGame = false;

    static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();
    static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args)
    {
        setProperties(); //Mise en place du tableau, ainsi que des joueurs.
        round(); // On commence à faire les tours.
    }

    public static void setProperties() {

        //Tableau de tableau de pièce.
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
        }

        //Tableau de joueurs.
        for (int i = 0; i < nbrOfPlayer; i++) {
            Player firstPlayerBuffer = new Player(Interface.getChoiceOfName(),Interface.getChoiceOfColor()); //remplacer argument par variable qui est choisi avec l'interface.
            players.add(firstPlayerBuffer);
        } 
    
        Interface.display();
    }

    public static void round() {
        while(!isEndGame) {

            for(Player player : players) {
                
                System.out.println("C'est au tour de " + player.name);
                player.toAddPiece(); //ajout d'une pièce qui sera choisi par le joueur.
                Interface.display();//Affichage de la zone de jeu.
                
                // Si endGameTest() renvoie "true", alors c'est la fin de la partie
                isEndGame = player.endGameTest();

                //Sortir de la boucle de joueurs.
                if(isEndGame) {
                    break;
                }
            }
        }
    }
}