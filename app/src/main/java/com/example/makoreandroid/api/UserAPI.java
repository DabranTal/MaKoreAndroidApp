package com.example.makoreandroid.api;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.activities.ContactActivity;
import com.example.makoreandroid.entities.User;
import com.example.makoreandroid.repositories.UsersRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    String token;

    public UserAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    public void loginUser(UsersRepository usersRepository, User u,  AppCompatActivity activity) {
        Call<String> call = webServiceAPI.loginGetToken(u);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //usersRepository.setToken(response.body());
                if (response.raw().code() == 200) {
                    token = response.body();
                    Intent intent = new Intent(activity, ContactActivity.class);
                    intent.putExtra("UserName", u.getUserName());
                    activity.startActivity(intent);
                } else {
                    Log.e("Hemi", "hemi");
                    //activity.binding.loginError.setText(R.string.login_invalid_details);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //usersRepository.setToken(null);
            }
        });
    }
}


