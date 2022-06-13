package com.example.makoreandroid.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.makoreandroid.R;
import com.example.makoreandroid.databinding.ActivitySettingsBinding;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    public ActivitySettingsBinding binding;
    private SwitchCompat switchTheme;
    List<String> list;

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
                Toast.makeText(SettingsActivity.this, "Dark mode is on", Toast.LENGTH_SHORT).show();
                goInDarkMode();
            } else {
                Toast.makeText(SettingsActivity.this, "Light mode is on", Toast.LENGTH_SHORT).show();
                goInLightMode();
            }
        });
    }

    private void goInDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void goInLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}