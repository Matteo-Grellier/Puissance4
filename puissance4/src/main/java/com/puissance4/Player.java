package com.puissance4;

import java.util.ArrayList;

enum ColorOfPieces {
    RED,
    GREEN
}

public class Player {

    private ColorOfPieces teamColor;
    String name;
    
    boolean isWinner = false;

    private Piece lastPiece = null;

    public Player(ColorOfPieces teamColor, String name) {
        this.teamColor = teamColor;
        this.name = name;
    }

    public void toAddPiece() {
        int choosenColumn = 0; // fonction de l'interface qui return le choix du joueur
        // int choosenColumn = Interface.getChoiceOfColumn(); // fonction de l'interface qui return le choix du joueur

        if(App.colonnes.get(choosenColumn).size() <= App.nbrLines) {
            int lineIndex = (App.colonnes.get(choosenColumn).size());
            lastPiece = new Piece(this.teamColor, lineIndex, choosenColumn);
            App.colonnes.get(choosenColumn).add(lastPiece);
        } else {
            System.out.println("La colonne est deja pleine, choisissez-en une autre.");
            toAddPiece();
        }
    }

    public boolean endGameTest() {

        //victoire 

        if(checkDirection(1, 0) + checkDirection(-1, 0) - 1 >= App.nbrToWin) { //horizontal
            return true;
        } else if(checkDirection(0, -1) >= App.nbrToWin) { //vertical
            return true;
        } else if(checkDirection(1,1) + checkDirection(-1,-1) - 1 >= App.nbrToWin) { //diagonale (bas-gauche vers haut-droit)
            return true;
        } else if(checkDirection(-1,1) + checkDirection(1,-1) - 1 >= App.nbrToWin) { //diagonale (bas-droit vers haut-gauche)
            return true;
        }

        //egalité

        //autre
        return false;

    }

    public int checkDirection(int dirX, int dirY) {

        int bufferNbrRightPieces = 0;
        
        int x = lastPiece.columnIndex;
        int y = lastPiece.lineIndex;

        for(int i = 0; i <= App.nbrToWin; i++) {

            int posX = x+dirX*i;
            int posY = y+dirY*i;

            if((posX >= 0) && (posX < App.nbrColumns) && (posY >= 0) && (posY < App.nbrLines) && (App.colonnes.get(posX).size() >= (posY+1))) {
                if(App.colonnes.get(posX).get(posY).colorOfPiece == this.teamColor) {
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
