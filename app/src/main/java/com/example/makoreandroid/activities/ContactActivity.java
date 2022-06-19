package com.example.makoreandroid.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.CustomListAdapter;
import com.example.makoreandroid.adapters.MessageListAdapter;
import com.example.makoreandroid.api.ContactsAPI;
import com.example.makoreandroid.api.FireBaseAPI;
import com.example.makoreandroid.api.MessageAPI;
import com.example.makoreandroid.dao.ImageUserDao;
import com.example.makoreandroid.dao.MessageDao;
import com.example.makoreandroid.dao.RemoteUserDao;
import com.example.makoreandroid.db.ImageUserDB;
import com.example.makoreandroid.db.MessageDB;
import com.example.makoreandroid.db.RemoteUserDB;
import com.example.makoreandroid.entities.ImageUser;
import com.example.makoreandroid.entities.Message;
import com.example.makoreandroid.entities.RemoteUser;
import com.example.makoreandroid.jsonfiles.SendingMessageJson;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private final String[] displayingError = {"You can't add your self as a user!",
            "This user already exists!",
            "There is no such user!",
            "This server could not be reached",
            "This may take awhile. Please don't Add again",
            "UserName is required!", "Server is required!", "Nickname is required!",
            "This server could not be reach!", "It might take awhile!"};
    private FloatingActionButton addBtn;
    private ListView listView;
    private CustomListAdapter adapter;
    private AlertDialog dialog;
    String token;
    private ArrayList<RemoteUser> remote;
    ContactsAPI contactsAPI = new ContactsAPI();
    TextView error;
    String UserName;
    ArrayList<RemoteUser> r = new ArrayList<RemoteUser>();
    NotificationManagerCompat notificationManager;
    private RemoteUserDB db;
    private RemoteUserDao userDao;
    private ImageUserDB IuDB;
    private ImageUserDao IuDao;
    private TextView title;
    private String newTitle;
    String friendID;
    String friendNickName;
    String friendServer;
    ConversationActivity conversationActivity;
    MessageListAdapter messageAdapter;
    MessageDao messageDao;
    MessageAPI messageAPI;
    RecyclerView lstMessages;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts_list);

        //take the jwt
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token","");
        notificationManager = NotificationManagerCompat.from(this);


        //add button and onclick listener
        addBtn = findViewById(R.id.hey);
        buildDialog();
        Intent i = getIntent();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                token = prefs.getString("token","");
                dialog.show();
            }
        });

        //actionbar customization
        UserName = i.getStringExtra("UserName");
        IuDB = Room.databaseBuilder(getApplicationContext(), ImageUserDB.class, "ImageUserDB")
                .allowMainThreadQueries().build();
        IuDao = IuDB.imageUserDao();
        ImageUser image = IuDao.get(UserName);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        title = findViewById(R.id.User_name_title);
        ImageView img = findViewById(R.id.profile_image1);
        try {
            img.setImageBitmap(image.getProfilePic());
        }catch (Exception e) {
            img.setImageResource(R.drawable.avatar);
        }
        String newTitle = "Welcome " + UserName + "!";
        title.setText(newTitle);

        //Room
        db = Room.databaseBuilder(getApplicationContext(), RemoteUserDB.class,
                "RemoteUserDB").allowMainThreadQueries().build();
        userDao = db.remoteUserDao();
        listView = findViewById(R.id.list_view);
        ArrayList<RemoteUser> newRemoteArray = new ArrayList<RemoteUser>(userDao.get(UserName));
        Collections.sort(newRemoteArray, (o1, o2) -> o2.getLastdate().compareTo(o1.getLastdate()));
        remote = new ArrayList<RemoteUser>(newRemoteArray);
        adapter = new CustomListAdapter(getApplicationContext(), remote, IuDao);
        adapter.setAdapter(remote);
        listView.setAdapter(adapter);


        // send Server Firebase Token
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ContactActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Intent myIntent = getIntent();
                String fireBaseToken = instanceIdResult.getToken();
                // Call FireBaseMap on SERVER
                FireBaseAPI fireBaseAPI = new FireBaseAPI();
                fireBaseAPI.setFireBaseToken(myIntent.getStringExtra("UserName"), fireBaseToken);
            }
        });


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            newTitle = UserName;
            title.setText(newTitle);

            EditText editText = findViewById(R.id.typing_board_land);
            androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.recycler_conversaion_land);
            com.google.android.material.floatingactionbutton.FloatingActionButton button = findViewById(R.id.button_send_land);

            editText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

            //set every contact clickable and define the onItemClick
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ColorDrawable colorDrawable
                            = new ColorDrawable(Color.alpha(R.color.chat_settings_bar));
                    adapterView.getChildAt(i).setBackgroundDrawable(colorDrawable);
                    friendID = remote.get(i).getId();
                    friendNickName = remote.get(i).getName();
                    friendServer = remote.get(i).getServer();

                    editText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    notificationManager = NotificationManagerCompat.from(ContactActivity.this);

                    // init partner props bar
                    ImageUserDB IuDB = Room.databaseBuilder(getApplicationContext(), ImageUserDB.class, "ImageUserDB")
                            .allowMainThreadQueries().build();
                    IuDao = IuDB.imageUserDao();
