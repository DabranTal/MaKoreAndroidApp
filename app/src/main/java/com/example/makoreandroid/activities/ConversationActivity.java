package com.example.makoreandroid.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.entities.Message;
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
        if (messages.size() > 1)
            lstMessages.scrollToPosition(messages.size() - 1);

        FloatingActionButton btnSend = findViewById(R.id.button_send);
        btnSend.setOnClickListener(view->{
            EditText et = findViewById(R.id.typing_board);
            messages.add(new Message(3,et.getText().toString(),"21:40",true));
            et.setText("");
            lstMessages.setAdapter(adapter);
            lstMessages.scrollToPosition(messages.size() - 1);
        });

    }
}