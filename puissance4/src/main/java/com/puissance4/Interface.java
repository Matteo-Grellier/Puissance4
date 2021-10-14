package com.puissance4;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Interface {

    //Créer un "event" pour chaque boutons
    static class ColumnButtonHandler implements EventHandler<ActionEvent> {
        private final int number ;
        private Player player;
        ColumnButtonHandler(int number, Player player) {
            this.number = number;
            this.player = player;
        }
        @Override
        public void handle(ActionEvent event) { //L'event va ajouter la pièce en fonction de la colonne choisie.
            player.toAddPiece("Turn " + number);
        }

    }

    //Fonction permettant de créer les boutons, retourne un Group (de boutons)
    static Group createButtons(Player player) {

        Group groupButtons = new Group();

        groupButtons.getStylesheets().add(Interface.class.getResource("style.css").toExternalForm());

        int initialX = 5;

        for (int i = 0; i < App.nbrColumns; i++) {
            Button button = new Button();
            button.setOnAction(new ColumnButtonHandler(i, player));
            button.setLayoutX(initialX);

            button.setPrefSize(90, App.nbrLines*100);
            button.setViewOrder(1.0);
            initialX += 100;
            groupButtons.getChildren().add(button); 
        }

        return groupButtons;
    }

    //Fonction permettant d'afficher la grille et les boutons de sélection.
    static void display(Player player){

        Label l = new Label("C'est au tour de " + player.name);
        Font font = Font.font("Arial", 36);
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        Group gridElements = new Group();

        Group groupButtons = createButtons(player);
        Group groupCircles = new Group();

        gridElements.getChildren().add(groupCircles);
        gridElements.getChildren().add(groupButtons);
        groupCircles.setViewOrder(-1.0);
        groupCircles.setViewOrder(1.0);



        int circlePosX = 50;
        int circlePosY = 50;
        for (int i = App.nbrLines-1; i >= 0 ; i--) {

            for(ArrayList<Piece> column : App.colonnes) {    
                if(i < column.size()) {  
                    if(column.get(i).colorOfPiece == ColorOfPieces.GREEN) {
                        Circle circle = new Circle(circlePosX, circlePosY, 30);
                        circle.setStroke(Color.rgb(85,205,116));
                        circle.setFill(Color.rgb(85,205,116));
                        groupCircles.getChildren().add(circle);
                    } else {
                        Circle circle = new Circle(circlePosX, circlePosY, 30);
                        circle.setStroke(Color.rgb(181,52,79));
                        circle.setFill(Color.rgb(181,52,79));
                        groupCircles.getChildren().add(circle);
                    }
                } else {
                    Circle circle = new Circle(circlePosX, circlePosY, 30);
                    circle.setStroke(Color.rgb(36,35,50));
                    circle.setFill(Color.rgb(36,35,50));
                    groupCircles.getChildren().add(circle);
                }

                circlePosX += 100;
            }
            circlePosX = 50;
            circlePosY += 100;
        }

        circlePosX = 50;
        circlePosY = 50;

        VBox box = new VBox();
        box.getChildren().addAll(l,gridElements);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        int width = App.nbrColumns * 120;
        int height = App.nbrLines * 120;

        Scene scene = new Scene(box, width, height);

        App.mainWindow.setScene(scene);
        App.mainWindow.show();
    }

    //Choix du nom.
    public static void getChoicesOfPlayer() {

        Font font = Font.font("Arial", 18);
        Label l = new Label("Veuillez entrer votre nom :");
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");


        TextField name = new TextField();
        name.setPrefSize(100, 30);
        name.setMaxWidth(150);

        Button send = new Button("Valider");
        send.setFont(font);
        send.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        send.setOnMouseClicked(e ->{

            if(App.isNetworking) {
                try {
                    Communicator.comm.write(name.getText());
                    App.name = name.getText();
                } catch(IOException ex) {
                    System.err.println("IOException " + ex.getMessage());
                }
            } else {
                App.name = name.getText();
                getChoiceOfColor();
            }

        });

        VBox box = new VBox();
        box.getChildren().addAll(l, name, send);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        Scene networkScene = new Scene(box, 600, 600);
        App.mainScene = networkScene;

        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
        
    }

    public static void getChoiceOfColor() {

        Font font = Font.font("Arial", 18);

        Label l = new Label("Veuillez choisir la couleur :");
        Button red = new Button("Rouge");
        Button green = new Button("Vert");

        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        red.setFont(font);
        red.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #b73651; -fx-background-radius: 20;");

        green.setFont(font);
        green.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #36b773; -fx-background-radius: 20;");

        red.setOnMouseClicked(e -> {

            ColorOfPieces color = ColorOfPieces.valueOf("RED");

            if (isValidColor(color)) {

                if(App.isNetworking) {
                    try {
                        Communicator.comm.write(color.toString());
                        App.color = color;
                    } catch(IOException ex) {
                        System.err.println("IOException " + ex.getMessage());
                    }
                } else {
                    App.color = color;
                    App.setPlayers(App.name, App.color);

                    if (App.players.size() != App.nbrOfPlayer) {
                        getChoicesOfPlayer();
                    } else {
                        App.setStartingPlayer(App.generateRandomNbr()); //Choix aléatoire du premier joueur.
                        
                        Interface.display(App.players.get(0));
                    }
                }

            } else {
                getChoiceOfColor();
            }
        });

        green.setOnMouseClicked(e -> {

            ColorOfPieces color = ColorOfPieces.valueOf("GREEN");

            if (isValidColor(color)) {
                if(App.isNetworking) {
                    try {
                        Communicator.comm.write(color.toString());
                        App.color = color;
                    } catch(IOException ex) {
                        System.err.println("IOException " + ex.getMessage());
                    }              
                } else {
                    App.color = color;
                    App.setPlayers(App.name, App.color);

                    if (App.players.size() != App.nbrOfPlayer) {
                        getChoicesOfPlayer();
                    } else {
                        System.out.println("nombre de joueur actuelle : " + App.players.size());
                        App.setStartingPlayer(App.generateRandomNbr()); //Choix aléatoire du premier joueur.
                        
                        Interface.display(App.players.get(0));
                    }
                }

            } else {
                getChoiceOfColor();
            }
        });

        VBox box = new VBox();
        box.getChildren().addAll(l, red, green);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        Scene networkScene = new Scene(box, 600, 600);
        App.mainScene = networkScene;

        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
    }

    public static boolean isValidColor(ColorOfPieces colorOfTeamPlayer) {
        //Vérification de la couleur choisi : si elle est déjà prise, alors relancer la fonction de demande.
        for(Player player : App.players) {
            if(player.teamColor == colorOfTeamPlayer) {
                System.out.println("Couleur déjà choisi !");

                Stage alreadyChoosenStage = new Stage();

                Font font = Font.font("Arial", 18);
                
                Label l = new Label("Couleur déjà choisi ! Choisissez-en une autre");
                Button b = new Button("Choisir");

                
                l.setFont(font);
                l.setStyle("-fx-text-fill: #FFFFFF;");

                b.setFont(font);
                b.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");


                b.setOnMouseClicked(e ->{
                    alreadyChoosenStage.close();
                });
                
                VBox box = new VBox();
                box.getChildren().addAll(l,b);
                box.setAlignment(Pos.CENTER);
                box.setSpacing(20);
                box.setStyle("-fx-background-color: #3e3c55;"); 

                Scene alreadyChooseScene = new Scene(box, 300, 300);
                alreadyChoosenStage.setTitle("Mince...");
                alreadyChoosenStage.setScene(alreadyChooseScene);
                alreadyChoosenStage.initModality(Modality.APPLICATION_MODAL);

                alreadyChoosenStage.show();


                return false;
            }
        }

        return true;
    }

        //Choix de l'adresse IPv4, si elle n'est pas bonne alors le serveur ne se connectera pas.
        public static void getChoiceOfAdress() {

            Label l = new Label("Veuillez entrer l'adresse (ip) de la partie :");
            TextField address = new TextField ();
            Button send = new Button("Valider");

            Font font = Font.font("Arial", 18);

            address.setPrefSize(100, 30);
            address.setMaxWidth(150);
            
            l.setFont(font);
            l.setStyle("-fx-text-fill: #FFFFFF;");

            send.setFont(font);
            send.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

            send.setOnMouseClicked(e ->{

                try {
                   Communicator.comm.connect(address.getText());
                   App.clientSetup();
                } catch(IOException ex) {
                    System.err.println("IOException " + ex.getMessage());
                }
            });
    
            VBox box = new VBox();
            box.getChildren().addAll(l, address, send);
            box.setAlignment(Pos.CENTER); //pour centrer
            box.setSpacing(20);
            box.setStyle("-fx-background-color: #3e3c55;");
    
            Scene networkScene = new Scene(box, 600, 600);
            App.mainScene = networkScene;
    
            App.mainWindow.setScene(App.mainScene);
    
            App.mainWindow.show();


        }


    //Fonction de l'état de fin du jeu : Victoire.
    public static void displayEndGameState(Player winner) {
        System.out.println("Le grand gagnant est " + winner.name + " !");

        Label l = new Label("Le grand gagnant est " + winner.name + " !");
        Button retry = new Button("Recommencer une partie");
        Button quit = new Button("Quitter");

        Font font = Font.font("Arial", 18);
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        retry.setFont(font);
        retry.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        quit.setFont(font);
        quit.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        retry.setOnMouseClicked(e -> {
            App.colonnes = new ArrayList<ArrayList<Piece>>();
            App.players = new ArrayList<Player>();
            App.setGrid(); //Mise en place du tableau.
            Interface.isNetwork();
        });

        quit.setOnMouseClicked(e -> {
            App.mainWindow.close();
        });

        VBox box = new VBox();
        box.getChildren().addAll(l, retry, quit);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        Scene endScene = new Scene(box, 600, 600);
        App.mainScene = endScene;
        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
     
    }

    //Fonction de l'état de fin du jeu : Egalité.
    public static void displayEndGameState() {
        System.out.println("Il y a une égalité...");

        Label l = new Label("Mince ! Il y a une égalité...");
        Button retry = new Button("Recommencer une partie");
        Button quit = new Button("Quitter");

        Font font = Font.font("Arial", 18);
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        retry.setFont(font);
        retry.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        quit.setFont(font);
        quit.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        retry.setOnMouseClicked(e -> {
            App.colonnes = new ArrayList<ArrayList<Piece>>();
            App.players = new ArrayList<Player>();
            App.setGrid(); //Mise en place du tableau.
            Interface.isNetwork();
        });

        quit.setOnMouseClicked(e -> {
            App.mainWindow.close();
        });

        VBox box = new VBox();
        box.getChildren().addAll(l, retry, quit);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);

        Scene endScene = new Scene(box, 600, 600);
        App.mainScene = endScene;
        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
    }

        //fonction permettant de demander si la partie se fera en local ou en réseau.
        public static void isNetwork() {

            Label l = new Label("Comment voulez-vous jouer ?");
            Button local = new Button("Local");
            Button res = new Button("Réseaux");

            Font font = Font.font("Arial", 18);
            l.setFont(font);
            l.setStyle("-fx-text-fill: #FFFFFF;");
    
            local.setFont(font);
            local.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");
    
            res.setFont(font);
            res.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #b8334f; -fx-background-radius: 20;");

            res.setOnMouseClicked(e ->{
                App.isNetworking = true;
                Communicator.comm = new Communicator();
                Interface.isSameImplementation();
            });

            local.setOnMouseClicked(e ->{
                App.isNetworking = false;
                Interface.getChoicesOfPlayer();
                App.firstRoundState = "first round finished";
            });
    
            VBox box = new VBox();
            box.getChildren().addAll(l, local, res);
            box.setAlignment(Pos.CENTER); //pour centrer
            box.setSpacing(20);
            box.setStyle("-fx-background-color: #3e3c55;");
    
            Scene networkScene = new Scene(box, 600, 600);
            App.mainScene = networkScene;
    
            App.mainWindow.setScene(App.mainScene);
    
            App.mainWindow.show();
        }

    //demande de si c'est la même version/implementation du jeu.
    public static void isSameImplementation() {

        Label l = new Label("Jouez-vous sur la même version/implementation du jeu que votre adversaire ?");
        Button yes = new Button("Oui");
        Button no = new Button("Non");

        Font font = Font.font("Arial", 18);
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        yes.setFont(font);
        yes.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        no.setFont(font);
        no.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        yes.setOnMouseClicked(e ->{

            App.isSameImplementation = true;
            Interface.serverOrClient();

        });

        no.setOnMouseClicked(e ->{

            App.isSameImplementation = false;
            Interface.serverOrClient();
            
        });

        VBox box = new VBox();
        box.getChildren().addAll(l, yes, no);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        Scene networkScene = new Scene(box, 600, 600);
        App.mainScene = networkScene;

        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
    }

    //demande de jouer en tant que Serveur ou Client.
    public static void serverOrClient() {

        Label l = new Label("Comment voulez-vous jouer ?");
        Label warningMessage = new Label("ATTENTION ! L'implémentation du réseau ne marche pas...\n (Retrouvez la partie réseau sur l'étape 2)");
        Button serv = new Button("Créer une partie");
        Button client = new Button("Rejoindre une partie");

        Font font = Font.font("Arial", 18);
        l.setFont(font);
        l.setStyle("-fx-text-fill: #FFFFFF;");

        warningMessage.setFont(font);
        warningMessage.setStyle("-fx-text-fill: #b8334f;");

        serv.setFont(font);
        serv.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        client.setFont(font);
        client.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #6c63ff; -fx-background-radius: 20;");

        serv.setOnMouseClicked(e ->{

            App.setupNetworkGame(true);

        });

        client.setOnMouseClicked(e ->{

            App.setupNetworkGame(false);

        });

        VBox box = new VBox();
        box.getChildren().addAll(l, serv, client, warningMessage);
        box.setAlignment(Pos.CENTER); //pour centrer
        box.setSpacing(20);
        box.setStyle("-fx-background-color: #3e3c55;");

        Scene networkScene = new Scene(box, 600, 600);
        App.mainScene = networkScene;

        App.mainWindow.setScene(App.mainScene);

        App.mainWindow.show();
    }

}

