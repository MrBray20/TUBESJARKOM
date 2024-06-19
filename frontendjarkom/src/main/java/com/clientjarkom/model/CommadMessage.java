package com.clientjarkom.model;

public class CommadMessage extends Message {
    
    private String command;
    private String message;

    public CommadMessage(){
        this.type = "command";
    }

    public void setCommad(String command) {
        this.command = command;
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
        return command;
    }

    public String getMessage() {
        return message;
    }
}
