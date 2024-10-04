package com.example.demo.response;

public class LoginResponse {
    private String token;
    private String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getUser() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(String user) {
        this.token = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
