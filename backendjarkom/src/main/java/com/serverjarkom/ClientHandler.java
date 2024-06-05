package com.serverjarkom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client ;
    private Room currentRoom;
    private Set<Room> roomsJoined = new HashSet<>();

public ClientHandler(Socket socket) throws IOException{
    this.socket=socket;
    out = new PrintWriter(socket.getOutputStream(),true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
}

    @Override
    public void run() {
        try {
            out.println("Silahkan masukkan nama");
            String nama = in.readLine();

            client = new Client(randomManager.getUUID(),nama);
            listJoinedRooms();
            String message;
            while ((message = in.readLine()) != null) {
                if(message.startsWith("/join ")){
                    if (currentRoom != null) {
                        sendMessage("Silahkan keluar room terlebih dahulu !!!");
                    }else{
                        joinRoom(message.substring(6));
                    }
                }else if (message.equalsIgnoreCase("/leave")){
                    leaveRoom();
                }else if(message.equalsIgnoreCase("/create")){
                    createRooom();
                }else if(message.equalsIgnoreCase("/exit")){
                    exit();
                }else if (message.equalsIgnoreCase("/thisroom")){
                    accessRoom();
                }else if(message.equalsIgnoreCase("/listrooms")){
                    listJoinedRooms();
                }else if((currentRoom != null) && message.equalsIgnoreCase("/people")){
                    showMember();
                }else if(message.startsWith("/kick ")){
                    if(currentRoom == null){
                        sendMessage("anda tidak berada dalam room jadi tidak bisa mengeluarkan orang");
                    }
                    else{
                        currentRoom.kick(message.substring(6),this);
                    }
                }else if(message.equals("/deleteroom")){
                    deleteRoom();
                }
                else if(currentRoom != null){
                    currentRoom.broadcast(message, this);
                }
            }
            
        } catch (IOException e ){
            // TODO:s
        }
        
    }



    public void deleteRoom(){
        if (currentRoom != null) {
            currentRoom.deletethisRoom(this);
        }
    }

    public void createRooom() throws IOException{
        out.println("Silahkan masukkan nama room");
        String roomName = in.readLine();
        boolean isNewRoom = Server.addRoom(roomName,this.client);
        currentRoom = Server.getRoom(roomName);
        currentRoom.addClient(this);
        roomsJoined.add(currentRoom);
        out.println("Memasuki room: " + roomName);
    }
    
    public void joinRoom(String roomName) {
        currentRoom = Server.getRoom(roomName);
        if(currentRoom != null){
            currentRoom.addClient(this);
            roomsJoined.add(currentRoom);
            out.println("Memasuki room: " + roomName);
        }else {
            out.println("Room tidak tidak ada");
        }
    }

    public void leaveRoom(){
        if(currentRoom != null){
            currentRoom.removeClient(this);
            roomsJoined.remove(currentRoom);
            sendMessage("Meninggalkan room " + currentRoom.getRoomName());
            currentRoom = null;
        }
    }

    public boolean kickedClient(Room room){
        if(this.roomsJoined.contains(room)){
            this.roomsJoined.remove(room);
            if(currentRoom.equals(room)){
                currentRoom = null;
            }
            sendMessage("Kamu dikeluarkan dari room:" + room.getRoomName());
            return true;
        }
        return false;
    }

    public void exit(){
        if(currentRoom != null){
            sendMessage("Keluar room " + currentRoom.getRoomName());
            currentRoom = null;
        }
    }

    public void leaveRoom(Room room){
        if (this.roomsJoined.contains(room)){
            if(currentRoom == room){
                roomsJoined.remove(room);
                currentRoom = null;
            }
        }
    }

    // private Set<Room> listJoinedRooms(ClientHandler client) {
    //     return this.roomsJoined;
    // }

    private void listJoinedRooms() {
        if (roomsJoined.isEmpty()) {
            out.println("Kamu belum masuk ke dalam room apapun.");
        } else {
            out.println("Room yang sudah kamu join:");
            for (Room room : roomsJoined) {
                out.println(room.getRoomName());
            }
        }
    }

    private void accessRoom(){
        if (currentRoom != null){
            out.println("Sekarang kamu berada pada room : " + currentRoom.getRoomName());
        }else{
            out.println("Kamu tidak didalam room");
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }


    public void showMember (){
        currentRoom.showMember(this);
    }
    

    public Client getClient(){
        return this.client;
    }
}
