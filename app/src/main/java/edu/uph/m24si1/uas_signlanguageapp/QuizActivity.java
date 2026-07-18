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
                daftarSoal.add(new QuizItem(R.drawable.c, "Huruf A", "Huruf B", "Huruf C", "Huruf L", "Huruf C"));
                daftarSoal.add(new QuizItem(R.drawable.d, "Huruf E", "Huruf D", "Huruf B", "Huruf Y", "Huruf D"));
                daftarSoal.add(new QuizItem(R.drawable.e, "Huruf K", "Huruf E", "Huruf F", "Huruf M", "Huruf E"));
                daftarSoal.add(new QuizItem(R.drawable.f, "Huruf G", "Huruf T", "Huruf F", "Huruf Z", "Huruf F"));
                daftarSoal.add(new QuizItem(R.drawable.g, "Huruf G", "Huruf C", "Huruf P", "Huruf S", "Huruf G"));
                daftarSoal.add(new QuizItem(R.drawable.h, "Huruf V", "Huruf H", "Huruf I", "Huruf W", "Huruf H"));
                daftarSoal.add(new QuizItem(R.drawable.i, "Huruf O", "Huruf Q", "Huruf I", "Huruf D", "Huruf I"));
                daftarSoal.add(new QuizItem(R.drawable.j, "Huruf J", "Huruf V", "Huruf T", "Huruf E", "Huruf J"));
                daftarSoal.add(new QuizItem(R.drawable.k, "Huruf M", "Huruf O", "Huruf K", "Huruf Y", "Huruf K"));
                daftarSoal.add(new QuizItem(R.drawable.l, "Huruf P", "Huruf L", "Huruf Q", "Huruf X", "Huruf L"));
                daftarSoal.add(new QuizItem(R.drawable.m, "Huruf S", "Huruf Z", "Huruf M", "Huruf N", "Huruf M"));
                daftarSoal.add(new QuizItem(R.drawable.n, "Huruf U", "Huruf W", "Huruf N", "Huruf O", "Huruf N"));
                daftarSoal.add(new QuizItem(R.drawable.o, "Huruf R", "Huruf O", "Huruf X", "Huruf D", "Huruf O"));
                daftarSoal.add(new QuizItem(R.drawable.p, "Huruf P", "Huruf B", "Huruf A", "Huruf H", "Huruf P"));
                daftarSoal.add(new QuizItem(R.drawable.q, "Huruf I", "Huruf R", "Huruf J", "Huruf Q", "Huruf Q"));
                daftarSoal.add(new QuizItem(R.drawable.r, "Huruf R", "Huruf W", "Huruf R", "Huruf E", "Huruf R"));
                daftarSoal.add(new QuizItem(R.drawable.s, "Huruf J", "Huruf S", "Huruf G", "Huruf B", "Huruf S"));
                daftarSoal.add(new QuizItem(R.drawable.t, "Huruf E", "Huruf V", "Huruf T", "Huruf U", "Huruf T"));
                daftarSoal.add(new QuizItem(R.drawable.u, "Huruf R", "Huruf M", "Huruf S", "Huruf U", "Huruf U"));
                daftarSoal.add(new QuizItem(R.drawable.v, "Huruf U", "Huruf Y", "Huruf V", "Huruf K", "Huruf V"));
                daftarSoal.add(new QuizItem(R.drawable.w, "Huruf D", "Huruf C", "Huruf F", "Huruf W", "Huruf W"));
                daftarSoal.add(new QuizItem(R.drawable.x, "Huruf N", "Huruf X", "Huruf T", "Huruf B", "Huruf X"));
                daftarSoal.add(new QuizItem(R.drawable.y, "Huruf Y", "Huruf G", "Huruf E", "Huruf R", "Huruf Y"));
                daftarSoal.add(new QuizItem(R.drawable.z, "Huruf P", "Huruf K", "Huruf N", "Huruf Z", "Huruf Z"));
                break;

            case "HARI":
                daftarSoal.add(new QuizItem(R.drawable.senin, "Besok", "Lusa", "Senin", "Jumat", "Senin"));
                daftarSoal.add(new QuizItem(R.drawable.selasa, "Selasa", "Kamis", "Lusa", "Tahun", "Selasa"));
                daftarSoal.add(new QuizItem(R.drawable.rabu, "Sekarang", "Besok", "Rabu", "Hari", "Rabu"));
                daftarSoal.add(new QuizItem(R.drawable.kamis, "Sabtu", "Kamis", "Minggu", "Kemarin", "Kamis"));
                daftarSoal.add(new QuizItem(R.drawable.jumat1, "Dulu", "Tanggal", "Senin", "Jumat", "Jumat"));
                daftarSoal.add(new QuizItem(R.drawable.sabtu, "Sabtu", "Rabu", "Dulu", "Jumat", "Sabtu"));
                daftarSoal.add(new QuizItem(R.drawable.minggu, "Kemarin", "Minggu", "Senin", "Sekarang", "Minggu"));
                daftarSoal.add(new QuizItem(R.drawable.hari, "Tanggal", "Lusa", "Hari", "Kamis", "Hari"));
                daftarSoal.add(new QuizItem(R.drawable.tanggal, "Tanggal", "Rabu", "Besok", "Senin", "Tanggal"));
                daftarSoal.add(new QuizItem(R.drawable.bulan, "Jumat", "Sekarang", "Bulan", "Minggu", "Bulan"));
                daftarSoal.add(new QuizItem(R.drawable.tahun, "Jumat", "Tahun", "Rabu", "Kamis", "Tahun"));
                daftarSoal.add(new QuizItem(R.drawable.sekarang, "Sekarang", "Lusa", "Besok", "Jumat", "Sekarang"));
                daftarSoal.add(new QuizItem(R.drawable.dulu, "Senin", "Rabu", "Kamis", "Dulu", "Dulu"));
                daftarSoal.add(new QuizItem(R.drawable.kemarin, "Hari", "Tanggal", "Kemarin", "Sabtu", "Kemarin"));
                daftarSoal.add(new QuizItem(R.drawable.besok, "Kamis", "Lusa", "Senin", "Besok", "Besok"));
                daftarSoal.add(new QuizItem(R.drawable.lusa, "Lusa", "Hari", "Minggu", "Dulu", "Lusa"));
                break;

            case "KELUARGA":
                daftarSoal.add(new QuizItem(R.drawable.bapak, "Bapak", "Kakek", "Om", "Adik", "Bapak"));
                daftarSoal.add(new QuizItem(R.drawable.ibu, "Kakak", "Adik", "Bapak", "Ibu", "Ibu"));
                daftarSoal.add(new QuizItem(R.drawable.anak, "Anak", "Bayi", "Kakek", "Nenek", "Anak"));
                daftarSoal.add(new QuizItem(R.drawable.bayi, "Suami", "Nenek", "Ibu", "Bayi", "Bayi"));
                daftarSoal.add(new QuizItem(R.drawable.adik, "Ibu", "Adik", "Anak", "Kakak", "Adik"));
                daftarSoal.add(new QuizItem(R.drawable.kakak, "Om", "Kakak", "Bapak", "Adik", "Kakak"));
                daftarSoal.add(new QuizItem(R.drawable.kakek, "Tante", "Om", "Kakek", "Ibu", "kakek"));
                daftarSoal.add(new QuizItem(R.drawable.nenek, "Suami", "Kakek", "Bayi", "Nenek", "Nenek"));
                daftarSoal.add(new QuizItem(R.drawable.om, "Om", "Anak", "Adik", "Suami", "Om"));
                daftarSoal.add(new QuizItem(R.drawable.tante, "Nenek", "Tante", "Bapak", "Ibu", "Tante"));
                daftarSoal.add(new QuizItem(R.drawable.suami, "Kakek", "Adik", "Suami", "NEnek", "Suami"));
                daftarSoal.add(new QuizItem(R.drawable.istri, "Kakak", "Bapak", "Ibu", "Istri", "Istri"));
                break;

            case "SAPAAN":
                daftarSoal.add(new QuizItem(R.drawable.sapa_1, "Assalamualaikum.W.B", "Selamat Siang", "Siapa", "Kenapa", "Assalamualaikum.W.B"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_2, "Selamat Siang", "Walaikumsalam.W.B", "Selamat Pagi", "Selamat Sore", "Walaikumsalam.W.B"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_3, "Selamat Malam", "Selamat Sore", "Selamat Pagi", "Assalamualaikum.W.B", "Selamat Pagi"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_4, "Walaikumsalam.W.B", "Selamat Malam", "Aku", "Selamat Siang", "Selamat Siang"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_5, "Kenapa", "Apa", "Selamat Sore", "Semua", "Selamat Sore"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_6, "Selamat Malam", "Berapa", "Kalian", "Selamat Sore", "Selamat Malam"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_7, "Semua", "Aku", "Assalamualaikum.W.B", "Berapa", "Aku"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_8, "Bagaimana", "Apa", "Saya", "Aku", "Saya"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_9, "Assalamualaikum.W.B", "Siapa", "Anda", "Kamu", "Kamu"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_10, "Dia", "Kenapa", "Selamat Pagi", "Darimana", "Dia"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_11, "Anda", "Kamu", "Selamat Sore", "Dimana", "Anda"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_12, "Selamat Sore", "Kita", "Walaikumsalam.W.B", "Selamat Sore", "Kita"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_13, "Dimana", "Walaikumsalam.W.B", "Kalian", "Kenapa", "Kalian"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_14, "Kalian", "Berapa", "Anda", "Semua", "Semua"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_15, "Anda", "Selamat Malam", "Apa", "Berapa", "Apa"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_16, "Siapa", "Selamat Pagi", "Semua", "Selamat Sore", "Siapa"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_17, "Selamat Siang", "Bagaimana", "Apa", "Assalamualaikum.W.B", "Bagaimana"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_18, "Bagaimana", "Apa", "Kenapa", "Selamat Malam", "Kenapa"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_19, "Aku", "Walaikumsalam.W.B", "Dia", "Berapa", "Berapa"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_20, "Apa", "Selamat Malam", "Assalamualaikum.W.B", "Dimana", "Dimana"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_21, "Darimana", "Kenapa", "Dimana", "Anda", "Darimana"));
                daftarSoal.add(new QuizItem(R.drawable.sapa_22, "Selamat Pagi", "Kemana", "Assalamualaikum.W.B", "Siapa", "Kemana"));
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