package com.clientjarkom.model;

import java.util.Map;

public class CommadMessage extends Message {
    
    private String action;
    private String message;
    private Map<String,String> rooms;

    

    public CommadMessage(String action,String message){
        super("command");
        this.action = action;
        this.message = message;
    }

    public CommadMessage(String action,String message,Map<String,String> room){
        super("command");
        this.action = action;
        this.message = message;
        this.rooms = room;
    }

    public void setCommad(String action) {
        this.action = action;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return super.getType();
    }

    public String getCommand() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public Map<String,String> getroom(){
        return this.rooms;
    }
}
