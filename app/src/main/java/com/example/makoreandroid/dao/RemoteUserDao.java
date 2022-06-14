package com.example.makoreandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.makoreandroid.entities.RemoteUser;

import java.util.List;
@Dao
public interface RemoteUserDao {

    @Query("SELECT * FROM remoteuser")
    List<RemoteUser> index();

    @Query("SELECT * FROM REMOTEUSER WHERE id = :id AND localuser = :localuser")
    List<RemoteUser> get(String id, String localuser);

    @Insert
    void insert(RemoteUser... remoteUsers);

    @Update
    void update(RemoteUser... remoteUsers);

    @Delete
    void delete(RemoteUser... remoteUsers);
}
