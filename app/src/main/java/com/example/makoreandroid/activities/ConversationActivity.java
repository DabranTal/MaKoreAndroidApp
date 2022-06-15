package com.example.makoreandroid.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.api.MessageAPI;
import com.example.makoreandroid.entities.Message;
import com.example.makoreandroid.jsonfiles.SendingMessageJson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConversationActivity extends AppCompatActivity {
    String token;
    String partnerName;
    RecyclerView lstMessages;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        partnerName = intent.getStringExtra("friendID");


        // get JWT
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token","");

        // init partner props bar
        TextView partnerNameTV = findViewById(R.id.partner_name);
        partnerNameTV.setText(partnerName);
        ImageView imageView = findViewById(R.id.partner_profile_image);
        imageView.setImageResource(intent.getIntExtra("friendAvatar", 0));

        // init messages RecyclerView
        lstMessages = findViewById(R.id.recycler_conversaion);
        adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));



        //get messages for conversation
        MessageAPI messageAPI = new MessageAPI();
        messageAPI.get(adapter, token, partnerName, lstMessages);

        //update local backup messages view
        List<Message> messages = new ArrayList<>();
        adapter.copyMessagesTo(messages);


        // on Click on send button
        FloatingActionButton btnSend = findViewById(R.id.button_send);
        btnSend.setOnClickListener(view->{
            EditText et = findViewById(R.id.typing_board);
            Message newMessage = new Message(3,et.getText().toString(),"21:40",true);
            messages.add(newMessage);
            // post request to save new message
            SendingMessageJson sendingMessageJson = new SendingMessageJson(
                    intent.getStringExtra("UserName"),partnerName, et.getText().toString());
            messageAPI.transferAndGet(sendingMessageJson,adapter, token,partnerName, lstMessages);
            // clean typing board
            et.setText("");
        });

        // on Click on back button
        FloatingActionButton btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(view-> finish());

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(activityReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registering BroadcastReceiver
        if (activityReceiver != null) {
            IntentFilter intentFilter = new IntentFilter("ACTION_ACTIVITY");
            registerReceiver(activityReceiver, intentFilter);
        }
    }

    BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //get messages for conversation
            MessageAPI messageAPI = new MessageAPI();
            messageAPI.get(adapter, token, partnerName, lstMessages);
        }
    };
}