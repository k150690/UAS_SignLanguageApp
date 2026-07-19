package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtEditUsername;
    private Button btnSimpanProfile, btnBatalProfile, btnLogout;
    private ImageView imgAvatarEdit;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        selectedImageUri = imageUri;
                        imgAvatarEdit.setImageURI(imageUri);
                    }
                }
            }
    );

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
        btnLogout = findViewById(R.id.btnLogout);
        imgAvatarEdit = findViewById(R.id.imgAvatarEdit);

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);

        String activeEmail = prefs.getString("ACTIVE_EMAIL", "default");

        String namaLama = prefs.getString("USERNAME_" + activeEmail, prefs.getString("USERNAME", "User"));
        edtEditUsername.setText(namaLama);

        String savedUriStr = prefs.getString("PROFILE_IMAGE_URI_" + activeEmail, null);
        if (savedUriStr != null) {
            try {
                imgAvatarEdit.setImageURI(Uri.parse(savedUriStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        imgAvatarEdit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSimpanProfile.setOnClickListener(v -> {
            String namaBaru = edtEditUsername.getText().toString().trim();

            if (namaBaru.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("USERNAME_" + activeEmail, namaBaru);
                editor.putString("USERNAME", namaBaru); // Fallback

                if (selectedImageUri != null) {
                    editor.putString("PROFILE_IMAGE_URI_" + activeEmail, selectedImageUri.toString());
                }

                editor.apply();
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBatalProfile.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Logout") .setMessage("Apakah anda yakin ingin keluar dari akun?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove("ACTIVE_EMAIL");
                        editor.apply();

                        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProfileActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
    }
}