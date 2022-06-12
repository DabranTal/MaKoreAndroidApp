package com.example.makoreandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey()
    private int Id;
    private String Content;
    private String Time;
    private boolean Sent;


    public Message(int id, String content, String time, boolean sent) {
        Id = id;
        Content = content;
        Time = time;
        Sent = sent;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getId() {
        return Id;
    }

    public String getContent() {
        return Content;
    }

    public String getTime() {
        return Time;
    }

    public boolean isSent() {
        return Sent;
    }

}
