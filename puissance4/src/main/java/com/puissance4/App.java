package com.puissance4;

import java.util.ArrayList;

public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;
    static Boolean verifEgalite = false;


    public static void main(String[] args)
    {
        Interface.Test();
        arr();
        egalite();
    }

    static ArrayList<ArrayList<String>> arr(){
        final ArrayList<ArrayList<String>> colonnes =  new ArrayList<ArrayList<String>>();        
        for (int i = 0; i < nbrColumns; i++) {
            colonnes.add(new ArrayList<String>());
            for (int j = 0; j < nbrLines; j++) {
                colonnes.get(i).add(" ");
            }
        }
        for (int i = 0; i < nbrColumns; i++) {
            colonnes.get(i).set(0,"O");
        }
        // System.out.println(colonnes);
        return colonnes; 
    }
    static void egalite(){
        for (int i = 0; i < nbrColumns; i++) {
            if (arr().get(i).get(i) == " "){
                break;
            }else {
               verifEgalite = true; 
            }
        
        }
        if (verifEgalite == true) {
            System.out.println("Egalité");
        }
    }

}