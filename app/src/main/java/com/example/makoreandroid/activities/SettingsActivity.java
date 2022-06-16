package com.example.makoreandroid.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.makoreandroid.R;
import com.example.makoreandroid.api.ServerAPI;
import com.example.makoreandroid.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    public ActivitySettingsBinding binding;
    private SwitchCompat switchTheme;
    private ImageButton changeServer;
    private ImageButton changeNickname;
    private ServerAPI serverAPI;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.alpha(R.color.chat_settings_bar));
        actionBar.setBackgroundDrawable(colorDrawable);
        setContentView(binding.getRoot());

        switchTheme = binding.settingsNextTheme;
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "Yellow theme is on", Toast.LENGTH_SHORT).show();
                goInDarkMode();
            } else {
                Toast.makeText(SettingsActivity.this, "MaKore original theme is on", Toast.LENGTH_SHORT).show();
                goInLightMode();
            }
        });


        changeServer = binding.settingsNextServer;
        changeServer.setOnClickListener(v -> {
            buildDialog();
            dialog.show();
        });
    }


    private void goInDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void goInLightMode() {
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