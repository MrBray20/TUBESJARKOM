package com.serverjarkom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.serverjarkom.Server;
import com.serverjarkom.model.AuthMessage;
import com.serverjarkom.model.AuthResponseMessage.AuthResponseData;
import com.serverjarkom.model.ChatMessage;
import com.serverjarkom.model.Client;
import com.serverjarkom.model.CommadMessage;
import com.serverjarkom.model.Room;
import com.serverjarkom.util.jsonHelper;
import com.serverjarkom.util.randomManager;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private Room currentRoom;
    private Set<Room> roomsJoined = new HashSet<>();
    private ChatMessage chatMessage;
    private CommadMessage commadMessage;
    private JsonObject jsonObject;
    private Gson gson;
    private MessageHandler messageHandler;

    public ClientHandler(Socket socket) throws IOException, SQLException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.messageHandler = new MessageHandler();
        // chatMessage = new ChatMessage();
        // commadMessage = new CommadMessage();
        // gson = new Gson();
        // handlerMessage = new HandlerMessage();
        // db = new DBhandler();
    }

    @Override
    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                messageHandler.handleMessage(clientMessage);
            }

            // out.println("Daftar/Login");
            // String context = in.readLine();
            // if (context.equalsIgnoreCase("daftar")) {
            //     out.println("Silahkan masukkan username");
            //     String username = in.readLine();
            //     out.println("Silahkan masukkan password");
            //     String password = in.readLine();
            //     out.println("Silahkan masukkan nama anda");
            //     String nama = in.readLine();
            //     String id = randomManager.getUUID();
            //     client = new Client(id, username, password, nama);
            //     db.dbRegister(id, username, password, nama);
            //     Server.clients.add(client);
            // } else {
            //     boolean credentials = false;
            //     while (!credentials) {
            //         out.println("Silahkan masukkan username");
            //         String username = in.readLine();
            //         out.println("Silahkan masukkan password");
            //         String password = in.readLine();

            //         for (int i = 0; i < Server.clients.size(); i++) {
            //             System.out.println(Server.clients.get(i).getUsername());
            //             if (Server.clients.get(i).getUsername().equals(username)
            //                     && Server.clients.get(i).getPassword().equals(password)) {
            //                 credentials = true;
            //                 client = Server.clients.get(i);
            //                 ResultSet joinedRoomData = db.dbListJoinedRoom(client.getUUID());
            //                 while (joinedRoomData.next()) {
            //                     roomsJoined.add(Server.getRoom(joinedRoomData.getString(1)));
            //                 }
            //             }
            //         }

            //         if (!credentials) {
            //             out.println("Username atau password salah!");
            //         }
            //     }
            // }

            // out.println("Silahkan masukkan nama");
            // String nama = in.readLine();


            

            listJoinedRooms();
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/join ")) {
                    if (currentRoom != null) {
                        sendMessage("Silahkan keluar room terlebih dahulu !!!");
                    } else {
                        joinRoom(message.substring(6));
                    }
                } else if (message.equalsIgnoreCase("/leave")) {
                    leaveRoom();
                } else if (message.equalsIgnoreCase("/create")) {
                    // createRooom();
                } else if (message.equalsIgnoreCase("/exit")) {
                    exit();
                } else if (message.equalsIgnoreCase("/thisroom")) {
                    accessRoom();
                } else if (message.equalsIgnoreCase("/listrooms")) {
                    listJoinedRooms();
                } else if ((currentRoom != null) && message.equalsIgnoreCase("/people")) {
                    showMember();
                } else if (message.startsWith("/kick ")) {
                    if (currentRoom == null) {
                        sendMessage("anda tidak berada dalam room jadi tidak bisa mengeluarkan orang");
                    } else {
                        currentRoom.kick(message.substring(6), this);
                    }
                } else if (message.equals("/deleteroom")) {
                    deleteRoom();
                } else if (currentRoom != null) {
                    currentRoom.broadcast(message, this);
                }
            }

        } catch (IOException e) {
            // TODO:s
        } 
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void deleteRoom() throws SQLException {
        if (currentRoom != null) {
            Server.db.dbDeleteRoom(this.currentRoom.getRoomName());
            Server.deleteRoom(currentRoom.getRoomName());
            currentRoom.deletethisRoom(this);

        }
    }

    public void createRooom(String roomName) throws IOException, SQLException {
        // out.println("Silahkan masukkan nama room");
        // String roomName = in.readLine();
        boolean isNewRoom = Server.addRoom(roomName, this.client);
        currentRoom = Server.getRoom(roomName);
        currentRoom.addClient(this);
        roomsJoined.add(currentRoom);
        Server.db.dbCreateRoom(roomName, randomManager.codeRoom(), this.client.getUUID());
        out.println(jsonHelper.jsonCommand("rescreate", roomName));
    }

    public void joinRoom(String roomName) throws SQLException {
        currentRoom = Server.getRoom(roomName);
        if (currentRoom != null) {
            currentRoom.addClient(this);
            roomsJoined.add(currentRoom);
            if (!roomsJoined.contains(currentRoom))
                Server.db.dbJoinRoom(roomName, this.client.getUUID());
            out.println("Memasuki room: " + roomName);
        } else {
            out.println("Room tidak tidak ada");
        }
    }

    public void leaveRoom() throws SQLException {
        if (currentRoom != null) {
            currentRoom.removeClient(this);
            roomsJoined.remove(currentRoom);
            sendMessage("Meninggalkan room " + currentRoom.getRoomName());
            Server.db.dbLeaveRoom(currentRoom.getRoomName(), this.client.getUUID());
            currentRoom = null;
        }
    }

    public boolean kickedClient(Room room) {
        if (this.roomsJoined.contains(room)) {
            this.roomsJoined.remove(room);
            if (currentRoom.equals(room)) {
                currentRoom = null;
            }
            sendMessage("Kamu dikeluarkan dari room:" + room.getRoomName());

            return true;
        }
        return false;
    }

    public void exit() {
        if (currentRoom != null) {
            sendMessage("Keluar room " + currentRoom.getRoomName());
            currentRoom = null;
        }
    }

    public void leaveRoom(Room room) {
        if (this.roomsJoined.contains(room)) {
            if (currentRoom == room) {
                roomsJoined.remove(room);
                currentRoom = null;
            }
        }
    }

    // private Set<Room> listJoinedRooms(ClientHandler client) {
    // return this.roomsJoined;
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

    private void accessRoom() {
        if (currentRoom != null) {
            out.println("Sekarang kamu berada pada room : " + currentRoom.getRoomName());
        } else {
            out.println("Kamu tidak didalam room");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void showMember() {
        currentRoom.showMember(this);
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    class MessageHandler {
        private Gson gson;
        private JsonObject jsonObject;
        
    
        public MessageHandler(){
            this.gson = new Gson();
        }
    
        public void handleMessage(String jsonMessage) throws SQLException, IOException{
            JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
            String type = jsonObject.get("type").getAsString();
            System.out.println(jsonMessage);
            switch (type) {
                case "auth":
                    String json = handleAuthMessage(jsonMessage);
                    System.out.println(json);
                    sendMessage(json);
                    break;
                case "resAuth":
                    handleAuthResponseMessage(jsonMessage);
                    break;
                case "chat":
                    handleChatMessage(jsonMessage);
                    break;
                case "command":
                    handleCommandMessage(jsonMessage);
                    break;
                default:
                    break;
            }
        
        
        }
    
        private void handleChatMessage(String jsonMessage) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'handleChatMessage'");
        }
    
        private String handleCommandMessage(String jsonMessage) throws IOException, SQLException {
            // TODO Auto-generated method stub
            JsonObject jsonObject = gson.fromJson(jsonMessage, JsonObject.class);
            String action = jsonObject.get("action").getAsString();
            String message = jsonObject.get("message").getAsString();
            switch (action) {
                case "create":
                    CommadMessage commadMessage = jsonHelper.parserCommadMessage(jsonMessage);
                    createRooom(commadMessage.getMessage());
                case "leave":
                    
                    break;
                case "join":
                    
                    joinRoom(message);
                    break;
                case "exit":
                    
                    break;
                case "thisroom":
                    
                    break;
                case "listrooms":
                    ResultSet setRoom = Server.db.dbListJoinedRoom(message);
                    Map<String,String> room = new HashMap<>();

                    while (setRoom.next()) {
                        room.put(setRoom.getString("id_room"), setRoom.getString("name_room"));
                    }
                    String json = jsonHelper.jsonCommandRoomList(action, message,room);
                    sendMessage(json);
                    break;
                case "kick":
                    
                    break;
                case "deleteroom":
                    
                    break;
                
                default:
                    break;
            }
            return null;
        }
    
    
        private void handleAuthResponseMessage(String jsonMessage) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'handleAuthResponseMessage'");
        }
    
        private String handleAuthMessage(String jsonMessage) throws SQLException {
            JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
            String type = jsonObject.get("action").getAsString();
            switch (type) {
                case "register":
                    AuthMessage authMessageRegister = jsonHelper.parserAuthMessage(jsonMessage);
                    Server.db.dbRegister(randomManager.getUUID(), authMessageRegister.getUsername(), authMessageRegister.getPassword(), authMessageRegister.getName());
                    return jsonHelper.jsonAuthResponseMessage("succes", "register");
                case "login":
                    AuthMessage authMessageLogin = jsonHelper.parserAuthMessage(jsonMessage);
                    ResultSet client =Server.db.dbLogin(authMessageLogin.getUsername(), authMessageLogin.getPassword());
                    if(client.next()){
                        AuthResponseData data = new AuthResponseData(client.getString(2), client.getString(1));
                        Client clientnew = new Client(client.getString(1), client.getString(2), client.getString(3), client.getString(4));
                        setClient(clientnew);
                        return jsonHelper.jsonAuthResponseMessage("succes", "login",data);    
                    }
                default:
                    return jsonHelper.jsonAuthResponseMessage("error", "fail_login");
            }
        }
        
    }
    
    













}


