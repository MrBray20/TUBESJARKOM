package com.clientjarkom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.print.attribute.standard.JobName;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.clientjarkom.model.AuthMessage;
import com.clientjarkom.model.AuthResponseMessage;
import com.clientjarkom.model.ChatMessage;
import com.clientjarkom.model.CommadMessage;
import com.clientjarkom.util.jsonHelper;

public class MessageHandler {
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

    public String getType() {
        return this.type;
    }


    public void readLine() throws IOException{
        in.readLine();
    }


    public Object handleMessage(String jsonMessage) {
        JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
        String type = jsonObject.get("type").getAsString();
        System.out.println(jsonMessage);
        switch (type) {
            case "auth":
                sendMessage(handleAuthMessage(jsonMessage));
                break;
            case "resAuth":
                return (handleAuthResponseMessage(jsonMessage));
            case "chat":
                handleChatMessage(jsonMessage);
                break;
            case "command":
                System.out.println("im here");
                return handleCommandMessage(jsonMessage);
            case "rescommand":
                handleCommandMessage(jsonMessage);
                break;
        }
        return null;
    
    
    }

    private void handleChatMessage(String jsonMessage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleChatMessage'");
    }

    private Object handleCommandMessage(String jsonMessage) {
        JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        String type = jsonObject.get("message").getAsString();

        switch (action) {
            case "listrooms":
                CommadMessage commadMessage = jsonHelper.parsercommandlistrooms(jsonMessage);
                return commadMessage;
        }
        return null;
    }

    private Object handleAuthResponseMessage(String jsonMessage) {
        JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        String type = jsonObject.get("message").getAsString();
        switch (status) {
            case "succes":
                switch (type) {
                    case "register":
                        AuthResponseMessage authResponseMessageRegister = jsonHelper.parserAuthResponse(jsonMessage);
                        return authResponseMessageRegister.getStatus();
                    case "login":
                        AuthResponseMessage authResponseMessageLogin = jsonHelper.parserAuthResponse(jsonMessage);
                        return authResponseMessageLogin.getData();
                }
                break;
            case "error":
                switch (type) {
                    case "fail_login":
                        AuthResponseMessage authResponseMessageFaildLogin = jsonHelper.parserAuthResponse(jsonMessage);
                        System.out.println(authResponseMessageFaildLogin.getMessage());
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
                        return authResponseMessageFaildLogin.getMessage();
                }
                break;
        }
        
        return null;
    }

    private String handleAuthMessage(String jsonMessage)  {
        JsonObject jsonObject = gson.fromJson(jsonMessage,JsonObject.class);
        String type = jsonObject.get("action").getAsString();
        return type;
    }

    public void sendMessage(String json){
        out.println(json);
    }
}
