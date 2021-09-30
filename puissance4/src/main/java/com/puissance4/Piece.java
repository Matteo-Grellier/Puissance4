package com.puissance4;

public class Piece {
    
    ColorOfPieces colorOfPiece; //couleur de la pièce (selon un Enum)

    int lineIndex; //index de la ligne
    int columnIndex; //index de la colonne

    public Piece(ColorOfPieces colorOfPiece, int lineIndex, int columnIndex) {
        this.colorOfPiece = colorOfPiece;
        this.lineIndex = lineIndex;
        this.columnIndex = columnIndex;
    }   

}
