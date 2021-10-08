package com.puissance4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Communicator {
    public static final int BUFFER_LENGTH = 1024;
    SocketChannel socket = null;

    static Communicator comm = null;

    // public static void main(String[] args) {
    //     comm = new Communicator();
    //     String message = "";
    //     if(args.length > 0){
    //         try{
    //             comm.connect(args[0]);
    //         }
    //         catch(IOException e){
    //             System.err.println("Could not connect to target " + args[0] + " : " + e.getMessage());
    //             return;
    //         }
    //         message = sendMessage();
    //     }
    //     else {
    //         comm.accept();
    //     }
        
    //     while(!message.equals("Quit")){
    //         message = comm.read();
    //         if(message.equals("Quit")){
    //             break;
    //         }
    //         System.out.print(">>");
    //         System.out.print(message);
    //         System.out.print("\n");
    //         message = sendMessage();
    //     }
    //     System.out.println("Communication ended.");
    // }

    public static boolean isServing() {

        String choice = Interface.serverOrClient();
        
        if(choice.equals("1")) {
            return true;
        } else if(choice.equals("2")){
            return false;
        } else {
            System.out.println("Réessayez...");
            return isServing();
        }
    }


    public static String getMessageFromConsole() throws IOException{
        System.out.print(">");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String input = br.readLine();
        if(input == ""){
            throw new IOException("Input cannot be empty.");
        }
        return input;
    }

    public static String sendMessage(){
        try{
            String message = getMessageFromConsole();
            comm.write(message);
            return message;
        }
        catch(IOException e){
            System.err.println("Could not send message. " + e.getMessage());
        }
        return "";
    }

    public void accept(){
        System.out.println("Waiting for connection on port 8000");
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(8000));
            socket = ssc.accept();
            System.out.println("Connection established!");
        }
        catch(IOException e){
            System.err.println("Could not launch server : " + e.getMessage());
        }
        
    }

    public void connect(String address) throws IOException{
        System.out.println("Connecting to host...");
        socket = SocketChannel.open();
        socket.connect(new InetSocketAddress(address, 8000));        
        System.out.println("Connection established!");
    }

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

    public void close(){
        try{
            socket.close();
        }
        catch(IOException e){
            System.err.println("Could not close socket");
        }
        
    }

}

