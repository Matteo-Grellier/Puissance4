package com.puissance4;

import java.util.ArrayList;

public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;

    public static void main(String[] args)
    {
        Interface.Test();
        arr();
    }

    static ArrayList<ArrayList<String>> arr(){
        final ArrayList<ArrayList<String>> colonnes =  new ArrayList<ArrayList<String>>();        
        for (int i = 0; i < nbrColumns; i++) {
            colonnes.add(new ArrayList<String>());
            for (int j = 0; j < nbrLines; j++) {
                colonnes.get(i).add(" ");
            }
        }
        // System.out.println(colonnes);
        return colonnes; 
    }
 
}