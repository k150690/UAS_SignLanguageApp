package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        Button btnAbjad = findViewById(R.id.btnKategoriAbjad);
        Button btnHari = findViewById(R.id.btnKategoriHari);
        Button btnKeluarga = findViewById(R.id.btnKategoriKeluarga);
        Button btnSapaan = findViewById(R.id.btnKategoriSapaan);
        Button btnBack = findViewById(R.id.btnBackToMenu);

        btnAbjad.setOnClickListener(v -> bukaFlashcard("ABJAD"));
        btnHari.setOnClickListener(v -> bukaFlashcard("HARI"));
        btnKeluarga.setOnClickListener(v -> bukaFlashcard("KELUARGA"));
        btnSapaan.setOnClickListener(v -> bukaFlashcard("SAPAAN"));

        btnBack.setOnClickListener(v -> finish());
    }

    private void bukaFlashcard(String jenisKategori) {
        Intent intent = new Intent(this, FlashcardActivity.class);
        intent.putExtra("KATEGORI_PILIHAN", jenisKategori);
        startActivity(intent);
    }
}