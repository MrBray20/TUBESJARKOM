package com.clientjarkom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 6789;


        try (Socket socket = new Socket(hostname, port)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));


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


            String userMessage ;

            while ((userMessage = stdIn.readLine()) != null) {
                out.println(userMessage);
            }


        }catch (IOException e){

        }
    }
}
