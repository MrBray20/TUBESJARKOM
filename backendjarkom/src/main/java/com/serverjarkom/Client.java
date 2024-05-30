package com.serverjarkom;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.*;

public class Client {
    public static void main(String[] args) {
        String serverName = "localhost"; // server ip
        int serverPort = 6789; // server port

        try (Socket clientSocket = new Socket(serverName, serverPort);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Scanner sc = new Scanner(System.in)) {
            JSONObject jsonObject = new JSONObject();
            System.out.println("Pilih halaman: (Login/Registrasi)");
            String context = sc.nextLine();
            if (context.equalsIgnoreCase("login")) {
                System.out.println("===========HALAMAN LOGIN===========");
                System.out.println("User name:");
                String userName = sc.nextLine();
                System.out.println("Password:");
                String pass = sc.nextLine();

                // Convert ke JSON
                jsonObject.put("context", context);
                jsonObject.put("username", userName);
                jsonObject.put("password", pass);
            } else {
                System.out.println("===========HALAMAN REGISTRASI===========");
                System.out.println("User name:");
                String userName = sc.nextLine();
                System.out.println("Password:");
                String pass = sc.nextLine();
                System.out.println("Nama:");
                String name = sc.nextLine();

                // Convert ke JSON
                jsonObject.put("context", context);
                jsonObject.put("username", userName);
                jsonObject.put("password", pass);
                jsonObject.put("name", name);
            }
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
}
