package com.example.makoreandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.makoreandroid.entities.RemoteUser;

import java.util.List;

@Dao
public interface RemoteUsersDao {

    @Query("SELECT * FROM remoteuser")
    List<RemoteUser>index();

    @Query("SELECT * FROM remoteuser WHERE userName = :userName")
    RemoteUser get(String userName);

    @Insert
    void insert(RemoteUser... remote);

    @Update
    void update(RemoteUser... remote);

    @Delete
    void delete(RemoteUser... remote);

}
