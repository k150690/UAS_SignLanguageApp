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
    private Button btnSimpanProfile, btnBatalProfile;
    private ImageView imgAvatarEdit; // Tambahan komponen foto
    private Uri selectedImageUri = null; // Menyimpan sementara URI foto yang baru dipilih

    // Registrasi fungsi pemicu galeri HP ala pemula
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        // Ambil akses izin membaca file gambar secara permanen
                        getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        // Simpan ke variabel temporary dan tampilkan di layar edit
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
        imgAvatarEdit = findViewById(R.id.imgAvatarEdit); // Hubungkan ke XML

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);

        // Load Nama Lama
        String namaLama = prefs.getString("USERNAME", "User");
        edtEditUsername.setText(namaLama);

        // Load Foto Lama (jika sebelumnya sudah pernah ganti)
        String savedUriStr = prefs.getString("PROFILE_IMAGE_URI", null);
        if (savedUriStr != null) {
            try {
                imgAvatarEdit.setImageURI(Uri.parse(savedUriStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Kalau foto profil diklik, langsung buka galeri HP
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
                editor.putString("USERNAME", namaBaru);

                // Jika user ada memilih foto baru, simpan permanen saat tombol SIMPAN diklik
                if (selectedImageUri != null) {
                    editor.putString("PROFILE_IMAGE_URI", selectedImageUri.toString());
                }

                editor.apply();
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBatalProfile.setOnClickListener(v -> finish());
    }
}