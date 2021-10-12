package com.puissance4;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interface {

    //Affichage de la zone de jeu.
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

    //fonction permettant de demander la colonne que veut le joueur
    public static String getChoiceOfColumn() {

        System.out.println("Choisissez la colonne :");

        int choiceColumn = 0;

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            choiceColumn = Integer.parseInt(choice); 
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
            
        } catch(NumberFormatException e) {        
            System.err.println("NumberFormatException : " + e.getMessage());
            
            System.out.println("Veuillez entrer un nombre valide");
            return getChoiceOfColumn();
        }

        if(choiceColumn < App.nbrColumns && choiceColumn >= 0) { //vérification de si le nombre est bien une colonne
            return "Turn " + Integer.toString(choiceColumn);
        } else {
            System.out.println("Veuillez entrer un nombre valide");
            return getChoiceOfColumn();
        }
    }

    //Choix du nom.
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

    //Choix de la couleur.
    public static ColorOfPieces getChoiceOfColor() {

        ColorOfPieces colorOfTeamPlayer = null;

        System.out.println("Veuillez choisir une couleur parmis : ");

        //Affichage du menu de choix de couleur.
        for(int i = 0; i < ColorOfPieces.values().length; i++) {
            System.out.println(i + ". " + ColorOfPieces.values()[i]);
        }

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            String choice = br.readLine();
            int choiceInt = Integer.parseInt(choice);
            
            // Si le numéro en
            if(choiceInt >= 0 && choiceInt < ColorOfPieces.values().length) {
                colorOfTeamPlayer = ColorOfPieces.values()[choiceInt];
            } else {
                System.out.println("Mauvais choix !");
                return getChoiceOfColor();
            }

            //Vérification de la couleur choisi : si elle est déjà prise, alors relancer la fonction de demande.
            for(Player player : App.players) {
                if(player.teamColor == colorOfTeamPlayer) {
                    System.out.println("Couleur déjà choisi !");
                    return getChoiceOfColor();
                }
            }
            
        } catch(IOException e) {
            System.err.println("IOException : " + e.getMessage());
            
        } catch(NumberFormatException e) {
            System.err.println("Nombre invalide !");
            return getChoiceOfColor();
        }  

        return colorOfTeamPlayer;
    }

    //Choix de l'adresse IPv4, si elle n'est pas bonne alors le serveur ne se connectera pas.
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

    //Fonction de l'état de fin du jeu : Victoire.
    public static void displayEndGameState(Player winner) {
        Interface.display();
        System.out.println("The winner is " + winner.name + " !");
    }

    //Fonction de l'état de fin du jeu : Egalité.
    public static void displayEndGameState() {
        Interface.display();
        System.out.println("Il y a une égalité...");
    }

    
    //fonction permettant de demander si la partie se fera en local ou en réseau.
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

    //demande de si c'est la même version/implementation du jeu.
    public static boolean isSameImplementation() {
        System.out.println("Jouez-vous sur la meme version/implementation du jeu que votre adversaire ?");
        System.out.println("| 1. oui | 2. non |");
        System.out.println("Choose a number ;)");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try{
            String choice = br.readLine();

            if(choice.equals("1")) {
                return true;
            } else if(choice.equals("2")) {
                return false;
            } else {
                return isSameImplementation();
            }

        } catch(IOException e) {
            System.err.println("IOException" + e.getMessage());
        }

        return false;
    }

    //demande de jouer en tant que Serveur ou Client.
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

