package com.serverjarkom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.attribute.standard.JobName;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.serverjarkom.Server;
import com.serverjarkom.model.AuthMessage;
import com.serverjarkom.model.AuthResponseMessage;
import com.serverjarkom.model.ChatMessage;
import com.serverjarkom.model.CommadMessage;
import com.serverjarkom.model.AuthResponseMessage.AuthResponseData;
import com.serverjarkom.util.jsonHelper;
import com.serverjarkom.util.randomManager;

public class MessageHandler {
    private CommadMessage outCommadMessage;
    private ChatMessage ouChatMessage;
    private CommadMessage inCommadMessage;
    private ChatMessage inChatMessage;
    private Gson gson;
    private JsonObject jsonObject;
    private JsonParser jsonParser;
    private String type;
    private PrintWriter out;
    private BufferedReader in;
    

    public MessageHandler(PrintWriter out, BufferedReader in){
        this.gson = new Gson();
        this.out = out;
        this.in = in;
        
    }

    public ChatMessage getInChatMessage() {
        return inChatMessage;
    }
    public CommadMessage getInCommadMessage() {
        return inCommadMessage;
    }

    public String InMessage(String message){
        jsonObject = JsonParser.parseString(message).getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        
        if("command".equals(type)){
            inCommadMessage = gson.fromJson(message,CommadMessage.class);
            this.type = inCommadMessage.getType();
            return this.type;
        }else{
            inChatMessage = gson.fromJson(message, ChatMessage.class);
            this.type = inChatMessage.getType();
            return this.type;
        }
    }
    
    public String getType() {
        return this.type;
    }


    public void readLine() throws IOException{
        in.readLine();
    }

    public void handleMessage(String jsonMessage) throws SQLException{
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

    private String handleCommandMessage(String jsonMessage) {
        // TODO Auto-generated method stub
        JsonObject jsonObject = gson.fromJson(jsonMessage, JsonObject.class);
        String action = jsonObject.get("action").getAsString();

        switch (action) {
            case "create":
                CommadMessage commadMessage = jsonHelper.parserCommadMessage(jsonMessage);
                return commadMessage.getMessage();
            case "leave":
                
                break;
            case "join":
                
                break;
            case "exit":
                
                break;
            case "thisroom":
                
                break;
            case "listrooms":
                
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
                    return jsonHelper.jsonAuthResponseMessage("succes", "login",data);    
                }
            default:
                return jsonHelper.jsonAuthResponseMessage("error", "fail_login");
        }
    }

    public void sendMessage(String json){
        out.println(json);
    }

    
}
