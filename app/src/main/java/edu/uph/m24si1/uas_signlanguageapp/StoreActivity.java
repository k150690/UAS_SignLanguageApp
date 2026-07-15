package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // CONTOH LOGIKA ANAS SAAT USER BELI BARANG:
        /*
        int koinSekarang = prefs.getInt("TOTAL_KOIN", 0);
        int hargaItem = 50;

        if (koinSekarang >= hargaItem) {
            editor.putInt("TOTAL_KOIN", koinSekarang - hargaItem);
            editor.putString("EQUIPPED_TITLE", "Raja Minyak Medan"); // Update title
            editor.apply();
        }
        */
    }
}