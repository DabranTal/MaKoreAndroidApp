package com.example.makoreandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.makoreandroid.entities.ImageUser;

import java.util.List;

@Dao
public interface ImageUserDao {

    @Query("SELECT * FROM imageuser")
    List<ImageUser> index();

    @Query("SELECT * FROM imageuser WHERE name = :name")
    ImageUser get(String name);

    @Update
    void update(ImageUser... imageUsers);

    @Insert
    void insert(ImageUser... imageUsers);

    @Delete
    void delete(ImageUser... imageUsers);

}
