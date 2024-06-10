package com.serverjarkom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private Room currentRoom;
    private dbHandler db;
    private Set<Room> roomsJoined = new HashSet<>();

    public ClientHandler(Socket socket) throws IOException, SQLException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        db = new dbHandler();
    }

    @Override
    public void run() {
        try {
            out.println("Daftar/Login");
            String context = in.readLine();
            if (context.equalsIgnoreCase("daftar")) {
                out.println("Silahkan masukkan username");
                String username = in.readLine();
                out.println("Silahkan masukkan password");
                String password = in.readLine();
                out.println("Silahkan masukkan nama anda");
                String nama = in.readLine();
                String id = randomManager.getUUID();
                client = new Client(id, username, password, nama);
                db.dbRegister(id, username, password, nama);
                Server.clients.add(client);
            } else {
                boolean credentials = false;
                while (!credentials) {
                    out.println("Silahkan masukkan username");
                    String username = in.readLine();
                    out.println("Silahkan masukkan password");
                    String password = in.readLine();

                    for (int i = 0; i < Server.clients.size(); i++) {
                        System.out.println(Server.clients.get(i).getUsername());
                        if (Server.clients.get(i).getUsername().equals(username)
                                && Server.clients.get(i).getPassword().equals(password)) {
                            credentials = true;
                            client = Server.clients.get(i);
                            ResultSet joinedRoomData = db.dbListJoinedRoom(client.getUUID());
                            while (joinedRoomData.next()) {
                                roomsJoined.add(Server.getRoom(joinedRoomData.getString(1)));
                            }
                        }
                    }

                    if (!credentials) {
                        out.println("Username atau password salah!");
                    }
                }
            }

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
                    createRooom();
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void deleteRoom() throws SQLException {
        if (currentRoom != null) {
            currentRoom.deletethisRoom(this);
            db.dbDeleteRoom(this.currentRoom.getRoomName());
        }
    }

    public void createRooom() throws IOException, SQLException {
        out.println("Silahkan masukkan nama room");
        String roomName = in.readLine();
        boolean isNewRoom = Server.addRoom(roomName, this.client);
        currentRoom = Server.getRoom(roomName);
        currentRoom.addClient(this);
        roomsJoined.add(currentRoom);
        db.dbCreateRoom(roomName, "AHDSX", this.client.getUUID());
        out.println("Memasuki room: " + roomName);
    }

    public void joinRoom(String roomName) throws SQLException {
        currentRoom = Server.getRoom(roomName);
        if (currentRoom != null) {
            currentRoom.addClient(this);
            roomsJoined.add(currentRoom);
            if (!roomsJoined.contains(currentRoom))
                db.dbJoinRoom(roomName, this.client.getUUID());
            out.println("Memasuki room: " + roomName);
        } else {
            out.println("Room tidak tidak ada");
        }
    }

    public void leaveRoom() throws SQLException {
        if (currentRoom != null) {
            currentRoom.removeClient(this);
            roomsJoined.remove(currentRoom);
            db.dbJoinRoom(currentRoom.getRoomName(), this.client.getName());
            sendMessage("Meninggalkan room " + currentRoom.getRoomName());
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
}
