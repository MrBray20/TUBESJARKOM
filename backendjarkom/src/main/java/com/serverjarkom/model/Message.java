package com.serverjarkom.model;

import java.io.Serializable;

public abstract class Message implements Serializable{
    private String type ;
    
    public Message(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
