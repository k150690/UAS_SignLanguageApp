package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        TextView tvRankingBox = findViewById(R.id.tvRankingBox);

        SharedPreferences sharedPref = getSharedPreferences("SignTeachPrefs", Context.MODE_PRIVATE);

        String namaAku = sharedPref.getString("USER_NAME", "JL");
        String titleAku = sharedPref.getString("CURRENT_USER_TITLE", "NPC Berbakat ️");
        int koinAku = sharedPref.getInt("TOTAL_KOIN", 80);

        String teksPeringkat =
                "1. Anastasia Willim - 180 Poin\n" +
                        "   Pakar Isyarat\n\n" +

                        "2. Vivien - 140 Poin\n" +
                        "   Silent Speaker\n\n" +

                        "3. " + namaAku + " - " + koinAku + " Poin\n" +
                        "   " + titleAku + "\n\n" +

                        "4. Andi Wijaya - 90 Poin\n" +
                        "   Isyarat Newbie\n\n" +

                        "5. Roni Skena - 40 Poin\n" +
                        "   Masih Pemula";

        tvRankingBox.setText(teksPeringkat);
    }
}