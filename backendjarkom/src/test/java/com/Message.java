package com;

import java.io.Serializable;

abstract class Message implements Serializable{
    protected String Type ;
    
    public String getType() {
        return Type;
    }
}
