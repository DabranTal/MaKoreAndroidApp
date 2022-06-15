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

    public ImageUser(@NonNull String name, Bitmap profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

}
