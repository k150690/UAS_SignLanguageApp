package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {
    class Player implements Comparable<Player> {
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

        TextView tvRankingBox = findViewById(R.id.tvRankingBox);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("Siti", 150, "Pakar Isyarat"));
        players.add(new Player("Budi", 90, "Silent Speaker"));
        players.add(new Player("Agus", 45, "Isyarat Newbie"));

        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        String myName = prefs.getString("USERNAME", "User");
        int myScore = prefs.getInt("TOTAL_KOIN", 0);
        String myTitle = prefs.getString("EQUIPPED_TITLE", "Pemula");

        players.add(new Player(myName + " (Kamu)", myScore, myTitle));
        Collections.sort(players);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            result.append(i + 1).append(". ").append(p.name).append("\n")
                    .append("   Skor: ").append(p.score).append(" | [").append(p.title).append("]\n\n");
        }
        tvRankingBox.setText(result.toString());
    }
}