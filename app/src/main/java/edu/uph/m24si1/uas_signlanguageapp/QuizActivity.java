package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private ImageView imgQuizSign;
    private TextView tvQuizProgress;
    private Button btnOpsiA, btnOpsiB, btnOpsiC, btnOpsiD;

    private ArrayList<QuizItem> daftarSoal;
    private int currentIndex = 0;
    private int skorTotal = 0;
    private String kategoriAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        imgQuizSign = findViewById(R.id.imgQuizSign);
        tvQuizProgress = findViewById(R.id.tvQuizProgress);
        btnOpsiA = findViewById(R.id.btnOpsiA);
        btnOpsiB = findViewById(R.id.btnOpsiB);
        btnOpsiC = findViewById(R.id.btnOpsiC);
        btnOpsiD = findViewById(R.id.btnOpsiD);

        kategoriAktif = getIntent().getStringExtra("KATEGORI_PILIHAN");
        if (kategoriAktif == null) {
            Toast.makeText(this, "Akses Ilegal: Kategori tidak ditemukan.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inisialisasiSoal(kategoriAktif);

        if (daftarSoal.isEmpty()) {
            Toast.makeText(this, "Bank soal untuk kategori ini belum tersedia.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Collections.shuffle(daftarSoal);
        tampilkanSoal();

        btnOpsiA.setOnClickListener(v -> periksaJawaban(btnOpsiA.getText().toString()));
        btnOpsiB.setOnClickListener(v -> periksaJawaban(btnOpsiB.getText().toString()));
        btnOpsiC.setOnClickListener(v -> periksaJawaban(btnOpsiC.getText().toString()));
        btnOpsiD.setOnClickListener(v -> periksaJawaban(btnOpsiD.getText().toString()));
    }

    private void inisialisasiSoal(String kategori) {
        daftarSoal = new ArrayList<>();

        switch (kategori) {
            case "ABJAD":
                daftarSoal.add(new QuizItem(R.drawable.a, "Huruf B", "Huruf A", "Huruf C", "Huruf D", "Huruf A"));
                daftarSoal.add(new QuizItem(R.drawable.b, "Huruf A", "Huruf C", "Huruf B", "Huruf Z", "Huruf B"));
                // TODO: Lanjutkan soal abjad lainnya...
                break;

            case "HARI":
                daftarSoal.add(new QuizItem(R.drawable.senin, "Selasa", "Rabu", "Senin", "Jumat", "Senin"));
                daftarSoal.add(new QuizItem(R.drawable.besok, "Kemarin", "Hari", "Lusa", "Besok", "Besok"));
                // TODO: Lanjutkan soal hari lainnya...
                break;

            case "KELUARGA":
                daftarSoal.add(new QuizItem(R.drawable.bapak, "Ibu", "Kakek", "Bapak", "Adik", "Bapak"));
                daftarSoal.add(new QuizItem(R.drawable.adik, "Adik", "Bayi", "Anak", "Kakak", "Adik"));
                // TODO: Lanjutkan soal keluarga lainnya...
                break;

            case "SAPAAN":
                daftarSoal.add(new QuizItem(R.drawable.sapa_1, "Selamat Pagi", "Assalamualaikum", "Siapa", "Kenapa", "Assalamualaikum"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_3, "Selamat Siang", "Selamat Malam", "Selamat Pagi", "Selamat Sore", "Selamat Pagi"));
                // TODO: Lanjutkan soal sapaan lainnya...
                break;
        }
    }

    private void tampilkanSoal() {
        QuizItem soalSekarang = daftarSoal.get(currentIndex);
        tvQuizProgress.setText("Soal " + (currentIndex + 1) + " / " + daftarSoal.size());

        ArrayList<String> opsiAcak = new ArrayList<>();
        opsiAcak.add(soalSekarang.opsiA);
        opsiAcak.add(soalSekarang.opsiB);
        opsiAcak.add(soalSekarang.opsiC);
        opsiAcak.add(soalSekarang.opsiD);
        Collections.shuffle(opsiAcak);

        btnOpsiA.setText(opsiAcak.get(0));
        btnOpsiB.setText(opsiAcak.get(1));
        btnOpsiC.setText(opsiAcak.get(2));
        btnOpsiD.setText(opsiAcak.get(3));

        try {
            android.graphics.drawable.Drawable drawable = android.graphics.ImageDecoder.decodeDrawable(
                    android.graphics.ImageDecoder.createSource(getResources(), soalSekarang.idGambarWebP)
            );
            imgQuizSign.setImageDrawable(drawable);

            if (drawable instanceof android.graphics.drawable.AnimatedImageDrawable) {
                ((android.graphics.drawable.AnimatedImageDrawable) drawable).start();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat soal gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void periksaJawaban(String jawabanDipilih) {
        QuizItem soalSekarang = daftarSoal.get(currentIndex);

        if (jawabanDipilih.equals(soalSekarang.jawabanBenar)) {
            skorTotal += 10;
            Toast.makeText(this, "Benar! (+10 Koin)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Salah. Jawaban: " + soalSekarang.jawabanBenar, Toast.LENGTH_SHORT).show();
        }

        currentIndex++;

        if (currentIndex < daftarSoal.size()) {
            tampilkanSoal(); // Panggil soal berikutnya
        } else {
            selesaikanKuis(); // Akhiri kuis jika soal habis
        }
    }

    private void selesaikanKuis() {
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        int koinLama = prefs.getInt("TOTAL_KOIN", 0);

        prefs.edit().putInt("TOTAL_KOIN", koinLama + skorTotal).apply();

        Toast.makeText(this, "Kuis Selesai! Total koin yang didapat: " + skorTotal, Toast.LENGTH_LONG).show();
        finish();
    }
}