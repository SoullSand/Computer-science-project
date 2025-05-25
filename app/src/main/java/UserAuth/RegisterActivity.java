package UserAuth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.computerscienceproject.R;
import com.example.computerscienceproject.UpdateFirebaseService;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int usernameLength = etUsername.getText().length();
        int emailLength = etEmail.getText().length();
        int passwordLength = etPassword.getText().length();

        Log.d("Tamir", "Onclick ");
        if (usernameLength == 0) {
            etUsername.setError("Username is required");
        }
        else if (usernameLength > 6) {
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

            // Create an Intent to start UpdateFirebaseService
            Intent intent = new Intent(this, UpdateFirebaseService.class);
            intent.putExtra("username", etUsername.getText().toString());
            intent.putExtra("email", etEmail.getText().toString());
            intent.putExtra("password", etPassword.getText().toString());
            intent.putExtra("action", "Register");

            // Start the service
            startService(intent);
            finish();
        }
    }
}