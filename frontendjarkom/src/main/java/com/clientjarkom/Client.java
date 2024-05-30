package com.clientjarkom;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.*;


public class Client {
    private static String serverName = "localhost"; // server ip
    private static int serverPort = 6789; // server port
    public static void login(String username, String password) {

        try (Socket clientSocket = new Socket(serverName, serverPort);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            JSONObject jsonObject = new JSONObject();

            // Convert ke JSON
            jsonObject.put("context", "login");
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            // Kirim ke server
            out.println(jsonObject.toString());

            // Terima respon server
            String serverResponse = in.readLine();
            JSONObject jsonResponse = new JSONObject(serverResponse);
            String status = jsonResponse.getString("status");
            System.out.println("Status: " + status);

            // Jika respons adalah login berhasil, dapatkan token
            if (status.equalsIgnoreCase("login berhasil")) {
                String token = jsonResponse.getString("token");
                System.out.println("Token: " + token);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void register(String username, String password, String name) {


        try (Socket clientSocket = new Socket(serverName, serverPort);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            JSONObject jsonObject = new JSONObject();

            // Convert ke JSON
            jsonObject.put("context", "register");
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("name", name);

            // Kirim ke server
            out.println(jsonObject.toString());

            // Terima respon server
            String serverResponse = in.readLine();
            JSONObject jsonResponse = new JSONObject(serverResponse);
            String status = jsonResponse.getString("status");
            System.out.println("Status: " + status);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
