package com.serverjarkom;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private Set<ClientHandler> clients = new HashSet<>();
    private String roomCode;
    private ClientHandler admin;


    public Room(String name) {
        this.name = name;
    }


    public synchronized void addClient(ClientHandler client){
        joinBroadcast(client);
        clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        leaveBroadcast(client);
        clients.remove(client);
    }

    public synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            String name = sender.getClientName();
            if (client != sender) {
                client.sendMessage(name +  ": " + message);
            }
        }
    }
    public synchronized void joinBroadcast(ClientHandler sender) {
        for (ClientHandler client : clients) {
            String name = sender.getClientName();
            if (client != sender) {
                client.sendMessage(name + " joined room");
            }
        }
    }

    public synchronized void leaveBroadcast(ClientHandler sender) {
        for (ClientHandler client : clients) {
            String name = sender.getClientName();
            if (client != sender) {
                client.sendMessage(name + " leave room");
            }
        }
    }


    public synchronized Set<ClientHandler> getClients(){
        return clients;
    }

    public String getName() {
        return name;
    }

}
