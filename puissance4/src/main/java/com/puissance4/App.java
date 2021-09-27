package com.puissance4;

import java.util.*;
public class App {

    static int nbrColumns = 7;
    static int nbrLines = 6;

    private static ArrayList<ArrayList<Piece>> colonnes =  new ArrayList<ArrayList<Piece>>();

    public static void main(String[] args)
    {

        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Piece> newColumn = new ArrayList<Piece>();
            colonnes.add(newColumn);
        }

    }

    public static void round() {
        
    }
}