package com.example.makoreandroid.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.makoreandroid.R;

@Entity(primaryKeys = {"id", "localuser"})
public class RemoteUser {
    private int avatar = R.drawable.avatar;
    private String name;
    @NonNull
    private String id;
    private String server;
    private String last;
    private String lastdate;

    public String getLocaluser() {
        return localuser;
    }
    @NonNull
    private String localuser;



    public RemoteUser(String name, @NonNull String UserName, String last, String lastdate, String Server) {
        this.name = name;
        this.id = UserName;
        this.server = Server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public RemoteUser() {}


    public void setLocaluser(String s) {
        this.localuser = s;
    }

    public int getAvatar() {
        return this.avatar;
    }

    public String getLastdate() {
        return this.lastdate;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLast() {
        return this.last;
    }

    public String getServer() {
        return this.server;
    }

    public void setAvatar(int i) {
        this.avatar = i;
    }

    public void setName(String nick) {
        this.name = nick;
    }

    public void setId(@NonNull String user) {
        this.id = user;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }




}