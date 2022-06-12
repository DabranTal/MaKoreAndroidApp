package com.example.makoreandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.makoreandroid.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

// Login
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(binding.getRoot());

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                String userName = getIntent().getExtras().getString("UserName");
                //User user = userDao.get(userName);
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
            binding.loginError.setText("error");
            Intent intent = new Intent(this, FakeChatActivity.class);
            intent.putExtra("UserName" ,binding.loginUserName.getText().toString());
            startActivity(intent);
        });


    }
}