package com.puissance4;

import java.util.*;
public class App {

    static int nbrColumns = 8;
    static int nbrLines = 6;


    public static void main(String[] args)
    {
        
        // Here aList is an ArrayList of ArrayLists
        ArrayList<ArrayList<Integer>> colonnes =  new ArrayList<ArrayList<Integer>>();
  
        // Create n lists one by one and append to the 
        // master list (ArrayList of ArrayList)
        
        for (int i = 0; i < nbrColumns; i++) {
            ArrayList<Integer> a1 = new ArrayList<Integer>();
            colonnes.add(a1);
            a1.add(i);
        }  
    }
}