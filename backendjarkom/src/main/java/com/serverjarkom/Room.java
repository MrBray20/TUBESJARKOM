package com.serverjarkom;

import java.util.HashSet;
import java.util.Set;

import java.util.Iterator;

public class Room {
    private String name;
    private Set<ClientHandler> clients = new HashSet<>();
    private String roomCode;
    private Client ownRoom;


    public Room(String name, Client client) {
        this.name = name;
        roomCode = randomManager.codeRoom();
        ownRoom = client;
    }
    

    public synchronized void addClient(ClientHandler client){
        joinBroadcast(client);
        clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        leaveBroadcast(client);
        clients.remove(client);
    }

    public synchronized void broadcastKick(String message,ClientHandler removeabelClient , ClientHandler sender) {
        for (ClientHandler client : this.clients) {
            String name = sender.getClient().getName();
            if ((client != sender) && (client != removeabelClient)) {
                client.sendMessage(name +  " mengeluarkan " + removeabelClient.getClient().getName() + " dari room");
            }
        }
    }

    public synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : this.clients) {
            String name = sender.getClient().getName();
            if (client != sender) {
                client.sendMessage(name +  ": " + message);
            }
        }
    }

    public synchronized void joinBroadcast(ClientHandler sender) {
        for (ClientHandler client : this.clients) {
            String name = sender.getClient().getName();
            if (client != sender) {
                client.sendMessage(name + " Memasukkin room");
            }
        }
    }

    public synchronized void leaveBroadcast(ClientHandler sender) {
        for (ClientHandler client : this.clients) {
            String name = sender.getClient().getName();
            if (client != sender) {
                client.sendMessage(name + " Keluar room");
            }
        }
    }


    public synchronized void showMember(ClientHandler request){
        for (ClientHandler client : clients) {
            request.sendMessage(client.getClient().getName());
        }
    }

    public synchronized Set<ClientHandler> getClients(){

        return this.clients;
    }

    public synchronized void kick(String name,ClientHandler admin){
        ClientHandler removableClient = cekClient(name);
        System.out.println(admin.getClient().getName());
        System.out.println(removableClient.getClient().getName());
        System.out.println(this.ownRoom.getName());
        if((removableClient!=null) && (this.ownRoom.equals(admin.getClient())) ){
            clients.remove(removableClient);
            removableClient.kickedClient(this);
            broadcastKick(name,removableClient, admin);
            admin.sendMessage("Kamu sudah mengeluarkan " + removableClient.getClient().getName()+ " dari room");
        }else if(this.ownRoom.equals(admin)){
            admin.sendMessage("Orang yang ada ingin kick tidak ada didalam room");
        }else{
            admin.sendMessage("Kamu tidak memiliki hak untuk mengeluarkan orang");
        }
    }

    public ClientHandler cekClient(String targetClient) {
        String UUIDTarget = getClientUUID(targetClient);
        if(UUIDTarget.length()>=0){
            for (ClientHandler client : this.clients) {
                if (client.getClient().getUUID().equalsIgnoreCase(UUIDTarget)) {
                    return client;
                }
            }
        }
        return null;
    }

    private synchronized String getClientUUID(String name){
        for (ClientHandler client : this.clients) {
            if (client.getClient().getName().equalsIgnoreCase(name)) {
                return client.getClient().getUUID();
            }
        }
        return "";
    } 

    public synchronized void deletethisRoom(ClientHandler sender){
        if (this.ownRoom.equals(sender.getClient())){
            Iterator<ClientHandler> iterator = this.clients.iterator();
            while (iterator.hasNext()) {
                ClientHandler client = iterator.next();
                if (client != sender) {
                    iterator.remove(); // Menghapus elemen menggunakan iterator
                    client.leaveRoom(this);
                    client.sendMessage("Room telah dihapus oleh pemilik room " +sender.getClient().getName() );
                } else {
                    iterator.remove(); // Menghapus elemen menggunakan iterator
                    sender.leaveRoom(this);
                    sender.sendMessage("Room telah kamu hapus");
                }
            }
        } else {
            sender.sendMessage("Maaf tidak dapat mendelete room karena anda buka pemilik room");
        }
    }

    public String getRoomName() {
        return this.name;
    }

    public String getCodeRoom(){
        return this.roomCode;
    }

    
    

}
