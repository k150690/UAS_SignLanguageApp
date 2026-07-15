package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    private ImageView imgSign;
    private TextView tvLabel, tvProgress;
    private Button btnPrev, btnNext;

    private ArrayList<FlashcardItem> materiList;
    private int currentIndex = 0;
    private String kategoriAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // 1. Hubungkan variabel dengan ID di XML
        imgSign = findViewById(R.id.imgSign);
        tvLabel = findViewById(R.id.tvLabel);
        tvProgress = findViewById(R.id.tvProgress);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // 2. Tangkap sinyal Kategori dari Intent
        kategoriAktif = getIntent().getStringExtra("KATEGORI_PILIHAN");

        // Validasi keamanan: Jika lolos tanpa kategori (null), tutup paksa Activity
        if (kategoriAktif == null) {
            Toast.makeText(this, "Akses Ilegal: Kategori tidak ditemukan.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 3. Susun data berdasarkan kategori, lalu tampilkan
        inisialisasiData(kategoriAktif);

        if (materiList.isEmpty()) {
            Toast.makeText(this, "Materi untuk kategori ini belum tersedia.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tampilkanMateri();

        // 4. Logika Tombol Navigasi
        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                tampilkanMateri();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < materiList.size() - 1) {
                currentIndex++;
                tampilkanMateri();
            } else {
                selesaikanSesi();
            }
        });
    }

    private void inisialisasiData(String kategori) {
        materiList = new ArrayList<>();

        switch (kategori) {
            case "ABJAD":
                materiList.add(new FlashcardItem("Huruf A", R.drawable.a));
                materiList.add(new FlashcardItem("Huruf B", R.drawable.b));
                break;

            case "HARI":
                materiList.add(new FlashcardItem("Senin", R.drawable.senin));
                materiList.add(new FlashcardItem("Selasa", R.drawable.selasa));

                break;

            case "KELUARGA":
                materiList.add(new FlashcardItem("Bapak", R.drawable.bapak));
                materiList.add(new FlashcardItem("Ibu", R.drawable.ibu));
                materiList.add(new FlashcardItem("Adik", R.drawable.adik));

                break;
        }
    }

    private void tampilkanMateri() {
        FlashcardItem item = materiList.get(currentIndex);

        tvLabel.setText(item.label);
        tvProgress.setText("Materi " + (currentIndex + 1) + " / " + materiList.size());

        if (currentIndex == materiList.size() - 1) {
            btnNext.setText("Selesai & Klaim Koin");
        } else {
            btnNext.setText("Selanjutnya");
        }

        btnPrev.setEnabled(currentIndex != 0);

        // Mesin Glide untuk memutar animasi WebP
        Glide.with(this)
                .load(item.resourceId)
                .into(imgSign);
    }

    private void selesaikanSesi() {
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        int koinLama = prefs.getInt("TOTAL_KOIN", 0);

        prefs.edit().putInt("TOTAL_KOIN", koinLama + 10).apply();

        Toast.makeText(this, "Sesi Selesai! Ditambahkan 10 Koin.", Toast.LENGTH_SHORT).show();
        finish();
    }
}