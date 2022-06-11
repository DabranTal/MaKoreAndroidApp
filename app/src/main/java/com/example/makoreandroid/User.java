package com.example.makoreandroid;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey() @NonNull
    private String UserName;
    private String Password;
    private String NickName;
    //private List<Conversation> ConversationList

    public User(@NonNull String userName, String password, String nickName) {
        UserName = userName;
        Password = password;
        NickName = nickName;
    }

    public User() {
        UserName = "coral";
        Password = "1234";
        NickName = "corali";
    }

    public User(@NonNull String userName, String password) {
        UserName = userName;
        Password = password;
    }

    @NonNull
    public String getUserName() {
        return UserName;
    }

    public void setUserName(@NonNull String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}
