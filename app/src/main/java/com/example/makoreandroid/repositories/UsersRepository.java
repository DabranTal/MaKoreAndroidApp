package com.example.makoreandroid.repositories;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.api.UserAPI;
import com.example.makoreandroid.entities.User;

public class UsersRepository {
    private UserAPI api;
    private String token;

    public UsersRepository() {
        api = new UserAPI();
    }

    public void getTokenLogin(String username, String password, AppCompatActivity activity) {
        api.loginUser(this, new User(username, password), activity);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
