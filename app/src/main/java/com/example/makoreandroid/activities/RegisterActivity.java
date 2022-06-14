package com.example.makoreandroid.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.R;
import com.example.makoreandroid.databinding.ActivityRegisterBinding;
import com.example.makoreandroid.repositories.UsersRepository;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public ActivityRegisterBinding binding;
    private UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        usersRepository = new UsersRepository();

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

        binding.registerLogin.setOnClickListener(v -> { this.finish(); });

        binding.registerBtnRegister.setOnClickListener(v -> {
            // Empty username
            if (binding.registerUserName.getText().toString().equals("")) {
                binding.registerError.setText(R.string.register_username_have_chars);
                return;
            }

            // No passwords inputted
            if (binding.registerPassword.getText().toString().equals("") || binding.registerVPassword.getText().toString().equals("")) {
                binding.registerError.setText(R.string.register_have_pass);
                return;
            }

            // Not the same passwords
            if (!binding.registerPassword.getText().toString().equals(binding.registerVPassword.getText().toString())) {
                binding.registerError.setText(R.string.register_pass_not_equal);
                return;
            }

            // Password at least 8 digits, digits and letters
            Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(binding.registerPassword.getText().toString());
            boolean matchFound = matcher.find();
            if (binding.registerPassword.getText().toString().length() < 8 || !matchFound) {
                binding.registerError.setText(R.string.register_8digits_pass);
                return;
            }

            if (binding.registerNickName.getText() != null) {
                usersRepository.getTokenRegister(binding.registerUserName.getText().toString(),
                        binding.registerNickName.getText().toString(),
                        binding.registerPassword.getText().toString(), this);
            // no nickname - username again
            } else {
                usersRepository.getTokenRegister(binding.registerUserName.getText().toString(),
                        binding.registerUserName.getText().toString(),
                        binding.registerPassword.getText().toString(), this);
            }
        });

    }
}