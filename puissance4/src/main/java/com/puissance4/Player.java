package com.puissance4;


import java.io.IOException;
import java.util.ArrayList;

enum ColorOfPieces {
    RED,
    GREEN
}

public class Player {

    ColorOfPieces teamColor;
    String name;
    
    boolean isWinner = false;
    private Piece lastPiece = null;

    public Player(String name, ColorOfPieces teamColor) {
        this.teamColor = teamColor;
        this.name = name;
    }

    //fonction récupérant le choix du joueur adverse ou du joueur actuel.
    public String getChoosenColumn() {

        String choosenColumn;
        
        if(App.isNetworking && App.myPlayer.equals(this)) { //Si on joue en réseau et que c'est notre tour alors
            choosenColumn = Interface.getChoiceOfColumn(); // fonction de l'interface qui return le choix du joueur
            
            try {
                Communicator.comm.write(choosenColumn);
            } catch(IOException e) {
                System.err.println("IOException : " + e.getMessage());
            }

        } else if (App.isNetworking && !App.myPlayer.equals(this)) { //Si on joue en réseau et que c'est le tour de l'adversaire, alors
            choosenColumn = Communicator.comm.read();
        } else { //Si on est en local.
            choosenColumn = Interface.getChoiceOfColumn(); // fonction de l'interface qui return le choix du joueur
        }

        return choosenColumn;
    }

    //fonction ajoutant une pièce dans le tableau (la grille de jeu).
    public void toAddPiece(String choosenColumn) {

        int column = Integer.parseInt(String.valueOf(choosenColumn.charAt(5))); // à la sixième valeur (soit 5 ici) il y a le numéro

        if(App.colonnes.get(column).size() < App.nbrLines) { //Si la ligne choisi est inférieur au nombre de lignes.
            int lineIndex = (App.colonnes.get(column).size());
            lastPiece = new Piece(this.teamColor, lineIndex, column);   //On créé une nouvelle pièce.
            App.colonnes.get(column).add(lastPiece);    //On l'ajoute à notre grille.
        } else {
            System.out.println("La colonne est deja pleine, choisissez-en une autre.");
            toAddPiece(getChoosenColumn());
        }
    }

    public boolean endGameTest() {

        //victoire 

        boolean isVictory = false;

        if(checkDirection(1, 0) + checkDirection(-1, 0) - 1 >= App.nbrToWin) { //horizontal
            isVictory = true;
        } else if(checkDirection(0, -1) >= App.nbrToWin) { //vertical
            isVictory = true;
        } else if(checkDirection(1,1) + checkDirection(-1,-1) - 1 >= App.nbrToWin) { //diagonale (bas-gauche vers haut-droit)
            isVictory = true;
        } else if(checkDirection(-1,1) + checkDirection(1,-1) - 1 >= App.nbrToWin) { //diagonale (bas-droit vers haut-gauche)
            isVictory = true;
        }

        if(isVictory) {
            Interface.displayEndGameState(this); //On affiche le gagnant.
            return isVictory;
        }

        // egalité

        int bufferEquality = 0;

        // vérification pour toutes les colonnes : si la taille de la colonne est égale au nombre maximal (nbr de ligne).
        for (ArrayList<Piece> column : App.colonnes) {
            if (column.size() >= App.nbrLines) {
                bufferEquality++;
            }
        }

        if(bufferEquality == App.nbrColumns) { //Si la grille est remplie.        
            Interface.displayEndGameState(); //On affiche l'égalité.
            return true;
        }

        //autre
        return false;

    }
    
    //Méthode permettant de vérifier le nombre de pièce aligné (de la même couleur), pour une direction donnée.
    public int checkDirection(int dirX, int dirY) {

        int bufferNbrRightPieces = 0; //Nombre de pièce bonne pour cette vérification.
        
        int x = lastPiece.columnIndex;
        int y = lastPiece.lineIndex;

        for(int i = 0; i <= App.nbrToWin; i++) { //Boucle sur 4 éléments vers une direction donnée.

            int posX = x+dirX*i;
            int posY = y+dirY*i;

            //Si on ne dépasse pas la grille (pas de OutOfBand) et que l'on ne dépasse pas la taille de la colonne (en fonction de son remplissage actuel)
            if((posX >= 0) && (posX < App.nbrColumns) && (posY >= 0) && (posY < App.nbrLines) && (App.colonnes.get(posX).size() >= (posY+1))) {
                if(App.colonnes.get(posX).get(posY).colorOfPiece == this.teamColor) { //Si la pièce est de la couleur recherchée.
                    bufferNbrRightPieces++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }        

        return bufferNbrRightPieces;
    }
}
