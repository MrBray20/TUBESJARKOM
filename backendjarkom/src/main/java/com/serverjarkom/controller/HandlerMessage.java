package com.serverjarkom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.serverjarkom.model.ChatMessage;
import com.serverjarkom.model.CommadMessage;

public class HandlerMessage {
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

    public HandlerMessage(){
        this.outCommadMessage = new CommadMessage();
        this.ouChatMessage = new ChatMessage();
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
}
