package com.puissance4;

import java.util.ArrayList;

enum ColorOfPieces {
    RED,
    GREEN
}

public class Player {

    private ColorOfPieces teamColor;
    String name;

    public Player(ColorOfPieces teamColor, String name) {
        this.teamColor = teamColor;
        this.name = name;
    }

    public void toAddPiece() {
        int choosenColumn = 0; // fonction de l'interface qui return le choix du joueur
        // int choosenColumn = Interface.getChoiceOfColumn(); // fonction de l'interface qui return le choix du joueur

        if(App.colonnes.get(choosenColumn).size() <= App.nbrLines) {
            int lineIndex = App.colonnes.get(choosenColumn).size();
            App.colonnes.get(choosenColumn).add(new Piece(this.teamColor, lineIndex, choosenColumn));
        }
    }
}
