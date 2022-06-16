package com.example.makoreandroid.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ServerAPI(String newServer) {
        String baseUrl = "http://10.0.2.2:";
        if (newServer.startsWith("localhost:")) {
            baseUrl += newServer.replace("localhost:", "");
            baseUrl += "/api/";
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }



}
