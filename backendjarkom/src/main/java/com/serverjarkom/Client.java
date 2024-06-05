package com.serverjarkom;

public class Client {
    private String UUID;
    private String name;
    private String usrname;
    private String passwoerd;

    public Client (String UUID ,String name){
        this.UUID = UUID;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getUUID(){
        return this.UUID;
    }

}
