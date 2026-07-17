package edu.uph.m24si1.uas_signlanguageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsername, tvTitle, tvCoins, tvStreak;
    private ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        tvTitle = findViewById(R.id.tvTitle);
        tvCoins = findViewById(R.id.tvCoins);
        tvStreak = findViewById(R.id.tvStreak);
        imgAvatar = findViewById(R.id.imgAvatar);

        Button btnBelajar = findViewById(R.id.btnBelajar);
        Button btnKuis = findViewById(R.id.btnKuis);
        Button btnToko = findViewById(R.id.btnToko);
        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);

        imgAvatar.setOnClickListener(v -> startActivity(new Intent(this, EditProfileActivity.class)));
        btnBelajar.setOnClickListener(v -> startActivity(new Intent(this, CategoryActivity.class)));
        btnKuis.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        btnToko.setOnClickListener(v -> startActivity(new Intent(this, StoreActivity.class)));
        btnLeaderboard.setOnClickListener(v -> startActivity(new Intent(this, LeaderboardActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);

        tvUsername.setText(prefs.getString("USERNAME", "User"));
        tvTitle.setText(prefs.getString("EQUIPPED_TITLE", "Pemula"));
        tvCoins.setText(String.valueOf(prefs.getInt("TOTAL_KOIN", 0)));
        tvStreak.setText(prefs.getInt("CURRENT_STREAK", 0) + " Hari");
    }
}