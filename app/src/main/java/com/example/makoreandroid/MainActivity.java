package com.example.makoreandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.makoreandroid.databinding.ActivityMainBinding;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Login
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                String userName = getIntent().getExtras().getString("UserName");
                //User user = userDao.get(userName);
                binding.loginUserName.setText(userName);
            }
        }

        binding.loginRegister.setOnClickListener(v -> {
            Log.d("coral", "enter");
            if ((binding.loginUserName.getText() != null) && (binding.loginPassword.getText() != null)) {
                String userName = binding.loginUserName.getText().toString();
                String password = binding.loginPassword.getText().toString();

                for (User u : users) {
                    if (u.getUserName().equals(userName)) {
                        if (u.getPassword().equals(password)) {
                            Intent intent = new Intent(this, RegisterActivity.class);
                            intent.putExtra("UserName", binding.loginUserName.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
                // wrong pass or username
            }

        });

        binding.loginBtnLogin.setOnClickListener(v -> {
            Log.d("coral", "enter1");
            Intent intent = new Intent(this, FakeChatActivity.class);
            intent.putExtra("UserName" ,binding.loginUserName.getText().toString());
            startActivity(intent);
        });


    }
}