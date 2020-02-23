package com.team6.rideshare.util;

public class CurrentLogin {

    private CurrentLogin instance;
    public CurrentLogin getInstance() {
        if(instance == null) {
            instance = new CurrentLogin();
        }
        return instance;
    }

    private String username;

    private CurrentLogin() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
