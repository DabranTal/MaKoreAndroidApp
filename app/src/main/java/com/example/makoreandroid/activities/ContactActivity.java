package com.example.makoreandroid.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.RemoteUserDB;
import com.example.makoreandroid.RemoteUsersDao;
import com.example.makoreandroid.adapters.CustomListAdapter;
import com.example.makoreandroid.api.ContactsAPI;
import com.example.makoreandroid.entities.RemoteUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private final String[] allUsers = {"Ido", "Coral", "Tal", "Matan", "Itamar", "Roni", "Eden",
            "Guy"};
    private final String[] displayingError = {"You can't add your self as a user!",
            "This user already exists!",
            "There is no such user!",
            "This server could not be reached",
            "This may take awhile. Please don't Add again",
            "UserName is required!", "Server is required!"};
    private FloatingActionButton addBtn;
    private ListView listView;
    private CustomListAdapter adapter;
    private AlertDialog dialog;
    private ArrayList<RemoteUser> remote;
    private final String Name = "Roni";
    private RemoteUserDB db;
    private RemoteUsersDao Rdao;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts_list);
        db = Room.databaseBuilder(getApplicationContext(), RemoteUserDB.class, "RemoteUserDB").allowMainThreadQueries().build();
        Rdao = db.RemoteUsersDao();
        addBtn = findViewById(R.id.hey);
        buildDialog();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = prefs.getString("token","");
                dialog.show();
            }
        });
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.alpha(R.color.chat_settings_bar));

        actionBar.setBackgroundDrawable(colorDrawable);
        listView = findViewById(R.id.list_view);
        remote = new ArrayList<RemoteUser>(Rdao.index());
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        ContactsAPI contactsAPI = new ContactsAPI();
        contactsAPI.get(this);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = getIntent();
                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                intent.putExtra("UserName", myIntent.getStringExtra("UserName"));
                intent.putExtra("friendID", remote.get(i).getUserName());
                intent.putExtra("friendNickName", remote.get(i).getNickName());
                intent.putExtra("friendServer", remote.get(i).getServer());
                intent.putExtra("friendAvatar", remote.get(i).getAvatar());
                startActivity(intent);
            }
        });

    }

    private void buildDialog() {
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        EditText userName = view.findViewById(R.id.AddUserName);
        EditText NickName = view.findViewById(R.id.AddNickName);
        EditText Server = view.findViewById(R.id.AddServer);
        TextView error = (TextView) view.findViewById(R.id.error);
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
                if(TextUtils.isEmpty(userName.getText().toString())) {
                    error.setText(displayingError[5]);
                    return;
                }
                if(TextUtils.isEmpty(Server.getText().toString())) {
                    error.setText(displayingError[6]);
                    return;
                }
                boolean isUser = userName.getText().toString().equals(Name),
                        isExists = false, isAlreadyExists = false;
                for (RemoteUser r : remote) {
                    if(r.getUserName().equals(userName.getText().toString())) {
                        isAlreadyExists = true;
                        break;
                    }
                }
                for (String r : allUsers) {
                    if(r.equals(userName.getText().toString())) {
                        isExists = true;
                        break;
                    }
                }
                if (!isUser && isExists && !isAlreadyExists) {
                    String nick = NickName.getText().toString();
                    if(nick.equals(""))
                        nick = userName.getText().toString();
                    addRemoteUser(userName.getText().toString(),nick,
                            Server.getText().toString());
                    userName.setText("");
                    NickName.setText("");
                    Server.setText("");
                    Toast.makeText(ContactActivity.this, "New Contact has been added",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                if(isUser)
                    error.setText(displayingError[0]);
                else if(!isExists)
                    error.setText(displayingError[2]);
                else if(isAlreadyExists)
                    error.setText(displayingError[1]);
            }
        });

    }

    private void addRemoteUser(String userName, String NickName, String Server) {
        RemoteUser r = new RemoteUser(NickName, userName, "", "", Server);
        remote.add(r);
        Rdao.insert(r);
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }

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