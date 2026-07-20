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

public class Register extends AppCompatActivity {
    EditText name, email, password; // Variabel phone dihapus
    Button registerButton;
    TextView loginTextLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword); // findViewById phone dihapus
        registerButton = findViewById(R.id.registerButton);
        loginTextLink = findViewById(R.id.loginTextLink);

        SharedPreferences userPrefs = getSharedPreferences("SignTeachUserPrefs", MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = name.getText().toString().trim();
                String inputEmail = email.getText().toString().trim();
                String inputPass = password.getText().toString().trim();

                if (inputName.isEmpty() || inputEmail.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(Register.this, "Name, Email, and Password are required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                    Toast.makeText(Register.this, "Please enter a valid email address (e.g. name@gmail.com)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPass.length() < 6) {
                    Toast.makeText(Register.this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPrefs.contains(inputEmail)) {
                    Toast.makeText(Register.this, "An account with this email already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = userPrefs.edit();
                editor.putString(inputEmail, inputPass);
                editor.putString(inputEmail + "_name", inputName);
                editor.apply();

                getSharedPreferences("SignTeachPrefs", MODE_PRIVATE)
                        .edit()
                        .putString("ACTIVE_EMAIL", inputEmail)
                        .putString("USERNAME_" + inputEmail, inputName)
                        .putString("USERNAME", inputName) // Fallback
                        .apply();

                Toast.makeText(Register.this, "Register Successful!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Register.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        loginTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}