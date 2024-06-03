package com.serverjarkom;

import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.serverjarkom.env.env;

public class Server {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Map<String, Room> rooms = new HashMap<>();
    
    public static void main (String[] args) {
        try {
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
        }finally {
            threadPool.shutdown();
        }

    }

    public static synchronized boolean addRoom(String roomName) {
        if(rooms.putIfAbsent(roomName, new Room(roomName)) == null){
            return true;
        };
        return false ;
    }

    public static synchronized Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    public static synchronized void allRooms(){
        for (Map.Entry<String, Room> room : rooms.entrySet()) {
            String key = room.getKey();
            Room value = room.getValue();
            value.getName();
        }
    }
}
