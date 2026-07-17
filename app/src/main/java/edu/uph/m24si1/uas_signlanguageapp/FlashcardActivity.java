package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

        imgSign = findViewById(R.id.imgSign);
        tvLabel = findViewById(R.id.tvLabel);
        tvProgress = findViewById(R.id.tvProgress);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);


        kategoriAktif = getIntent().getStringExtra("KATEGORI_PILIHAN");

        if (kategoriAktif == null) {
            Toast.makeText(this, "Akses Ilegal: Kategori tidak ditemukan.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inisialisasiData(kategoriAktif);

        if (materiList.isEmpty()) {
            Toast.makeText(this, "Materi untuk kategori ini belum tersedia.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tampilkanMateri();

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
                materiList.add(new FlashcardItem("Huruf C", R.drawable.c));
                materiList.add(new FlashcardItem("Huruf D", R.drawable.d));
                materiList.add(new FlashcardItem("Huruf E", R.drawable.e));
                materiList.add(new FlashcardItem("Huruf F", R.drawable.f));
                materiList.add(new FlashcardItem("Huruf G", R.drawable.g));
                materiList.add(new FlashcardItem("Huruf H", R.drawable.h));
                materiList.add(new FlashcardItem("Huruf I", R.drawable.i));
                materiList.add(new FlashcardItem("Huruf J", R.drawable.j));
                materiList.add(new FlashcardItem("Huruf K", R.drawable.k));
                materiList.add(new FlashcardItem("Huruf L", R.drawable.l));
                materiList.add(new FlashcardItem("Huruf M", R.drawable.m));
                materiList.add(new FlashcardItem("Huruf N", R.drawable.n));
                materiList.add(new FlashcardItem("Huruf O", R.drawable.o));
                materiList.add(new FlashcardItem("Huruf P", R.drawable.p));
                materiList.add(new FlashcardItem("Huruf Q", R.drawable.q));
                materiList.add(new FlashcardItem("Huruf R", R.drawable.r));
                materiList.add(new FlashcardItem("Huruf S", R.drawable.s));
                materiList.add(new FlashcardItem("Huruf T", R.drawable.t));
                materiList.add(new FlashcardItem("Huruf U", R.drawable.u));
                materiList.add(new FlashcardItem("Huruf V", R.drawable.v));
                materiList.add(new FlashcardItem("Huruf W", R.drawable.w));
                materiList.add(new FlashcardItem("Huruf X", R.drawable.x));
                materiList.add(new FlashcardItem("Huruf Y", R.drawable.y));
                materiList.add(new FlashcardItem("Huruf Z", R.drawable.z));
                break;

            case "HARI":
                materiList.add(new FlashcardItem("Senin", R.drawable.senin));
                materiList.add(new FlashcardItem("Selasa", R.drawable.selasa));
                materiList.add(new FlashcardItem("Rabu", R.drawable.rabu));
                materiList.add(new FlashcardItem("Kamis", R.drawable.kamis));
                materiList.add(new FlashcardItem("Jumat", R.drawable.jumat1));
                materiList.add(new FlashcardItem("Sabtu", R.drawable.sabtu));
                materiList.add(new FlashcardItem("Minggu", R.drawable.minggu));
                materiList.add(new FlashcardItem("Hari", R.drawable.hari));
                materiList.add(new FlashcardItem("Tanggal", R.drawable.tanggal));
                materiList.add(new FlashcardItem("Bulan", R.drawable.bulan));
                materiList.add(new FlashcardItem("Tahun", R.drawable.tahun));
                materiList.add(new FlashcardItem("Sekarang", R.drawable.sekarang));
                materiList.add(new FlashcardItem("Dulu", R.drawable.dulu));
                materiList.add(new FlashcardItem("Kemarin", R.drawable.kemarin));
                materiList.add(new FlashcardItem("Besok", R.drawable.besok));
                materiList.add(new FlashcardItem("Lusa", R.drawable.lusa));
                break;

            case "KELUARGA":
                materiList.add(new FlashcardItem("Bapak", R.drawable.bapak));
                materiList.add(new FlashcardItem("Ibu", R.drawable.ibu));
                materiList.add(new FlashcardItem("Anak", R.drawable.anak));
                materiList.add(new FlashcardItem("Bayi", R.drawable.bayi));
                materiList.add(new FlashcardItem("Adik", R.drawable.adik));
                materiList.add(new FlashcardItem("Kakak", R.drawable.kakak));
                materiList.add(new FlashcardItem("Kakek", R.drawable.kakek));
                materiList.add(new FlashcardItem("Nenek", R.drawable.nenek));
                materiList.add(new FlashcardItem("Om", R.drawable.om));
                materiList.add(new FlashcardItem("Tante", R.drawable.tante));
                materiList.add(new FlashcardItem("Suami", R.drawable.suami));
                materiList.add(new FlashcardItem("Istri", R.drawable.istri));
                break;

            case "SAPAAN":
                materiList.add(new FlashcardItem("Assalamualaikum Warahmatullahi Wabarakatuh", R.drawable.sapa_1));
                materiList.add(new FlashcardItem("Walaikumsalam Warahmatullahi Wabarakatuh", R.drawable.sapa_2));
                materiList.add(new FlashcardItem("Selamat Pagi", R.drawable.sapa_3));
                materiList.add(new FlashcardItem("Selamat Siang", R.drawable.sapa_4));
                materiList.add(new FlashcardItem("Selamat Sore", R.drawable.sapa_5));
                materiList.add(new FlashcardItem("Selamat Malam", R.drawable.sapa_6));
                materiList.add(new FlashcardItem("Aku", R.drawable.sapa_7));
                materiList.add(new FlashcardItem("Saya", R.drawable.sapa_8));
                materiList.add(new FlashcardItem("Kamu", R.drawable.sapa_9));
                materiList.add(new FlashcardItem("Dia", R.drawable.sapa_10));
                materiList.add(new FlashcardItem("Anda", R.drawable.sapa_11));
                materiList.add(new FlashcardItem("Kita", R.drawable.sapa_12));
                materiList.add(new FlashcardItem("Kalian", R.drawable.sapa_13));
                materiList.add(new FlashcardItem("Semua", R.drawable.sapa_14));
                materiList.add(new FlashcardItem("Apa", R.drawable.sapa_15));
                materiList.add(new FlashcardItem("Siapa", R.drawable.sapa_16));
                materiList.add(new FlashcardItem("Bagaimana", R.drawable.sapa_17));
                materiList.add(new FlashcardItem("Kenapa", R.drawable.sapa_18));
                materiList.add(new FlashcardItem("Berapa", R.drawable.sapa_19));
                materiList.add(new FlashcardItem("Dimana", R.drawable.sapa_20));
                materiList.add(new FlashcardItem("Darimana", R.drawable.sapa_21));
                materiList.add(new FlashcardItem("Kemana", R.drawable.sapa_22));
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

        try {
            android.graphics.drawable.Drawable drawable = android.graphics.ImageDecoder.decodeDrawable(
                    android.graphics.ImageDecoder.createSource(getResources(), item.resourceId)
            );
            imgSign.setImageDrawable(drawable);

            if (drawable instanceof android.graphics.drawable.AnimatedImageDrawable) {
                ((android.graphics.drawable.AnimatedImageDrawable) drawable).start();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void selesaikanSesi() {
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        int koinLama = prefs.getInt("TOTAL_KOIN", 0);

        prefs.edit().putInt("TOTAL_KOIN", koinLama + 10).apply();

        Toast.makeText(this, "Sesi Selesai! +10 Koin", Toast.LENGTH_SHORT).show();
        finish();
    }
}