package com.example.makoreandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.makoreandroid.dao.ImageUserDao;
import com.example.makoreandroid.entities.Converters;
import com.example.makoreandroid.entities.ImageUser;

@Database(entities = {ImageUser.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ImageUserDB extends RoomDatabase {
    public abstract ImageUserDao imageUserDao();
}
