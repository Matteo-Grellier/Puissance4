package com.puissance4;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Interface {

    static void display(){

        for (int i = App.nbrLines-1; i >= 0 ; i--) {
            System.out.print("|");
            for(ArrayList<Piece> column : App.colonnes) {                
                if(i < column.size()) {  
                    if(column.get(i).colorOfPiece == ColorOfPieces.GREEN) {
                        System.out.print("X" + "|");
                    } else {
                        System.out.print("O" + "|");
                    }
                } else {
                    System.out.print(" " + "|");
                }
            }
            System.out.print("\n");
        }
        for (int i = 0; i < App.nbrColumns*2; i++) {
            System.out.print("#");
        }
        System.out.println("");
        System.out.print("|");
        for (int i = 1; i <= App.nbrColumns; i++) {
            
            System.out.print(i);
            System.out.print("|");
        }
    }
    public static int getChoiceOfColumn() {
        int choiceColumn = 0;

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            choiceColumn = Integer.parseInt(choice); 
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }
        return choiceColumn;
    }
}

