package com.serverjarkom.model;

public class ChatMessage extends Message{
    private String room;
    private String message;
    private String sender;
    private String timeStamp ;

    public ChatMessage(){
        this.type =  "chat";
    }
    
    public void setMessage(String room, String message, String sender, String timeStamp) {
        this.room = room;
        this.message = message;
        this.sender = sender;
        this.timeStamp = timeStamp;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return super.getType();
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getRoom() {
        return room;
    }

}
