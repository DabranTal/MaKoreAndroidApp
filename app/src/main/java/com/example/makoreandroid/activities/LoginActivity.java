package com.example.makoreandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.databinding.ActivityLoginBinding;
import com.example.makoreandroid.entities.User;

import java.util.Objects;


// Login
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
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
                User u = server.get(binding.loginUserName.getText().toString());
                if (u != null) {
                    if (Objects.equals(u.getPassword(), binding.loginPassword.getText().toString())) {
                        Intent intent = new Intent(this, ContactActivity.class);
                        intent.putExtra("UserName", u.getUserName());
                        startActivity(intent);
                    }
                }
                binding.loginError.setText(R.string.login_invalid_details);
            }
            binding.loginError.setText(R.string.login_required_username);
        });


    }
}