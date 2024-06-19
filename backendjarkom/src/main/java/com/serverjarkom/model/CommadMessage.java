package com.serverjarkom.model;
public class CommadMessage extends Message {
    
    private String commad;
    private String message;

    public CommadMessage(){
        this.type = "command";
    }

    public void setCommad(String commad) {
        this.commad = commad;
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
        return commad;
    }

    public String getMessage() {
        return message;
    }
}
