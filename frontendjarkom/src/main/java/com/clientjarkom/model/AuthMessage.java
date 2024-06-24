package com.clientjarkom.model;

public class AuthMessage extends Message{
    private String username;
    private String password;
    private String name;
    private String action;


    public AuthMessage(String username, String password) {
        super("auth");
        this.username = username;
        this.password = password;
        this.action = "login";
    }

    public AuthMessage(String username, String password, String name) {
        super("auth");
        this.username = username;
        this.password = password;
        this.name = name;
        this.action = "register";
    }
    
    @Override
    public String getType(){
        return super.getType();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

}
