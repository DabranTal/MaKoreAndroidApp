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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.CustomListAdapter;
import com.example.makoreandroid.api.ContactsAPI;
import com.example.makoreandroid.entities.RemoteUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
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
    ContactsAPI contactsAPI = new ContactsAPI();
    TextView error;
    String UserName;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts_list);
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        addBtn = findViewById(R.id.hey);
        buildDialog();
        Intent i = getIntent();
        UserName = i.getStringExtra("UserName");
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
                = new ColorDrawable(Color.parseColor("#edc3f7"));
        actionBar.setBackgroundDrawable(colorDrawable);
        listView = findViewById(R.id.list_view);
        remote = new ArrayList<RemoteUser>();
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        contactsAPI.get(token, remote, adapter);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                intent.putExtra("UserName", intent.getStringExtra("UserName"));
                intent.putExtra("friendID", remote.get(i).getId());
                intent.putExtra("friendNickName", remote.get(i).getName());
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

                contactsAPI.validation(token, userName, Server, error, displayingError, remote,
                        NickName, adapter, ContactActivity.this, dialog, UserName);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView)mi.getActionView();
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
        return super.onCreateOptionsMenu(menu);
    }
}