package com.clientjarkom.util;

import com.clientjarkom.model.AuthMessage;
import com.clientjarkom.model.AuthResponseMessage;
import com.clientjarkom.model.CommadMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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

    public static AuthResponseMessage parserAuthResponse(String json) throws JsonSyntaxException{
        return fromJson(json,AuthResponseMessage.class);
        
    }

    public static String commandCreate(String room){
        CommadMessage commadMessage = new CommadMessage("create", room);
        return toJson(commadMessage);
    }


    public static String commandlistrooms(String idClient){
        CommadMessage commadMessage = new CommadMessage("listrooms",idClient);
        return toJson(commadMessage);
    }

    public static CommadMessage parsercommandlistrooms(String json){
        return fromJson(json,CommadMessage.class);
    }

    public static String commandJoin(String idRoom){
        CommadMessage commadMessage = new CommadMessage("join", idRoom);
        return toJson(commadMessage);
    }
}
