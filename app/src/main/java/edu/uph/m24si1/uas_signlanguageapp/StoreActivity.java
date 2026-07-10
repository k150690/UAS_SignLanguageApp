package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // Kerangka untuk Anas
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Anas akan memasukkan logika pemotongan TOTAL_KOIN dan penyimpanan EQUIPPED_TITLE di sini
    }
}