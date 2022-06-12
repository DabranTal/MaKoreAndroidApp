package com.example.makoreandroid.activities;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.api.WebServiceAPI;
import com.example.makoreandroid.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundService extends IntentService {
    public BackgroundService() {
        super("BackgroundService");
        Log.d("coral", "BackgroundService:v ");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("coral", "here");
        User user = new User(intent.getExtras().get("username").toString(),
                intent.getExtras().get("password").toString());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WebServiceAPI wsAPI = retrofit.create(WebServiceAPI.class);
        Call<String> call = wsAPI.loginGetToken(user);

        try {
            Response<String> response = call.execute();
        } catch (IOException e) {
            Log.d("coral", e.getMessage());
        }
    }
}
