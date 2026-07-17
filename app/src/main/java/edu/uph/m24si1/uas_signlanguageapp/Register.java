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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    EditText name, email, phone, password;
    Button registerButton;
    TextView loginTextLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        phone = findViewById(R.id.registerPhone);
        password = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        loginTextLink = findViewById(R.id.loginTextLink);

        SharedPreferences userPrefs = getSharedPreferences("SignTeachUserPrefs", MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = name.getText().toString().trim();
                String inputEmail = email.getText().toString().trim();
                String inputPhone = phone.getText().toString().trim();
                String inputPass = password.getText().toString().trim();

                if (inputName.isEmpty() || inputEmail.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(Register.this, "Name, Email, and Password are required.", Toast.LENGTH_SHORT).show();
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

                // Proses simpan password dan nama sekaligus ke SharedPreferences
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.putString(inputEmail, inputPass);
                editor.putString(inputEmail + "_name", inputName); // Menyimpan nama dengan key email+_name
                editor.apply();

                Toast.makeText(Register.this, "Register Successful!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Register.this, Login.class);
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