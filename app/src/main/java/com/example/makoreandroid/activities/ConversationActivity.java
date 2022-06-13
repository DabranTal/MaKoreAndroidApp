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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();

        // get JWT
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");


        RecyclerView lstMessages = findViewById(R.id.recycler_conversaion);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        //init partner props bar
        TextView partnerName = findViewById(R.id.partner_name);
        partnerName.setText(intent.getStringExtra("friendID"));
        ImageView imageView = findViewById(R.id.partner_profile_image);
        imageView.setImageResource(intent.getIntExtra("friendAvatar", 0));

        //get messages for conversation
        List<Message> messages = new ArrayList<>();
        MessageAPI messageAPI = new MessageAPI();
        messageAPI.get(adapter, token, intent.getStringExtra("friendID"));
        adapter.setMessages(messages);
        if (messages.size() > 1)
            lstMessages.scrollToPosition(messages.size() - 1);


        // on Click on send button
        FloatingActionButton btnSend = findViewById(R.id.button_send);
        btnSend.setOnClickListener(view->{
            EditText et = findViewById(R.id.typing_board);
            Message newMessage = new Message(3,et.getText().toString(),"21:40",true);
            messages.add(newMessage);
            // post request to save new message
            String to = intent.getStringExtra("friendID");
            SendingMessageJson sendingMessageJson = new SendingMessageJson(
                    intent.getStringExtra("UserName"),to, et.getText().toString());
            messageAPI.transfer(sendingMessageJson);
            messageAPI.post(to, token,  et.getText().toString());

            // new get request for conversation
            messageAPI.get(adapter, token, intent.getStringExtra("friendID"));
            lstMessages.scrollToPosition(messages.size() - 1);

            // clean typing board
            et.setText("");
        });

        // on Click on back button
        FloatingActionButton btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(view->{
            Intent i = new Intent(getApplicationContext(), ContactActivity.class);
            i.putExtra("UserName",intent.getStringExtra("UserName"));
            startActivity(i);
        });

    }
}