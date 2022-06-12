package com.example.makoreandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Content;
    private String Time;
    private boolean Sent;


    public Message(String content, String time, boolean sent) {
        Content = content;
        Time = time;
        Sent = sent;
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
