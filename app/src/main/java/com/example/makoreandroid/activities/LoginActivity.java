package com.example.makoreandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.R;
import com.example.makoreandroid.databinding.ActivityLoginBinding;
import com.example.makoreandroid.repositories.UsersRepository;


// Login
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UsersRepository usersRepository;


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
                usersRepository.getTokenLogin(binding.loginUserName.getText().toString(), binding.loginPassword.getText().toString(), this);
//                if (token != null) {
//                    Intent intent = new Intent(this, ContactActivity.class);
//                    intent.putExtra("UserName", binding.loginUserName.getText().toString());
//                    startActivity(intent);
//                    return;
//                }
            }
            binding.loginError.setText(R.string.login_required_username);
        });


    }
}