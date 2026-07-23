package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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
            return Integer.compare(other.score, this.score);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        TextView tvRank1 = findViewById(R.id.tvRank1);
        TextView tvRank2 = findViewById(R.id.tvRank2);
        TextView tvRank3 = findViewById(R.id.tvRank3);
        TextView tvRankingBox = findViewById(R.id.tvRankingBox);

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Anastasia Willim", 350, "Pakar Linguistik"));
        players.add(new Player("Vivien", 220, "Duta Isyarat"));
        players.add(new Player("Andi Wijaya", 110, "Pengejar Streak"));
        players.add(new Player("Roni Skena", 40, "NPC Berbakat"));

        SharedPreferences sharedPref = getSharedPreferences("SignTeachPrefs", Context.MODE_PRIVATE);
        String activeEmail = sharedPref.getString("ACTIVE_EMAIL", "default");
        String namaAku = sharedPref.getString("USERNAME_" + activeEmail, sharedPref.getString("USERNAME", "User"));
        String titleAku = sharedPref.getString("EQUIPPED_TITLE_" + activeEmail, "Pemula");
        int koinAku = sharedPref.getInt("TOTAL_KOIN_" + activeEmail, 0);

        players.add(new Player(namaAku + " (Kamu)", koinAku, titleAku));
        Collections.sort(players);

        if (players.size() > 0) {
            tvRank1.setText(Html.fromHtml(formatTopPlayer(1, players.get(0)), Html.FROM_HTML_MODE_LEGACY));
        }
        if (players.size() > 1) {
            tvRank2.setText(Html.fromHtml(formatTopPlayer(2, players.get(1)), Html.FROM_HTML_MODE_LEGACY));
        }
        if (players.size() > 2) {
            tvRank3.setText(Html.fromHtml(formatTopPlayer(3, players.get(2)), Html.FROM_HTML_MODE_LEGACY));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 3; i < players.size(); i++) {
            Player p = players.get(i);
            int rank = i + 1;

            result.append("<b>#").append(rank).append(" ").append(p.name).append("</b>")
                    .append(" — ").append(p.score).append(" Koin<br>")
                    .append("<font color='#64748B'>Gelar: ").append(p.title).append("</font><br><br>");
        }

        if (result.length() > 0) {
            tvRankingBox.setText(Html.fromHtml(result.toString(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvRankingBox.setText("Belum ada pemain lain.");
        }
    }

    private String formatTopPlayer(int rank, Player p) {
        String titleColor;
        if (p.title.equals("Pakar Linguistik") || p.title.equals("Pro Player") || p.title.equals("Duta Isyarat")) {
            titleColor = "#D97706";
        }
        else if (p.title.equals("Pengejar Streak") || p.title.equals("NPC Berbakat")) {
            titleColor = "#6B21A8";
        }
        else {
            titleColor = "#475569";
        }
        return "<b>#" + rank + " " + p.name + "</b> — " + p.score + " Koin<br>" +
                "<font color='" + titleColor + "'><b>" + p.title + "</b></font>";
    }
}