package com.serverjarkom;

public class Client {
    private String UUID;
    private String name;
    private String username;
    private String password;

    public Client(String UUID, String username, String password, String name) {
        this.UUID = UUID;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getUUID() {
        return this.UUID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
