package com.puissance4;


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

    public void toAddPiece() { 
        int choosenColumn = Interface.getChoiceOfColumn(); // fonction de l'interface qui return le choix du joueur
        if(App.colonnes.get(choosenColumn).size() < App.nbrLines) {
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
        if(bufferEquality == App.nbrColumns) {
            
            Interface.displayEndGameState(); //On affiche l'égalité.
            return true;
        }

        //autre
        return false;

    }
    
    //Méthode permettant de vérifier le nombre de pièce aligné (de la même couleur), pour une direction donnée.
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
