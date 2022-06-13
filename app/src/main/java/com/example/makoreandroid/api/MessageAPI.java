package com.example.makoreandroid.api;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.entities.Message;
import com.example.makoreandroid.jsonfiles.SendingMessageJson;

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

    public void get(MessageListAdapter adapter, String token, String partnerName) {

        Call<List<Message>> call = webServiceAPI.getMessages(partnerName, "Bearer " + token);
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

    public void post(String partnerName, String token, String content) {
        Call<Void> call = webServiceAPI.createMessage(partnerName, "Bearer " + token, content);
    }
    public void transfer(SendingMessageJson newMessage) {
        Call<Void> call = webServiceAPI.transferMessage(newMessage);
    }
}