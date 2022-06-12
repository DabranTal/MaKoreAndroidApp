package com.example.makoreandroid.api;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.RemoteUser;
import com.example.makoreandroid.entities.User;

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

    public void get() {
        User u = new User("Ido", "12341234a");
        Call <String> call2 = webServiceAPI.getJWT(u);
        Call<List<RemoteUser>> call = webServiceAPI.getContacts("Bearer " + call2);
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
