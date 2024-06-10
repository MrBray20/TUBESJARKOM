package com.serverjarkom;

import java.net.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.serverjarkom.env.env;

public class Server {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Map<String, Room> rooms = new HashMap<>();
    public static ArrayList<Client> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            dbHandler db = new dbHandler();
            ResultSet clientData = db.dblistClient();
            while (clientData.next()) {
                Client client = new Client(clientData.getString(1), clientData.getString(2), clientData.getString(3),
                        clientData.getString(4));
                clients.add(client);
            }
            ResultSet roomData = db.dblistRoom();
            while (roomData.next()) {
                Client cl = null;
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getUUID().equals(roomData.getString(5))) {
                        cl = clients.get(i);
                    }
                }
                addRoom(roomData.getString(2), cl);
            }
            ServerSocket serverSocket = new ServerSocket(env.PORT);
            System.out.println("Server started on port " + env.PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

    public static synchronized boolean addRoom(String roomName, Client client) {
        if (rooms.putIfAbsent(roomName, new Room(roomName, client)) == null) {
            return true;
        }
        ;
        return false;
    }

    public static synchronized boolean deleteRoom(String roomName) {
        if (rooms.remove(roomName) == null) {
            return true;
        }
        ;
        return false;
    }

    public static synchronized Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    public static synchronized void allRooms() {
        for (Map.Entry<String, Room> room : rooms.entrySet()) {
            String key = room.getKey();
            Room value = room.getValue();
            value.getRoomName();
        }
    }
}
