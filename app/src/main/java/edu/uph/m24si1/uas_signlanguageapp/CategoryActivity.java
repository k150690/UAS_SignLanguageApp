package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    private String tujuanActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Menangkap status apakah user ingin Belajar atau Kuis dari MainActivity
        tujuanActivity = getIntent().getStringExtra("TUJUAN_ACTIVITY");
        if (tujuanActivity == null) {
            tujuanActivity = "FLASHCARD"; // Fallback aman
        }

        Button btnAbjad = findViewById(R.id.btnKategoriAbjad);
        Button btnHari = findViewById(R.id.btnKategoriHari);
        Button btnKeluarga = findViewById(R.id.btnKategoriKeluarga);
        Button btnSapaan = findViewById(R.id.btnKategoriSapaan);
        Button btnBack = findViewById(R.id.btnBackToMenu);

        btnAbjad.setOnClickListener(v -> eksekusiNavigasi("ABJAD"));
        btnHari.setOnClickListener(v -> eksekusiNavigasi("HARI"));
        btnKeluarga.setOnClickListener(v -> eksekusiNavigasi("KELUARGA"));
        btnSapaan.setOnClickListener(v -> eksekusiNavigasi("SAPAAN"));

        btnBack.setOnClickListener(v -> finish());
    }

    private void eksekusiNavigasi(String kategori) {
        Intent intent;

        // Membelah rute perjalanan berdasarkan tombol yang ditekan di menu utama
        if (tujuanActivity.equals("KUIS")) {
            intent = new Intent(this, QuizActivity.class);
        } else {
            intent = new Intent(this, FlashcardActivity.class);
        }

        intent.putExtra("KATEGORI_PILIHAN", kategori);
        startActivity(intent);
    }
}