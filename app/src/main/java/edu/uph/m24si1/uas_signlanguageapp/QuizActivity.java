package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    // Struktur Data Soal
    class Question {
        String text;
        String option1, option2, option3;
        int correctOptionIndex; // 1, 2, atau 3

        Question(String text, String o1, String o2, String o3, int correctIndex) {
            this.text = text;
            this.option1 = o1;
            this.option2 = o2;
            this.option3 = o3;
            this.correctOptionIndex = correctIndex;
        }
    }

    private TextView tvQuizProgress, tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private Button btnSubmitAnswer;

    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int correctAnswersCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuizProgress = findViewById(R.id.tvQuizProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer);

        setupDatabaseSoalLokal();
        loadQuestionToScreen();

        btnSubmitAnswer.setOnClickListener(v -> checkAnswer());
    }

    private void setupDatabaseSoalLokal() {
        questionList = new ArrayList<>();
        // JL: Masukkan hardcode soal di sini. Untuk saat ini berupa teks, nanti bisa disesuaikan dengan ID gambar.
        questionList.add(new Question("Gerakan menempelkan telunjuk di bibir berarti?", "Tidur", "Makan", "Diam", 3));
        questionList.add(new Question("Gerakan membentuk huruf 'O' dengan jari berarti alfabet?", "Huruf O", "Huruf C", "Huruf D", 1));
        questionList.add(new Question("Gerakan menyatukan kedua telapak tangan di depan dada?", "Terima Kasih", "Maaf", "Tolong", 1));
        questionList.add(new Question("Mengepalkan tangan dengan ibu jari di antara telunjuk dan tengah?", "Huruf T", "Huruf M", "Huruf N", 1));
        questionList.add(new Question("Gerakan melambaikan tangan ke arah luar?", "Halo", "Selamat Tinggal", "Sama-sama", 2));
    }

    private void loadQuestionToScreen() {
        if (currentQuestionIndex < questionList.size()) {
            Question q = questionList.get(currentQuestionIndex);
            tvQuizProgress.setText("Soal " + (currentQuestionIndex + 1) + " / " + questionList.size());
            tvQuestion.setText(q.text);
            rbOption1.setText(q.option1);
            rbOption2.setText(q.option2);
            rbOption3.setText(q.option3);
            rgOptions.clearCheck();
        } else {
            finishQuizAndSaveCoins();
        }
    }

    private void checkAnswer() {
        int selectedOptionId = rgOptions.getCheckedRadioButtonId();
        if (selectedOptionId == -1) {
            Toast.makeText(this, "Pilih jawaban terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = 0;
        if (selectedOptionId == R.id.rbOption1) selectedIndex = 1;
        else if (selectedOptionId == R.id.rbOption2) selectedIndex = 2;
        else if (selectedOptionId == R.id.rbOption3) selectedIndex = 3;

        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (selectedIndex == currentQuestion.correctOptionIndex) {
            correctAnswersCount++;
        }

        currentQuestionIndex++;
        loadQuestionToScreen();
    }

    private void finishQuizAndSaveCoins() {
        // Kalkulasi: 1 Benar = 4 Koin
        int earnedCoins = correctAnswersCount * 4;

        // Bonus Perfect Session: Jika benar semua 5/5, tambah 5 koin (Total 25 Koin)
        if (correctAnswersCount == questionList.size()) {
            earnedCoins += 5;
        }

        // Integrasi dengan arsitektur pusat
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int oldCoins = prefs.getInt("TOTAL_KOIN", 0);
        int totalNewCoins = oldCoins + earnedCoins;

        // JL: Limit harian maksimal 50 koin bisa dikembangkan dengan mengecek tanggal di sini nanti

        editor.putInt("TOTAL_KOIN", totalNewCoins);
        editor.apply();

        Toast.makeText(this, "Kuis Selesai! Benar: " + correctAnswersCount + " | + " + earnedCoins + " Koin", Toast.LENGTH_LONG).show();
        finish(); // Kembali ke MainActivity
    }
}