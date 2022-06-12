package com.example.makoreandroid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.api.UserAPI;
import com.example.makoreandroid.api.WebServiceAPI;
import com.example.makoreandroid.databinding.ActivityLoginBinding;
import com.example.makoreandroid.entities.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Login
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    //private UsersRepository usersRepository;
    UserAPI userAPI;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    WebServiceAPI wsAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //usersRepository = new UsersRepository();
        setContentView(binding.getRoot());
        userAPI = new UserAPI();

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
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                wsAPI = retrofit.create(WebServiceAPI.class);
                UserAPI userAPI = new UserAPI();
                Call<String> call = wsAPI.loginGetToken(new User(binding.loginUserName.getText().toString(),
                        binding.loginPassword.getText().toString()));

                Intent i = new Intent(LoginActivity.this, BackgroundService.class);
                i.putExtra("username", binding.loginUserName.getText().toString());
                i.putExtra("password", binding.loginPassword.getText().toString());
                startService(i);



                Intent in = new Intent(this, ContactActivity.class);
                in.putExtra("UserName", binding.loginUserName.getText().toString());
                startActivity(in);
                Log.d("coral", "start");
                return;
            }

            binding.loginError.setText(R.string.login_invalid_details);


        });


    }
}