package com.example.makoreandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.makoreandroid.R;
import com.example.makoreandroid.databinding.ActivityLoginBinding;
import com.example.makoreandroid.repositories.UsersRepository;


// Login
public class LoginActivity extends AppCompatActivity {
    public ActivityLoginBinding binding;
    private UsersRepository usersRepository;
    SharedPreferences prefs;
    boolean isOriginalTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        usersRepository = new UsersRepository();

        setContentView(binding.getRoot());

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                String userName = getIntent().getExtras().getString("UserName");
                binding.loginUserName.setText(userName);
            }
        }

        binding.loginRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            if (binding.loginUserName.getText() != null) {
                intent.putExtra("UserName", binding.loginUserName.getText().toString());
            }
            startActivity(intent);
        });

        binding.loginBtnLogin.setOnClickListener(v -> {
            if (binding.loginUserName.getText() != null) {
                usersRepository.getTokenLogin(binding.loginUserName.getText().toString(),
                        binding.loginPassword.getText().toString(), this);
                return;
            }

            binding.loginError.setText(R.string.login_required_username);
        });

        // display last theme of client
        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        isOriginalTheme = prefs.getBoolean("isOriginalTheme", true);

        if (isOriginalTheme) {
            goThemeOriginal();
        } else {
            goThemeYellow();
        }
    }

    private void goThemeYellow() {
        Log.d("coral", "in go yellow login");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void goThemeOriginal() {
        Log.d("coral", "in go original login");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}