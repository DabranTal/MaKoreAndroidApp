package com.example.makoreandroid;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.makoreandroid.entities.RemoteUser;

@Database(entities = {RemoteUser.class}, version = 1)
public abstract class RemoteUserDB extends RoomDatabase {
    public abstract RemoteUsersDao RemoteUsersDao();
}
