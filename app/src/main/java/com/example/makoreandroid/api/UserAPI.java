package com.example.makoreandroid.api;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
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
    public String loginUser(UsersRepository usersRepository, User u) {
        Call<String> call = webServiceAPI.loginGetToken(u);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //usersRepository.setToken(response.body());
                token = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //usersRepository.setToken(null);
                token = null;
            }
        });
        return this.token;
    }
}


