package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class FlashcardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // CONTOH LOGIKA VIVIEN SAAT MATERI DIBACA:
        /*
        int koinLama = prefs.getInt("TOTAL_KOIN", 0);

        // Tambahkan batas limit flashcard harian jika perlu
        editor.putInt("TOTAL_KOIN", koinLama + 3);
        editor.apply();
        */
    }
}