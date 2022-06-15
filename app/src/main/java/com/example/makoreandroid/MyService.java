package com.example.makoreandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent new_intent = new Intent();

        // use bundle to pass data
        Bundle bundle = new Bundle();
        //bundle.putString("msgBody", remoteMessage.getData().toString());
        //bundle.putString("content","PP");
        new_intent.putExtra("msg", remoteMessage.getData().toString());
        new_intent.setAction("ACTION_ACTIVITY");
        sendBroadcast(new_intent);
    }

}