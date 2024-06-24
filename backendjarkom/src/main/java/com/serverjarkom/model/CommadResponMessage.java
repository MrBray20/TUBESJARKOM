package com.serverjarkom.model;

public class CommadResponMessage extends Message {
    
    private String action;
    private String message;
    

    public CommadResponMessage(String action,String message){
        super("rescommand");
        this.action = action;
        this.message = message;
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

    public String getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }
}

