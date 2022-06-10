package com.example.makoreandroid;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private String[] rUserName = {"Ido", "Coral", "Tal"};
    private String[] rNickName = {"idodo", "corali", "talush"};
    private String[] rLastMessage = {"hey there !", "whats up?", "hey !!"};
    private String[] rTime = {"13:33", "12:01", "00:32"};

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#edc3f7"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        setContentView(R.layout.activity_contacts_list);
        ArrayList<RemoteUser> remote = new ArrayList<>();
        for(int i = 0; i < rUserName.length; i++) {
            RemoteUser r = new RemoteUser(rNickName[i], rUserName[i], rLastMessage[i], rTime[i]);
            remote.add(r);
        }
        listView = findViewById(R.id.list_view);
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
