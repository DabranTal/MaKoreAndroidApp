package com.example.makoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("NOTIFICATION_DATA", remoteMessage.getData() + "");

        Intent new_intent = new Intent();

        Bundle bundle = new Bundle();// use bundle if you want to pass data
        bundle.putString("msgBody", remoteMessage.getData().toString());
        new_intent.putExtra("msg", bundle);

        new_intent.setAction("ACTION_ACTIVITY");
        sendBroadcast(new_intent);
    }

}