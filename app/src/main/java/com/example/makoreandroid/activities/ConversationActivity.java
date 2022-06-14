package com.example.makoreandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.api.MessageAPI;
import com.example.makoreandroid.dao.MessageDao;
import com.example.makoreandroid.db.MessageDB;
import com.example.makoreandroid.entities.Message;
import com.example.makoreandroid.jsonfiles.SendingMessageJson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        String partnerName = intent.getStringExtra("friendID");
        String UserName = intent.getStringExtra("UserName");


        // get JWT
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");

        // init partner props bar
        TextView partnerNameTV = findViewById(R.id.partner_name);
        partnerNameTV.setText(partnerName);
        ImageView imageView = findViewById(R.id.partner_profile_image);
        imageView.setImageResource(intent.getIntExtra("friendAvatar", 0));

        // init messages RecyclerView
        RecyclerView lstMessages = findViewById(R.id.recycler_conversaion);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        // init Room
        MessageDB db = Room.databaseBuilder(getApplicationContext(), MessageDB.class,
                "MessageDB").allowMainThreadQueries().build();
        MessageDao dao = db.messageDao();
        adapter.setMessages(dao.get(partnerName, UserName));

        //get messages for conversation
        MessageAPI messageAPI = new MessageAPI();
        messageAPI.get(adapter, token, partnerName, lstMessages, dao, UserName);

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
            messageAPI.transferAndGet(sendingMessageJson,adapter, token,partnerName, lstMessages,
                    dao, UserName, newMessage);
            // clean typing board
            et.setText("");
        });

        // on Click on back button
        FloatingActionButton btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(view-> finish());

    }
}