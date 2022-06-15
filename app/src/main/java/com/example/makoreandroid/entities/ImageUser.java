package com.example.makoreandroid.entities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ImageUser {
    @PrimaryKey
    @NonNull
    String name;
    Bitmap profilePic;

}
