package com.example.makoreandroid.api;

import com.example.makoreandroid.entities.Message;
import com.example.makoreandroid.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("contacts/Tal/messages")
    Call<List<Message>> getMessages();

    @POST("messages")
    Call<Void> createMessage(@Body Message message);

    @DELETE("messages/{id}")
    Call<Void> deleteMessage(@Path("id") int id);



    @POST("connection/login")
    Call<String> loginGetToken(@Body User user);

    @POST("contacts")
    Call<Void> createUser(@Body User user);

    @DELETE("contacts/{id}")
    Call<Void> deleteUser(@Path(value = "username", encoded = true) String username);

}
