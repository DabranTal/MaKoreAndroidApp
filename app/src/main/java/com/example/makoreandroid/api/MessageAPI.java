package com.example.makoreandroid.api;

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

    public void get(MessageListAdapter adapter) {
        Call<List<Message>> call = webServiceAPI.getMessages("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOlsidGFsIiwiMTIvMDYvMjAyMiAxMjowMToyOSJdLCJqdGkiOiI5MjExNjM0Ni1hZmNmLTQ4YzgtYTI4OS01ZmVhZDdlY2I2MDYiLCJOYW1lSWRlbnRpZmllciI6IklkbyIsImV4cCI6MTY1NTAzNjQ4OSwiaXNzIjoiQ29yYWwiLCJhdWQiOiJJZG8ifQ.il0-p7wOsOm5-cS0og6LIkFThDuH3qA5eB-lZhujgno");
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