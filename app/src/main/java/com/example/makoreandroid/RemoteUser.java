package com.example.makoreandroid;

public class RemoteUser {
    //private int Id;
    private String NickNam;
    private String UserName;
    private String Server;
    private String lastMessage;
    private String Time;
    //public Conversation Conversation;

    public RemoteUser(String nickNam, String UserName, String Server, String lastMessage, String Time) {
        this.NickNam = nickNam;
        this.UserName = UserName;
        this.Server = Server;
        this.lastMessage = lastMessage;
        this.Time = Time;
    }

    public String getTime() {
        return this.Time;
    }

    public String getUserName() {
        return this.UserName;
    }

    public String getNickNam() {
        return this.NickNam;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public String getServer() {
        return this.Server;
    }
}
