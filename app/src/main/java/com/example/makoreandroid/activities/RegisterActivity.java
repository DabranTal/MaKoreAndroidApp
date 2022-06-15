package com.example.makoreandroid.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.makoreandroid.R;
import com.example.makoreandroid.dao.ImageUserDao;
import com.example.makoreandroid.databinding.ActivityRegisterBinding;
import com.example.makoreandroid.db.ImageUserDB;
import com.example.makoreandroid.entities.ImageUser;
import com.example.makoreandroid.repositories.UsersRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public ActivityRegisterBinding binding;
    private UsersRepository usersRepository;
    private int REQUEST_IMAGE_GALLERY = 133;
    private int REQUEST_IMAGE_CAMERA = 123;
    private Bitmap image = null;
    private ImageUserDB db;
    private ImageUserDao dao;


    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        usersRepository = new UsersRepository();
        setContentView(binding.getRoot());

        //Room
        db = Room.databaseBuilder(getApplicationContext(), ImageUserDB.class,
                "ImageUserDB").allowMainThreadQueries().build();

        dao = db.imageUserDao();
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
            this.finish();
        });

        ImageView addImg = binding.addImg;
        addImg.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Profile Image");
            builder.setMessage("Please choose an option: ");
            builder.setPositiveButton("Gallery", (DialogInterface dialog, int which) -> {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
            });
            builder.setNegativeButton("Camera", (DialogInterface dialog, int which) -> {
                dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        String[] s = {Manifest.permission.CAMERA};
                        ActivityCompat.requestPermissions(this, s, 1);
                    }
                } else {
                    startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

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
                ImageUser imageUser = new ImageUser(binding.registerUserName.getText().toString(),
                        image);
                dao.insert(imageUser);
                usersRepository.getTokenRegister(binding.registerUserName.getText().toString(),
                        binding.registerNickName.getText().toString(),
                        binding.registerPassword.getText().toString(), this);
                // no nickname - username again
            } else {
                ImageUser imageUser = new ImageUser(binding.registerUserName.getText().toString(),
                        image);
                dao.insert(imageUser);
                usersRepository.getTokenRegister(binding.registerUserName.getText().toString(),
                        binding.registerUserName.getText().toString(),
                        binding.registerPassword.getText().toString(), this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            binding.addImg.setImageURI(imageUri);
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            image = Bitmap.createBitmap((Bitmap) data.getExtras().get("data"));
            binding.addImg.setImageBitmap(image);
        }
        else
            Toast.makeText(this, "Something went wrong.. Try again later", Toast.LENGTH_SHORT).show();
    }
}