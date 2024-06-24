package com.clientjarkom.model;

public class Client {
    private String session;
    private String username;

    public Client(){
        
    }

    public void setClient(String username,String session) {
        this.session = session;
        this.username = username;
    }

    public String getUUID() {
        return this.session;
    }

    public String getUsername() {
        return this.username;
    }

}
