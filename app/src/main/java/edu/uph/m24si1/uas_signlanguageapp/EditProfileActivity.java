package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtEditUsername;
    private Button btnSimpanProfile, btnBatalProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEditUsername = findViewById(R.id.edtEditUsername);
        btnSimpanProfile = findViewById(R.id.btnSimpanProfile);
        btnBatalProfile = findViewById(R.id.btnBatalProfile);

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        String namaLama = prefs.getString("USERNAME", "User");
        edtEditUsername.setText(namaLama);

        btnSimpanProfile.setOnClickListener(v -> {
            String namaBaru = edtEditUsername.getText().toString().trim();

            if (namaBaru.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                prefs.edit().putString("USERNAME", namaBaru).apply();
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBatalProfile.setOnClickListener(v -> finish());
    }
}