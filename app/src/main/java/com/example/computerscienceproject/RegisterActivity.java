package com.example.computerscienceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    FBModule fbModule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

        fbModule = new FBModule(this);
    }

    @Override
    public void onClick(View v) {
        int usernameLength = etUsername.getText().length();
        int emailLength = etEmail.getText().length();
        int passwordLength = etPassword.getText().length();

        if (usernameLength == 0) {
            etUsername.setError("Username is required");
        }
        else if (usernameLength > 6)
        {
            etUsername.setError("Your Username length must be under 7 letters long");
        }
        if (emailLength == 0) {
            etEmail.setError("Email is required");
        }
        if (passwordLength == 0) {
            etPassword.setError("Password is required");
        } else if (passwordLength < 6) {
            etPassword.setError("Password has to be at least 6 letters long");
        }

        if (usernameLength != 0 && emailLength != 0 && passwordLength > 5) {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            fbModule.createNewUser(username, email, password);
        }
    }
}