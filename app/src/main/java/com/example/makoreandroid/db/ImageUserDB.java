package com.example.makoreandroid.db;

import androidx.room.Database;

import com.example.makoreandroid.dao.ImageUserDao;
import com.example.makoreandroid.entities.ImageUser;

@Database(entities = {ImageUser.class}, version = 1)
public abstract class ImageUserDB {
    public abstract ImageUserDao imageUserDao();
}
