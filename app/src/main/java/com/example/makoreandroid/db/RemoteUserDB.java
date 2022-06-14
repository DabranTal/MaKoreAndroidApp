package com.example.makoreandroid.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.makoreandroid.dao.RemoteUserDao;
import com.example.makoreandroid.entities.RemoteUser;

@Database(entities = {RemoteUser.class}, version = 1)
public abstract class RemoteUserDB extends RoomDatabase {
    public abstract RemoteUserDao remoteUserDao();
    private static volatile  RemoteUserDB INSTANCE;

    static public RemoteUserDB getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RemoteUserDB.class,
                    "RemoteUserDB").allowMainThreadQueries().build();
        return INSTANCE;
    }

    static public RemoteUserDB getInstance() {
        return INSTANCE;
    }
}
