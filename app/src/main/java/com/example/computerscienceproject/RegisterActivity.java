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

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int usernameLength = etUsername.getText().length();
        int emailLength = etEmail.getText().length();
        int passwordLength = etPassword.getText().length();

        if (usernameLength == 0) {
            etUsername.setError("Username is required");
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
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            createNewUser(email, password);
        }
    }

    private void createNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "fail register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}