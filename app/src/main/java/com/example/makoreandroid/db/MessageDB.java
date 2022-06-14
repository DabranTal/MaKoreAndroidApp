package com.example.makoreandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.makoreandroid.dao.MessageDao;
import com.example.makoreandroid.entities.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageDB extends RoomDatabase {
    public abstract MessageDao messageDao();
}
