package com.example.makoreandroid.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    //private MutableLiveData<List<Message>> postListData;
    //private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    //    public MessageAPI(MutableLiveData<List<Message>> postListData, PostDao dao) {
//        this.postListData = postListData;
//        this.dao = dao;
    public MessageAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(MessageListAdapter adapter, AppCompatActivity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        Call<List<Message>> call = webServiceAPI.getMessages("Bearer " + token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                List<Message> messages = response.body();
                adapter.setMessages(messages);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}