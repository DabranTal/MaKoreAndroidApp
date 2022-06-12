package com.example.makoreandroid.api;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
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

    public void get() {
        Call<List<Message>> call = webServiceAPI.getMessages("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOlsidGFsIiwiMTIvMDYvMjAyMiAxMTo1NDowMCJdLCJqdGkiOiIxZjI5ZWM5My00NmVjLTQ4NTAtYWM1MS1iMTY1NWM2Njg0MzgiLCJOYW1lSWRlbnRpZmllciI6IklkbyIsImV4cCI6MTY1NTAzNjA0MCwiaXNzIjoiQ29yYWwiLCJhdWQiOiJJZG8ifQ.WL92sKOXbybn14-f1Q-xi8Pu1PwyqY_pGqR_Pmi-TX0");
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                List<Message> messages = response.body();
//                new Thread(() -> {
//                    dao.clear();
//                    dao.insertList(response.body());
//                    postListData.postValue(dao.get());
//                }).start();
//            }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}
