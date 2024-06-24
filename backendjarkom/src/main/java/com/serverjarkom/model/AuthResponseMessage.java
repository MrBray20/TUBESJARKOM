package com.serverjarkom.model;

public class AuthResponseMessage extends Message {
    private String status;
    private String message;
    private AuthResponseData data;

    public AuthResponseMessage(String status, String message, AuthResponseData data) {
        super("resAuth");
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public AuthResponseMessage(String status, String message){
        super("resAuth");
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AuthResponseData getData() {
        return data;
    }

    public static class AuthResponseData {
        private String username;
        private String sessionToken;

        public AuthResponseData(String username, String sessionToken) {
            this.username = username;
            this.sessionToken = sessionToken;
        }

        public String getUsername() {
            return username;
        }

        public String getSessionToken() {
            return sessionToken;
        }
    }
}
