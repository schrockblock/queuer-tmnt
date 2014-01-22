package com.tmnt.queuer.models;

/**
 * Created by billzito on 1/19/14.
 */
public class SignInModel {
    private String username;
    private String password;

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
