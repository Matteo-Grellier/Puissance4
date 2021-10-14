package com.puissance4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

//Les fonctions de cette classe proviennent du cours.
public class Communicator {
    public static final int BUFFER_LENGTH = 1024;
    SocketChannel socket = null;

    static Communicator comm = null;

    //fonction permettant de lancer un Serveur. Attente d'une réponse d'un Client.
    public void accept(){
        System.out.println("Waiting for connection on port 4004");
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(4004));
            socket = ssc.accept();
            System.out.println("Connection established!");
        }
        catch(IOException e){
            System.err.println("Could not launch server : " + e.getMessage());
        }     
    }

    //fonction permettant à un Client de se connecter à une certain Serveur.
    public void connect(String address) throws IOException{
        System.out.println("Connecting to host...");
        socket = SocketChannel.open();
        socket.connect(new InetSocketAddress(address, 4004));        
        System.out.println("Connection established!");
    }

    //fonction permettant d'envoyer du texte en UTF-8 à la personne connectée.
    public void write(String message) throws IOException{
        try{
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
            while(buffer.hasRemaining()){
                socket.write(buffer);
            }
        }
        catch(UnsupportedEncodingException e){
            System.err.println("Unsupported encoding ! " + e.getMessage());
        }
        
    }

    //fonction permettant de lire le texte en UTF-8 de la personne en face.
    public String read(){
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
        try {
            int bytesRead = socket.read(buffer);
            try{
                String message = new String(buffer.array(),0, bytesRead, "UTF-8");
                return message;
            }
            catch(UnsupportedEncodingException e){
                System.err.println("Unsupported encoding!");
            }
        }
        catch(IOException e){
            System.err.println("Could not read from socket : " + e.getMessage());;
        }
        return "";
        
    }

    //fonction permettant de fermer une connexion.
    public void close(){
        try{
            socket.close();
        }
        catch(IOException e){
            System.err.println("Could not close socket");
        }
        
    }

}

