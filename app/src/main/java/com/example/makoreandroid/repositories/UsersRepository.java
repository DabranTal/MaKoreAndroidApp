package com.example.makoreandroid.repositories;
import com.example.makoreandroid.api.UserAPI;
import com.example.makoreandroid.entities.User;

public class UsersRepository {
    private UserAPI api;
    private String token;

    public UsersRepository() {
        api = new UserAPI();
    }

    public String getTokenLogin(String username, String password) {
        return api.loginUser(this, new User(username, password));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
