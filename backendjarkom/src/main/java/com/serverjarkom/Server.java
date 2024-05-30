package com.serverjarkom;

import java.io.*;
import java.net.*;
import java.sql.*;
import org.json.*;

public class Server {
    public static final int serverPort = 6789;


    public static void main (String[] args) throws Exception{
        try {
            ServerSocket welcomeSocket = new ServerSocket(serverPort);
            System.out.println("TCP Server Up...");

            // Establish database connection
            Connection connection = connectToDatabase();
            if (connection != null) {
                // Execute SQL queries
                executeSQL(connection);
            }

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                new ClientHandler(connectionSocket, connection).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static Connection connectToDatabase() {
        String jdbcURL = "jdbc:mysql://localhost:3306/jarkom";
        String jdbcUsername = "admin";
        String jdbcPassword = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL database.");
            e.printStackTrace();
            return null;
        }
    }

    private static void executeSQL(Connection connection) {
        String[] sqlStatements = {
//                "CREATE TABLE IF NOT EXISTS `users` (" +
//                        " `id_user` char(36) NOT NULL UNIQUE," +
//                        " `user_name` varchar(100) NOT NULL," +
//                        " `pass_user` char(64) NOT NULL," +
//                        " `name` varchar(255) NOT NULL," +
//                        " `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
//                        " PRIMARY KEY (`id_user`)" +
//                        ")",
//
//                "CREATE TABLE IF NOT EXISTS `rooms` (" +
//                        " `id_room` int AUTO_INCREMENT NOT NULL UNIQUE," +
//                        " `name_room` varchar(255) NOT NULL," +
//                        " `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
//                        " `room_code` char(10) NOT NULL," +
//                        " `own_room` char(36) NOT NULL," +
//                        " PRIMARY KEY (`id_room`)" +
//                        ")",
//
//                "CREATE TABLE IF NOT EXISTS `message` (" +
//                        " `id_massage` int AUTO_INCREMENT NOT NULL UNIQUE," +
//                        " `user_id` char(36) NOT NULL," +
//                        " `room_id` int NOT NULL," +
//                        " `content` text NOT NULL," +
//                        " `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
//                        " PRIMARY KEY (`id_massage`)" +
//                        ")",
//
//                "CREATE TABLE IF NOT EXISTS `room_members` (" +
//                        " `user_id` char(36) NOT NULL UNIQUE," +
//                        " `room_id` int NOT NULL UNIQUE," +
//                        " PRIMARY KEY (`user_id`, `room_id`)" +
//                        ")",
//
//                "CREATE TABLE IF NOT EXISTS `friends` (" +
//                        " `id_friends` int AUTO_INCREMENT NOT NULL UNIQUE," +
//                        " `id_user1` char(36) NOT NULL," +
//                        " `id_user2` char(36) NOT NULL," +
//                        " `date` datetime NOT NULL," +
//                        " PRIMARY KEY (`id_friends`)" +
//                        ")",
//
//                "ALTER TABLE `rooms` ADD CONSTRAINT `rooms_fk4` FOREIGN KEY (`own_room`) REFERENCES `users`(`id_user`)",
//                "ALTER TABLE `message` ADD CONSTRAINT `message_fk1` FOREIGN KEY (`user_id`) REFERENCES `users`(`id_user`)",
//                "ALTER TABLE `message` ADD CONSTRAINT `message_fk2` FOREIGN KEY (`room_id`) REFERENCES `rooms`(`id_room`)",
//                "ALTER TABLE `room_members` ADD CONSTRAINT `room_members_fk0` FOREIGN KEY (`user_id`) REFERENCES `users`(`id_user`)",
//                "ALTER TABLE `room_members` ADD CONSTRAINT `room_members_fk1` FOREIGN KEY (`room_id`) REFERENCES `rooms`(`id_room`)",
//                "ALTER TABLE `friends` ADD CONSTRAINT `friends_fk1` FOREIGN KEY (`id_user1`) REFERENCES `users`(`id_user`)",
//                "ALTER TABLE `friends` ADD CONSTRAINT `friends_fk2` FOREIGN KEY (`id_user2`) REFERENCES `users`(`id_user`)"
        };

        try (Statement statement = connection.createStatement()) {
            for (String sql : sqlStatements) {
                statement.executeUpdate(sql);
            }
            System.out.println("Database tables created/updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
