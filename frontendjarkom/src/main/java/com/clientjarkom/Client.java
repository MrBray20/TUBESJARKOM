package com.clientjarkom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.clientjarkom.model.ChatMessage;
import com.clientjarkom.model.CommadMessage;

import com.google.gson.Gson;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 6789;
        String roomName = null;
        Gson gson = new Gson();

        try (Socket socket = new Socket(hostname, port)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            ChatMessage chatMessageFromServer = new ChatMessage();
            CommadMessage commadMessage = new CommadMessage();

            new Thread(() -> {
                String serverMessage;
                try{
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e){
                    // Todo : s
                }
            }).start();

            ChatMessage chatMessageToServer = new ChatMessage();


            String userMessage ;
            while ((userMessage = stdIn.readLine()) != null) {
                if(userMessage.startsWith("/")){
                    commadMessage.setCommad(userMessage);
                    out.println(gson.toJson(commadMessage));
                } else if(roomName==null){
                    commadMessage.setMessage(userMessage);
                    out.println(gson.toJson(commadMessage));
                }
                // out.println(userMessage);
            }


        }catch (IOException e){

        }
    }
}
