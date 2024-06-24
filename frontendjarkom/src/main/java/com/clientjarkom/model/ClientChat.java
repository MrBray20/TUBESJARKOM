package com.clientjarkom.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import com.clientjarkom.util.MessageHandler;
import com.google.gson.Gson;


public class ClientChat {
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private Client client;
    private Socket socket;
    private MessageHandler messageHandler;

    public ClientChat(){
        String hostname = "localhost";
        int port = 6789;
        String roomName = null;
        

        try {
            this.socket = new Socket(hostname, port);
            this.outToServer = new PrintWriter(this.socket.getOutputStream(),true);
            this.inFromServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            messageHandler = new MessageHandler(this.outToServer, this.inFromServer);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void handlerMessageIn (){
        new Thread(()-> {
            String serverMessage;
            try {
                while ((serverMessage = inFromServer.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }).start();
    }


    public void handlerMessageOut (String json){
        while (json !=null) {
            this.outToServer.println(json);
        }

    }

    public void handlerAuth(String json) {
        this.outToServer.println(json);
    }

    public Object handlerResAuth() throws IOException{
        return messageHandler.handleMessage(this.inFromServer.readLine());
    }

    public void handlerCommand(String json) {
        this.outToServer.println(json);
    }

    // public static void main(String[] args) {
    //     String hostname = "localhost";
    //     int port = 6789;
    //     String roomName = null;
    //     Gson gson = new Gson();

    //     try (Socket socket = new Socket(hostname, port)){
    //         PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
    //         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    //         BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    //         ChatMessage chatMessageFromServer = new ChatMessage();
    //         CommadMessage commadMessage = new CommadMessage();

    //         new Thread(() -> {
    //             String serverMessage;
    //             try{
    //                 while ((serverMessage = in.readLine()) != null) {
    //                     System.out.println(serverMessage);
    //                 }
    //             } catch (IOException e){
    //                 // Todo : s
    //             }
    //         }).start();

    //         ChatMessage chatMessageToServer = new ChatMessage();


    //         String userMessage ;
    //         while ((userMessage = stdIn.readLine()) != null) {
    //             if(userMessage.startsWith("/")){
    //                 commadMessage.setCommad(userMessage);
    //                 out.println(gson.toJson(commadMessage));
    //             } else if(roomName==null){
    //                 commadMessage.setMessage(userMessage);
    //                 out.println(gson.toJson(commadMessage));
    //             }
    //             out.println(userMessage);
    //         }


    //     }catch (IOException e){

    //     }
    // }
}
