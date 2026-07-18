package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button loginButton;
    TextView signupText, forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        SharedPreferences userPrefs = getSharedPreferences("SignTeachUserPrefs", MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUser = username.getText().toString().trim();
                String inputPass = password.getText().toString().trim();

                if (inputUser.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                String storedPass = userPrefs.getString(inputUser, null);

                if (storedPass != null && storedPass.equals(inputPass)) {
                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    String registeredName = userPrefs.getString(inputUser + "_name", "User");

                    getSharedPreferences("SignTeachPrefs", MODE_PRIVATE)
                            .edit()
                            .putString("ACTIVE_EMAIL", inputUser)
                            .putString("USERNAME_" + inputUser, registeredName)
                            .putString("USERNAME", registeredName) // Fallback
                            .apply();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Login Failed! Please check email/password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Forgot password functionality needs integration.", Toast.LENGTH_LONG).show();
            }
        });
    }
}