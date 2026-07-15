package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    static class Player implements Comparable<Player> {
        String name;
        int score;
        String title;

        Player(String name, int score, String title) {
            this.name = name;
            this.score = score;
            this.title = title;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(other.score, this.score); // Mengurutkan dari terbesar ke terkecil
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        TextView tvRankingBox = findViewById(R.id.tvRankingBox);

        // Array NPC Kompetitor
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Anastasia Willim", 180, "Pakar Isyarat"));
        players.add(new Player("Vivien", 140, "Silent Speaker"));
        players.add(new Player("Andi Wijaya", 90, "Isyarat Newbie"));
        players.add(new Player("Roni Skena", 40, "Masih Pemula"));

        // Tarik data asli menggunakan kunci yang SAMA dengan MainActivity
        SharedPreferences sharedPref = getSharedPreferences("SignTeachPrefs", Context.MODE_PRIVATE);
        String namaAku = sharedPref.getString("USERNAME", "User");
        String titleAku = sharedPref.getString("EQUIPPED_TITLE", "Pemula");
        int koinAku = sharedPref.getInt("TOTAL_KOIN", 0);

        // Masukkan data asli ke arena, lalu urutkan
        players.add(new Player(namaAku + " (Kamu)", koinAku, titleAku));
        Collections.sort(players);

        // Cetak hasil yang sudah diurutkan ke layar
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            result.append(i + 1).append(". ").append(p.name).append(" - ").append(p.score).append(" Poin\n")
                    .append("   ").append(p.title).append("\n\n");
        }

        tvRankingBox.setText(result.toString());
    }
}