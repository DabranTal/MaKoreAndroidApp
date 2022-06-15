package com.example.makoreandroid.api;

import android.util.Log;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.jsonfiles.FireBaseJson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FireBaseAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public FireBaseAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void setFireBaseToken(String username, String token) {
        FireBaseJson tokenJson = new FireBaseJson(username, token, "");
        Call<Void> call = webServiceAPI.addFirebaseToken(tokenJson);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("success", "setFireBaseToken succeed");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("failure", "failure", t);
            }
        });
    }
}
