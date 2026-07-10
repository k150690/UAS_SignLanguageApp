package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class FlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // Kerangka untuk Vivien: Pastikan menggunakan nama file ini
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Vivien akan memasukkan logika penambahan koin belajar (+3 koin, maks 30) di sini
    }
}