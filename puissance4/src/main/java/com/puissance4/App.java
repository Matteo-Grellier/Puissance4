package com.puissance4;

import java.util.*;
public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;
    


    public static void main(String[] args)
    {
        arr();
    }

    static ArrayList<Integer> arr(){
        final ArrayList<ArrayList<Integer>> colonnes =  new ArrayList<ArrayList<Integer>>();
        final ArrayList<Integer> lignes = new ArrayList<Integer>();
        for (int i = 0; i < nbrColumns; i++) {
            colonnes.add(lignes);
            lignes.add(i);
        }   
        // System.out.println(colonnes);
        return lignes; 
    }
}