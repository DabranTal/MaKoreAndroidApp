package com.example.makoreandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView  lstMessages= findViewById(R.id.recycler_conversaion);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(0,"hi","20:41",true));
        messages.add(new Message(1,"friend","20:41",true));
        adapter.setMessages(messages);

    }
}