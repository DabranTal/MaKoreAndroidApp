package com.example.makoreandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.makoreandroid.databinding.ActivityMainBinding;
import com.example.makoreandroid.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getString("UserName") != null) {
                    String userName = getIntent().getExtras().getString("UserName");
                    //User user = userDao.get(userName);
                    binding.registerUserName.setText(userName);
                }
            }
        }

        binding.registerLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            if (binding.registerUserName.getText() != null) {
                intent.putExtra("UserName", binding.registerUserName.getText().toString());
            }
            startActivity(intent);
        });

    }
}