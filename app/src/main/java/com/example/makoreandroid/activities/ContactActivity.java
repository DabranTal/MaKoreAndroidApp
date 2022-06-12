package com.example.makoreandroid.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.makoreandroid.adapters.CustomListAdapter;
import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.RemoteUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private final String[] allUsers = {"Ido", "Coral", "Tal", "Matan", "Itamar", "Roni", "Eden",
            "Guy"};
    private final String server = "localhost:5018";
    private final String[] rUserName = {"Ido", "Coral", "Tal", "Matan", "Itamar"};
    private final String[] rNickName = {"idodo", "corali", "talush", "Tani", "tamTam"};
    private final String[] rLastMessage = {"hey there !", "whats up?", "hey !!", "hey", ""};
    private final String[] rTime = {"13:33", "12:01", "00:32", "02:32", ""};
    private final String[] displayingError = {"You can't add your self as a user!",
            "This user already exists!",
            "There is no such user!",
            "This server could not be reached",
            "This may take awhile. Please don't Add again",
            "UserName is required!", "Server is required!"};
    FloatingActionButton addBtn;
    ListView listView;
    CustomListAdapter adapter;
    AlertDialog dialog;
    ArrayList<RemoteUser> remote = new ArrayList<>();
    String Name = "Roni";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contacts_list);
        addBtn = findViewById(R.id.hey);
        buildDialog();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#edc3f7"));
        actionBar.setBackgroundDrawable(colorDrawable);
        for (int i = 0; i < rUserName.length; i++) {
            RemoteUser r = new RemoteUser(rNickName[i], rUserName[i], rLastMessage[i], rTime[i], server);
            remote.add(r);
        }
        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                intent.putExtra("friendID", remote.get(i).getUserName());
                intent.putExtra("friendNickName", remote.get(i).getNickNam());
                intent.putExtra("friendServer", remote.get(i).getServer());
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
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mi = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) mi.getActionView();
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