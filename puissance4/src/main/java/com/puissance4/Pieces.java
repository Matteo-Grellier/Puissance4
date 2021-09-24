package com.puissance4;

public class Piece {
    
    private int colorOfPiece; //couleur de la pièce (celon un Enum)

    private int lineIndex; //index de la ligne
    private int columnIndex; //index de la colonne

    public Piece(int colorOfPiece, int lineIndex, int columnIndex) {
        this.colorOfPiece = colorOfPiece;
        this.lineIndex = lineIndex;
        this.columnIndex = columnIndex;
    }


}
