package com.serverjarkom;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.UUID;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Connection connection;

    public ClientHandler(Socket clientSocket, Connection connection) {
        this.clientSocket = clientSocket;
        this.connection = connection;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Read JSON string dari client
            String jsonString = in.readLine();

            // Parse JSON string
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonResponse = new JSONObject();
            String context = jsonObject.getString("context");
            if(context.equalsIgnoreCase("login")){
                String userName = jsonObject.getString("username");
                String passUser = jsonObject.getString("password");

                // Validasi login ke database
                if (login(userName, passUser)) {
                    System.out.println("Password benar!");
                    String token = generateToken();
                    jsonResponse.put("status", "Login berhasil");
                    jsonResponse.put("token", token);
                } else {
                    System.out.println("Password salah!");
                    jsonResponse.put("status", "Login gagal");
                }
                out.println(jsonResponse.toString());
            }else{
                String userName = jsonObject.getString("username");
                String passUser = jsonObject.getString("password");
                String name = jsonObject.getString("name");

                // Tambah user ke database
                if (register(userName, passUser, name)) {
                    System.out.println("Registrasi berhasil");
                    jsonResponse.put("status", "Registrasi berhasil");
                } else {
                    System.out.println("Registrasi gagal");
                    jsonResponse.put("status", "Registrasi gagal");
                }
                out.println(jsonResponse.toString());
            }




        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private boolean register(String userName, String passUser, String name) {
        String insertUserSQL = "INSERT INTO users (id_user, user_name, pass_user, name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            String userId = UUID.randomUUID().toString(); // Unik ID
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, passUser);
            preparedStatement.setString(4, name);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean login(String userName, String passUser) {
        String query = "SELECT * FROM users WHERE user_name = ? AND pass_user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Jika password benar
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateToken() {
        // Generate a random login token
        return UUID.randomUUID().toString();
    }
}