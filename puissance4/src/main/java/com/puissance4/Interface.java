package com.puissance4;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.lang.Enum;
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
        for (int i = 0; i < App.nbrColumns*2+1; i++) {
            System.out.print("#");
        }
        System.out.println("");
        System.out.print("|");
        for (int i = 0; i < App.nbrColumns; i++) {
            
            System.out.print(i);
            System.out.print("|");
        }
        System.out.print("\n");
    }
    public static int getChoiceOfColumn() {

        System.out.println("Choisissez la colonne :");

        int choiceColumn = 0;

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            choiceColumn = Integer.parseInt(choice); 
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
            
            //voir si ca marche ?
            System.out.println("Veuillez entrer un nombre valide");
            return getChoiceOfColumn();
        }
        return choiceColumn;
    }

    public static String getChoiceOfName() {

        System.out.println("Veuillez entrer votre nom : ");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            return choice;
            
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }

        return "default_name";
    }

    public static ColorOfPieces getChoiceOfColor() {

        ColorOfPieces colorOfTeamPlayer = null;

        System.out.println("Veuillez choisir une couleur parmis : ");

        for(int i = 0; i < ColorOfPieces.values().length; i++) {

            // for(Player player : App.players) {
            //     if(ColorOfPieces.values()[i] != player.teamColor) {
            //         System.out.println(i + ". " + ColorOfPieces.values()[i]);
            //     }
            // }
            // if(App.players.size() > j && ColorOfPieces.values()[i] != App.players.get(j).teamColor) {
            //     System.out.println(i + ". " + ColorOfPieces.values()[i]);
            // }

            System.out.println(i + ". " + ColorOfPieces.values()[i]);
        }

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            int choiceInt = Integer.parseInt(choice);
            colorOfTeamPlayer = ColorOfPieces.values()[choiceInt];

            for(Player player : App.players) {
                if(player.teamColor == colorOfTeamPlayer) {
                    System.out.println("Couleur déjà choisi !");
                    return getChoiceOfColor();
                }
            }
            
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }

        return colorOfTeamPlayer;
    }

    public static String getChoiceOfAdress() {
        System.out.println("Veuillez entrer l'adresse (ip) de la partie : ");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            return choice;
            
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
        }

        return "localhost";
    }

    public static void displayEndGameState(Player winner) {
        System.out.println("The winner is " + winner.name + " !");
    }

    public static void displayEndGameState() {
        System.out.println("Il y a une égalité...");
    }

    /*For the network system*/

    public static boolean isNetwork() {
        
        System.out.println("Comment voulez-vous jouer ?");
        System.out.println("| 1. Local | 2. Reseaux |");
        System.out.println("Choose a number ;)");


        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try{
            String choice = br.readLine();

            if(choice.equals("1")) {
                return false;
            } else if(choice.equals("2")) {
                return true;
            } else {
                return isNetwork();
            }

        } catch(IOException e) {
            System.err.println("IOException" + e.getMessage());
        }

        return false;
    }

    // public static boolean isServing();

    public static String serverOrClient() {
        System.out.println("Comment voulez-vous jouer ?");
        System.out.println("| 1. Créer une partie | 2. Rejoindre une partie |");
        System.out.println("Choisi un nombre ;)");


        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try{
            String choice = br.readLine();

            return choice;

        } catch(IOException e) {
            System.err.println("IOException" + e.getMessage());
        }

        return "";
    }
}

