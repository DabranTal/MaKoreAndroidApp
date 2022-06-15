package com.example.makoreandroid.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.activities.ContactActivity;
import com.example.makoreandroid.entities.User;
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
    SharedPreferences prefs;


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

    public void loginUser(User u, AppCompatActivity activity) {
        Call<String> call = webServiceAPI.loginGetToken(u);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // User exists - server approved !
                if (response.raw().code() == 200) {
                    // first get the JWT and transfer the client to the chats page
                    token = response.body();
                    Intent intent = new Intent(activity, ContactActivity.class);
                    intent.putExtra("UserName", u.getUserName());
                    activity.startActivity(intent);

                    // saving the JWT token for future interaction with the server
                    SharedPreferences.Editor edit;
                    prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    edit = prefs.edit();
                    try {
                        String saveToken = response.body();
                        edit.putString("token",saveToken);
                        Log.i("Login",saveToken);
                        edit.commit();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    // Wrong details - server didn't approve
                    TextView error = activity.findViewById(R.id.login_error);
                    error.setText(activity.getString(R.string.login_invalid_details));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }



    public void registerGetTokenNewUser(User u,  AppCompatActivity activity) {
        Call<String> call = webServiceAPI.registerGetTokenNewUser(u);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // New and legit user !!
                if (response.raw().code() == 200) {
                    // first get the JWT and transfer the client to the chats page
                    token = response.body();
                    Intent intent = new Intent(activity, ContactActivity.class);
                    intent.putExtra("UserName", u.getUserName());
                    activity.startActivity(intent);

                    // saving the JWT token for future interaction with the server
                    SharedPreferences.Editor edit;
                    prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    edit = prefs.edit();
                    try {
                        String saveToken = response.body();
                        edit.putString("token",saveToken);
                        Log.i("Register",saveToken);
                        edit.commit();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    // Server says the Username is already taken !
                    TextView error = activity.findViewById(R.id.register_error);
                    error.setText(activity.getString(R.string.register_username_taken));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


}


