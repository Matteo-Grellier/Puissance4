package com.puissance4;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
// import java.lang.Enum;
import javafx.stage.Stage;

public class Interface extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Group groupButtons = new Group();
        groupButtons.toFront();
        Group groupCircles = new Group();
        groupCircles.toBack();
        groupButtons.getChildren().add(groupCircles);
        Scene scene = new Scene(groupButtons,720 , 640);
        int initialX = 70;
        
        int circlePosX = 130;
        for (int i = 1; i <= App.nbrColumns; i++) {
            Button button = new Button();
            Scale bouton = new Scale(5,10);
            bouton.setX(7);
            bouton.setY(100);
            
            button.setLayoutX(0*i + initialX);
            initialX += 130;
            button.getTransforms().add(bouton);
            groupButtons.getChildren().add(button);
            button.toFront();
            groupButtons.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            for (int j = 2; j <= App.nbrLines; j++) {
                System.out.println(circlePosX);
                Circle circle = new Circle(circlePosX, 130*j-60, 50);
                circle.setStroke(Color.WHITE);
                circle.setFill(Color.WHITE);
                circle.toBack();
                groupCircles.getChildren().add(circle);
            }
            circlePosX += 130;
            Circle circle = new Circle(130*i, 70, 50);
            
            scene.setFill(Color.BLUEVIOLET);
            circle.setStroke(Color.WHITE);
            circle.setFill(Color.WHITE);
            groupCircles.getChildren().add(circle);
        }
        int width = App.nbrColumns * 150;
        int height = App.nbrLines * 150;
        
        window.setWidth(width);
        window.setHeight(height);
        window.setScene(scene);
        window.show();
    }
}

// public class Interface {

//     public static String getChoiceOfName() {

//         System.out.println("Veuillez entrer votre nom : ");

//         InputStreamReader isr = new InputStreamReader(System.in);
//         BufferedReader br = new BufferedReader(isr);

//         try {
//             String choice = br.readLine();
//             return choice;
             
//         } catch(IOException e) {
//             System.err.println("IOException : " + e.getMessage());
//         }

//         return "default_name";
//     }

//     public static ColorOfPieces getChoiceOfColor() {

//         ColorOfPieces colorOfTeamPlayer = null;

//         System.out.println("Veuillez choisir une couleur parmis : ");

//         for(int i = 0; i < ColorOfPieces.values().length; i++) {

//             // for(Player player : App.players) {
//             //     if(ColorOfPieces.values()[i] != player.teamColor) {
//             //         System.out.println(i + ". " + ColorOfPieces.values()[i]);
//             //     }
//             // }
//             // if(App.players.size() > j && ColorOfPieces.values()[i] != App.players.get(j).teamColor) {
//             //     System.out.println(i + ". " + ColorOfPieces.values()[i]);
//             // }

//             System.out.println(i + ". " + ColorOfPieces.values()[i]);
//         }

//         InputStreamReader isr = new InputStreamReader(System.in);
//         BufferedReader br = new BufferedReader(isr);

//         try {
//             String choice = br.readLine();
//             int choiceInt = Integer.parseInt(choice);
//             colorOfTeamPlayer = ColorOfPieces.values()[choiceInt];

//             for(Player player : App.players) {
//                 if(player.teamColor == colorOfTeamPlayer) {
//                     System.out.println("Couleur déjà choisi !");
//                     return getChoiceOfColor();
//                 }
//             }
            
//         } catch(IOException e) {
//             System.err.println("IOException : " + e.getMessage());
//         }

//         return colorOfTeamPlayer;
//     }

//     public static void displayEndGameState(Player winner) {
//         System.out.println("The winner is " + winner.name + " !");
//     }

//     public static void displayEndGameState() {
//         System.out.println("Il y a une égalité...");
//     }
// }

