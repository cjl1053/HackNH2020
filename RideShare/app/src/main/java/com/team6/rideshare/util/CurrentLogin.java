package com.team6.rideshare.util;

public class CurrentLogin {

    private static CurrentLogin instance;
    public static CurrentLogin getInstance() {
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
