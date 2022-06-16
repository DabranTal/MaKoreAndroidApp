package com.example.makoreandroid.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.api.MessageAPI;
import com.example.makoreandroid.dao.ImageUserDao;
import com.example.makoreandroid.dao.MessageDao;
import com.example.makoreandroid.db.ImageUserDB;
import com.example.makoreandroid.db.MessageDB;
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
    MessageDao dao;
    String UserName;
    String PartnerServer;
    NotificationManagerCompat notificationManager;
    private ImageUserDao IuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        partnerName = intent.getStringExtra("friendID");
        UserName = intent.getStringExtra("UserName");
        PartnerServer =intent.getStringExtra("friendServer");
        notificationManager = NotificationManagerCompat.from(this);

        // get JWT
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token","");

        // init partner props bar
        ImageUserDB IuDB = Room.databaseBuilder(getApplicationContext(), ImageUserDB.class, "ImageUserDB")
                .allowMainThreadQueries().build();
        IuDao = IuDB.imageUserDao();
        TextView partnerNameTV = findViewById(R.id.partner_name);
        partnerNameTV.setText(partnerName);
        ImageView imageView = findViewById(R.id.partner_profile_image);
        imageView.setImageBitmap(IuDao.get(partnerName).getProfilePic());

        // init messages RecyclerView
        lstMessages = findViewById(R.id.recycler_conversaion);
        adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        // init Room
        MessageDB db = Room.databaseBuilder(getApplicationContext(), MessageDB.class,
                "MessageDB").allowMainThreadQueries().build();
        dao = db.messageDao();
        adapter.setMessages(dao.get(partnerName, UserName));

        //get messages for conversation
        MessageAPI messageAPI = new MessageAPI(PartnerServer);
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
        if (btnBack != null) {
            btnBack.setOnClickListener(view-> finish());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration _newConfig) {
        super.onConfigurationChanged(_newConfig);
        if (_newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("coral", "land");
            // support landscape mode



        } else {
            Log.d("coral", "port");
        }
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
            //create notification
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("msg");
            if (msg.length() > 2) {
                msg = msg.substring(1,msg.length() - 1);
                String[] titleAndBody = msg.split("=");
                createNotificationChannel();
                String Msg = "Message";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Msg)
                        .setSmallIcon(R.drawable.avatar)
                        .setContentTitle(titleAndBody[0])
                        .setContentText(titleAndBody[1])
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                int notificationID = titleAndBody[0].hashCode();
                notificationManager.notify(notificationID, builder.build());
            }
            //get messages for conversation
            MessageAPI messageAPI = new MessageAPI(PartnerServer);
            messageAPI.get(adapter, token, partnerName, lstMessages, dao, UserName);
        }

        private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Message";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("Message", name, importance);

                NotificationManager notificationManager1 = getSystemService((NotificationManager.class));
                notificationManager1.createNotificationChannel(channel);
            }
        }
    };
}