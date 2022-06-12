package com.example.makoreandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.makoreandroid.databinding.ActivityMainBinding;
import com.example.makoreandroid.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(binding.getRoot());

        binding.registerError.setText("error");

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getString("UserName") != null) {
                    String userName = getIntent().getExtras().getString("UserName");
                    //User user = userDao.get(userName);
                    binding.registerUserName.setText(userName);
                }
            }
        }

        binding.registerLogin.setOnClickListener(v -> { this.finish(); });

    }
}