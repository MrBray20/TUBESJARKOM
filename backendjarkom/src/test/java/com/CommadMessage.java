package com;

public class CommadMessage extends Message {
    
    private String message;

    public CommadMessage(){
        this.Type = "command";
    }

    public void setCommad(String message) {
        this.message = message;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return super.getType();
    }

    public String getCommand() {
        return message;
    }
}
