package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtEditUsername;
    private Button btnSimpanProfile, btnBatalProfile;
    private ShapeableImageView imgAvatarEdit;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imgAvatarEdit.setImageURI(uri);
                    selectedImageUri = uri;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgAvatarEdit = findViewById(R.id.imgAvatarEdit);
        edtEditUsername = findViewById(R.id.edtEditUsername);
        btnSimpanProfile = findViewById(R.id.btnSimpanProfile);
        btnBatalProfile = findViewById(R.id.btnBatalProfile);

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        edtEditUsername.setText(prefs.getString("USERNAME", "User"));
        String savedUri = prefs.getString("PROFILE_URI", null);
        if (savedUri != null) imgAvatarEdit.setImageURI(Uri.parse(savedUri));

        imgAvatarEdit.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSimpanProfile.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("USERNAME", edtEditUsername.getText().toString());
            if (selectedImageUri != null) editor.putString("PROFILE_URI", selectedImageUri.toString());
            editor.apply();
            finish();
        });
        btnBatalProfile.setOnClickListener(v -> finish());
    }
}