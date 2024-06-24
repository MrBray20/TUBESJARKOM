package com.serverjarkom.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.serverjarkom.model.AuthMessage;
import com.serverjarkom.model.AuthResponseMessage;
import com.serverjarkom.model.CommadMessage;
import com.serverjarkom.model.AuthResponseMessage.AuthResponseData;

public class jsonHelper {
    
    private static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException{
        return gson.fromJson(json, classOfT);
    }

    public static String jsonRegister(String name,String username,String password,String confirmPassword) {
        AuthMessage authMessage = new AuthMessage(username, password, name);
        return toJson(authMessage);
    }

    public static String jsonLogin(String username,String password){
        AuthMessage authMessage = new AuthMessage(username, password);
        return toJson(authMessage);
    }

    public static String jsonCommand(String action, String command){
        CommadMessage commadMessage = new CommadMessage(action,command);
        return toJson(commadMessage);
    }

    public static String jsonCommandRoomList(String action, String command,Map<String,String> room){
        CommadMessage commadMessage = new CommadMessage(action,command);
        commadMessage.setRoom(room);
        return toJson(commadMessage);
    }

    public static String jsonResCommand(String action, String command){
        CommadMessage commadMessage = new CommadMessage(action,command);
        return toJson(commadMessage);
    }

    public static String jsonAuthResponseMessage(String status,String message){
        AuthResponseMessage authResponseMessage = new AuthResponseMessage(status, message);
        return toJson(authResponseMessage);
    }

    public static String jsonAuthResponseMessage(String status,String message, AuthResponseData data){
        AuthResponseMessage authResponseMessage = new AuthResponseMessage(status, message, data);
        return toJson(authResponseMessage);
    }

    public static AuthResponseMessage parserAuthResponse(String json) throws JsonSyntaxException{
        return fromJson(json,AuthResponseMessage.class);
        
    }

    public static CommadMessage parserCommadMessage(String json) throws JsonSyntaxException{
        return fromJson(json, CommadMessage.class);
    }

    public static AuthMessage parserAuthMessage(String json){
        return fromJson(json, AuthMessage.class);
    }

}
