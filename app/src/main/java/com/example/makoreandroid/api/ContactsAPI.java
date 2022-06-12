package com.example.makoreandroid.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.RemoteUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    //private MutableLiveData<List<Message>> postListData;
    //private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    //    public MessageAPI(MutableLiveData<List<Message>> postListData, PostDao dao) {
//        this.postListData = postListData;
//        this.dao = dao;
    public ContactsAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(AppCompatActivity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        Call<List<RemoteUser>> call = webServiceAPI.getContacts("Bearer " + token);
        call.enqueue(new Callback<List<RemoteUser>>() {
            @Override
            public void onResponse(Call<List<RemoteUser>> call, Response<List<RemoteUser>> response) {

                List<RemoteUser> remotes = response.body();
//                new Thread(() -> {
//                    dao.clear();
//                    dao.insertList(response.body());
//                    postListData.postValue(dao.get());
//                }).start();
//            }
            }

            @Override
            public void onFailure(Call<List<RemoteUser>> call, Throwable t) {

            }
        });
    }
}
