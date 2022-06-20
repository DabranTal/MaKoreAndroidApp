package com.example.makoreandroid.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.makoreandroid.R;
import com.example.makoreandroid.api.ServerAPI;
import com.example.makoreandroid.databinding.ActivitySettingsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsActivity extends AppCompatActivity {
    public ActivitySettingsBinding binding;
    static SwitchCompat switchTheme;
    private ImageButton changeServer;
    private ImageButton logOut;
    private ServerAPI serverAPI;
    private SharedPreferences prefs;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.alpha(R.color.chat_settings_bar));
        actionBar.setBackgroundDrawable(colorDrawable);
        setContentView(binding.getRoot());

        switchTheme = binding.settingsNextTheme;
        
        // save last theme of client
        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        if (prefs.getBoolean("isOriginalTheme", true)) {
            switchTheme.setChecked(false);
        } else if (!prefs.getBoolean("isOriginalTheme", true)) {
            switchTheme.setChecked(true);
        }

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                goThemeYellow();
                edit.putBoolean("isOriginalTheme", false);
                edit.commit();

            } else {
                goThemeOriginal();
                edit.putBoolean("isOriginalTheme", true);
                edit.commit();
            }
        });


        changeServer = binding.settingsNextServer;
        changeServer.setOnClickListener(v -> {
            buildDialog();
            dialog.show();
        });

        logOut = binding.settingsNextLogOut;
        logOut.setOnClickListener(v -> {
            // delete the token - the user wants to log out
             try {
                String saveToken = "";
                edit.putString("token",saveToken);
                Log.i("Logout",saveToken);
                edit.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            // logging out by popping all activities on top of LoginActivity in which we started
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


        // on Click on back button
        FloatingActionButton btnBack = findViewById(R.id.button_back_from_settings);
        if (btnBack != null) {
            btnBack.setOnClickListener(view-> finish());
        }
    }


    private void goThemeYellow() {
        Log.d("coral", "in go yellow");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void goThemeOriginal() {
        Log.d("coral", "in go original");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void buildDialog() {
        View view = getLayoutInflater().inflate(R.layout.pop_up_change_server, null);
        EditText server = view.findViewById(R.id.add_new_server);
        TextView error = (TextView) view.findViewById(R.id.settings_error_popup_server);
        dialog = new AlertDialog.Builder(this).setTitle("Change default server")
                .setPositiveButton("Change", null).setNegativeButton("Cancel", (dialogInterface, i) -> {
                    server.setText("");
                    error.setText("");
                }).create();
        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.create();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(view1 -> {
            SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = prefs.getString("token","");
            if(TextUtils.isEmpty(server.getText().toString())) {
                error.setText("Field is required !");
            } else {
                String newServer = server.getText().toString();
                serverAPI = new ServerAPI(newServer);
                //serverAPI.notifyNewClient(new InvitationJson());
            }
        });

    }
}