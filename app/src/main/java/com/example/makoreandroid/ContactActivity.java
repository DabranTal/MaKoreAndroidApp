package com.example.makoreandroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private String[] rUserName = {"Ido", "Coral", "Tal", "Matan", "Itamar"};
    private String[] rNickName = {"idodo", "corali", "talush", "Tani", "tamTam"};
    private String[] rLastMessage = {"hey there !", "whats up?", "hey !!", "hey", ""};
    private String[] rTime = {"13:33", "12:01", "00:32", "02:32", ""};
    FloatingActionButton addBtn;
    ListView listView;
    CustomListAdapter adapter;
    AlertDialog dialog;
    ArrayList<RemoteUser> remote = new ArrayList<>();
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

        for(int i = 0; i < rUserName.length; i++) {
            RemoteUser r = new RemoteUser(rNickName[i], rUserName[i], rLastMessage[i], rTime[i]);
            remote.add(r);
        }
        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), remote);
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        EditText userName = view.findViewById(R.id.AddUserName);
        EditText NickName = view.findViewById(R.id.AddNickName);
        EditText Server = view.findViewById(R.id.AddServer);
        builder.setView(view);
        builder.setTitle("Add new contact").setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addRemoteUser(userName.getText().toString(), NickName.getText().toString(),
                                Server.getText().toString());
                        userName.setText("");
                        NickName.setText("");
                        Server.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog = builder.create();

    }

    private void addRemoteUser(String userName, String NickName, String Server) {
            RemoteUser r = new RemoteUser(NickName, userName, "", "");
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
