package com.example.makoreandroid;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.entities.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lstMessages = findViewById(R.id.recycler_conversaion);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(0, "hi", "20:40", true));
        messages.add(new Message(1, "friend", "20:42", true));
        messages.add(new Message(0, "hi", "20:43", true));
        messages.add(new Message(1, "friend", "20:44", true));
        messages.add(new Message(0, "hi", "20:45", true));
        messages.add(new Message(1, "friend", "20:46", true));
        messages.add(new Message(0, "hi", "20:40", true));
        messages.add(new Message(1, "friend", "20:42", true));
        adapter.setMessages(messages);

        FloatingActionButton btnSend = findViewById(R.id.button_send);
        btnSend.setOnClickListener(view->{
            EditText et = findViewById(R.id.typing_board);
            messages.add(new Message(3,et.getText().toString(),"21:40",true));
            et.setText("");
        });

    }
}