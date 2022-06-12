package com.example.makoreandroid.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.makoreandroid.R;

@Entity
public class RemoteUser {
    private int avatar = R.drawable.avatar;
    private String nickName;
    @PrimaryKey
    @NonNull
    private String userName;
    private String server;
    private String lastMessage;
    private String time;

    public String getLocalUser() {
        return localUser;
    }

    public void setLocalUser(String localUser) {
        this.localUser = localUser;
    }

    private String localUser;
    //public Conversation Conversation;


    public RemoteUser(String nickName, @NonNull String UserName, String lastMessage, String Time, String Server) {
        this.nickName = nickName;
        this.userName = UserName;
        this.server = Server;
        this.lastMessage = lastMessage;
        this.time = Time;
    }

    public RemoteUser() {}

    public int getAvatar() {
        return this.avatar;
    }

    public String getTime() {
        return this.time;
    }

    @NonNull
    public String getUserName() {
        return this.userName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public String getServer() {
        return this.server;
    }

    public void setAvatar(int i) {
        this.avatar = i;
    }

    public void setNickName(String nick) {
        this.nickName = nick;
    }

    public void setUserName(@NonNull String user) {
        this.userName = user;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTime(String time) {
        this.time = time;
    }




}