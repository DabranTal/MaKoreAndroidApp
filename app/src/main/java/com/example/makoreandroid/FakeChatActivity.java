package com.example.makoreandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.makoreandroid.databinding.ActivityFakeChatBinding;
import com.example.makoreandroid.databinding.ActivityMainBinding;

public class FakeChatActivity extends AppCompatActivity {

    private ActivityFakeChatBinding binding;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFakeChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null) {
            String userName = getIntent().getExtras().getString("UserName");
            //User user = userDao.get(userName);
            binding.fakeChat.setText(userName);
        }
    }
}