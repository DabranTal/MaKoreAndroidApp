package com.example.makoreandroid.entities;

import androidx.room.PrimaryKey;

import java.util.List;

public class Conversation {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private User user;
    private List<Message> Messages;
    private int RemoteUserId;
    private RemoteUser RemoteUser;

    public Conversation(User user, List<Message> messages, com.example.makoreandroid.entities.RemoteUser remoteUser) {
        this.user = user;
        Messages = messages;
        RemoteUser = remoteUser;
    }

    public int getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return Messages;
    }

    public void setMessages(List<Message> messages) {
        Messages = messages;
    }

    public int getRemoteUserId() {
        return RemoteUserId;
    }

    public void setRemoteUserId(int remoteUserId) {
        RemoteUserId = remoteUserId;
    }

    public com.example.makoreandroid.entities.RemoteUser getRemoteUser() {
        return RemoteUser;
    }

    public void setRemoteUser(com.example.makoreandroid.entities.RemoteUser remoteUser) {
        RemoteUser = remoteUser;
    }
}
