package com.clientjarkom.model;

import java.util.Map;

import java.util.Map;

public class CommadMessage extends Message {
    
    private String action;
    private String message;
    private Map<String,String> room; // List untuk menyimpan daftar ruangan

    public CommadMessage(String action,String message){
        super("command");
        this.action = action;
        this.message = message;
    }

    public void setCommad(String action) {
        this.action = action;
    }

    public void setRoom(Map<String,String> roomList){
        this.room = roomList;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return super.getType();
    }

    public String getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public Map<String,String> getRoom(){
        return this.room;
    }
}