//                    TextView partnerNameTV = findViewById(R.id.partner_name);
//                    partnerNameTV.setText(friendID);
//                    ImageView imageView = findViewById(R.id.partner_profile_image);
//                    try {
//                        imageView.setImageBitmap(IuDao.get(friendID).getProfilePic());
//                    }catch (Exception e) {
//                        imageView.setImageResource(R.drawable.avatar);
//                    }

                    // init messages RecyclerView
                    lstMessages = findViewById(R.id.recycler_conversaion_land);
                    messageAdapter = new MessageListAdapter(ContactActivity.this);
                    lstMessages.setAdapter(messageAdapter);
                    lstMessages.setLayoutManager(new LinearLayoutManager(ContactActivity.this));

                    // init Room
                    MessageDB db = Room.databaseBuilder(getApplicationContext(), MessageDB.class,
                            "MessageDB").allowMainThreadQueries().build();
                    messageDao = db.messageDao();
                    messageAdapter.setMessages(messageDao.get(friendID, UserName));

                    //get messages for conversation
                    messageAPI = new MessageAPI(friendServer);
                    messageAPI.get(messageAdapter, token, friendID,
                            lstMessages, messageDao, UserName);

                    //update local backup messages view
                    List<Message> messages = new ArrayList<>();
                    messageAdapter.copyMessagesTo(messages);


                    // on Click on send button
                    FloatingActionButton btnSend = findViewById(R.id.button_send_land);
                    btnSend.setOnClickListener(vv->{
                        EditText et = findViewById(R.id.typing_board_land);
                        if(!et.getText().toString().trim().equals("")) {
                            Message newMessage = new Message(3, et.getText().toString(), "21:40", true);
                            messages.add(newMessage);
                            Intent myIntent = getIntent();
                            // post request to save new message
                            SendingMessageJson sendingMessageJson = new SendingMessageJson(
                                    myIntent.getStringExtra("UserName"), friendID, et.getText().toString());
                            messageAPI.transferAndGet(sendingMessageJson,
                                    messageAdapter, token, friendID, lstMessages,
                                    messageDao, UserName, newMessage);

                            // clean typing board
                            et.setText("");
                        }
                    });
                }
            });

        } else {
            // In portrait
            newTitle = "Welcome " + UserName + "!";
            title.setText(newTitle);

            //set every contact clickable and define the onItemClick
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ColorDrawable colorDrawable
                            = new ColorDrawable(Color.alpha(R.color.chat_settings_bar));
                    adapterView.getChildAt(i).setBackgroundDrawable(colorDrawable);
                    Intent myIntent = getIntent();
                    Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                    intent.putExtra("UserName", myIntent.getStringExtra("UserName"));
                    intent.putExtra("friendID", remote.get(i).getId());
                    intent.putExtra("friendNickName", remote.get(i).getName());
                    intent.putExtra("friendServer", remote.get(i).getServer());
                    startActivity(intent);
                }
            });
        }

    }

//
//    @Override
//    public void onConfigurationChanged(Configuration _newConfig) {
//        super.onConfigurationChanged(_newConfig);
//        if (_newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Log.d("coral", "land");
//            newTitle = UserName;
//            title.setText(newTitle);
//            // support landscape mode
//
//
//        } else {
//            Log.d("coral", "port");
//            newTitle = "Welcome " + UserName + "!";
//            title.setText(newTitle);
//        }
//    }

    private void buildDialog() {
        View view = getLayoutInflater().inflate(R.layout.pop_up_add_contact, null);
        EditText userName = view.findViewById(R.id.AddUserName);
        EditText NickName = view.findViewById(R.id.AddNickName);
        EditText Server = view.findViewById(R.id.AddServer);
        error = (TextView) view.findViewById(R.id.error);
        dialog = new AlertDialog.Builder(this).setTitle("Add new contact")
                .setPositiveButton("Add", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userName.setText("");
                        NickName.setText("");
                        Server.setText("");
                        error.setText("");
                    }
                }).create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(false);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setText("");
            }
        });
        NickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setText("");
            }
        });
        Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setText("");
            }
        });
        dialog.create();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = prefs.getString("token","");
                if(TextUtils.isEmpty(userName.getText().toString())) {
                    error.setText(displayingError[5]);
                    return;
                }
                if(TextUtils.isEmpty(Server.getText().toString())) {
                    error.setText(displayingError[6]);
                    return;
                }
                if(TextUtils.isEmpty(NickName.getText().toString())) {
                    error.setText(displayingError[7]);
                    return;
                }
                contactsAPI.validation(token, userName, Server, error, displayingError, remote,
                        NickName, adapter, ContactActivity.this, dialog, UserName, r, userDao);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        contactsAPI.get(token, remote, adapter, r, userDao, UserName);
        listView.setAdapter(adapter);
        // registering BroadcastReceiver
        if (activityReceiver != null) {
            IntentFilter intentFilter = new IntentFilter("ACTION_ACTIVITY");
            registerReceiver(activityReceiver, intentFilter);
        }
    }

    /* try */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(activityReceiver);
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
                String Msg = "Add";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Msg)
                        .setSmallIcon(R.drawable.avatar)
                        .setContentTitle(titleAndBody[0])
                        .setContentText(titleAndBody[1])
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                int notificationID = titleAndBody[0].hashCode();
                notificationManager.notify(notificationID, builder.build());
            }
            //get contacts for conversation
            SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = prefs.getString("token","");
            contactsAPI.get(token, remote, adapter, r, userDao, UserName);

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //get messages for conversation
                MessageAPI messageAPI = new MessageAPI(friendServer);
                messageAPI.get(messageAdapter, token, friendID, lstMessages, messageDao, UserName);
            }
        }

        private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Add";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("Add", name, importance);

                NotificationManager notificationManager1 = getSystemService((NotificationManager.class));
                notificationManager1.createNotificationChannel(channel);

                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    CharSequence name1 = "Message";
                    int importance1 = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel1 = new NotificationChannel("Message", name1, importance1);

                    NotificationManager notificationManager2 = getSystemService((NotificationManager.class));
                    notificationManager2.createNotificationChannel(channel1);
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView)search.getActionView();
        sv.setQueryHint("Search");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setAdapter(r);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        MenuItem settings = menu.findItem(R.id.action_settings);
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ContactActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}