package com.serverjarkom;

import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.serverjarkom.controller.ClientHandler;
import com.serverjarkom.env.Env;
import com.serverjarkom.model.Client;
import com.serverjarkom.model.Room;

public class Server {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Map<String, Room> rooms = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Env.PORT);
            System.out.println("Server started on port " + Env.PORT);

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

    public static synchronized boolean addRoom(String roomName,Client client) {
        if(rooms.putIfAbsent(roomName, new Room(roomName,client)) == null){
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
