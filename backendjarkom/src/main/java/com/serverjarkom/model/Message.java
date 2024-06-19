package com.serverjarkom.model;

import java.io.Serializable;

abstract class Message implements Serializable{
    protected String type ;
    
    public String getType() {
        return type;
    }
}
