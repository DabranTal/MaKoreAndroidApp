package com.example.makoreandroid.api;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

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
    //private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;


    public MessageAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    // get all messages for this partner conversation
    public void get(MessageListAdapter adapter, String token, String partnerName, RecyclerView view) {

        Call<List<Message>> call = webServiceAPI.getMessages(partnerName, "Bearer " + token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                List<Message> messages = response.body();
                adapter.setMessages(messages);
                if (adapter.getItemCount() > 1)
                    view.scrollToPosition(adapter.getItemCount() - 1);

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.w("failure", "failure", t);
            }
        });
    }

    // send message in conversation and get update right after
    public void transferAndGet(SendingMessageJson newMessage, MessageListAdapter adapter, String token, String partnerName, RecyclerView view) {
        Call<Void> call = webServiceAPI.transferMessage(newMessage);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                get(adapter, token, partnerName, view);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("failure", "failure", t);
            }
        });
    }
}