package com.example.makoreandroid.repositories;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.api.UserAPI;
import com.example.makoreandroid.entities.User;

public class UsersRepository {
    private UserAPI api;

    public UsersRepository() {
        api = new UserAPI();
    }

    public void getTokenLogin(String username, String password, AppCompatActivity activity) {
        api.loginUser(new User(username, password), activity);
    }

    public void getTokenRegister(String username, String nickname, String password, AppCompatActivity activity) {
        api.registerGetTokenNewUser(new User(username, nickname, password), activity);
    }

}
