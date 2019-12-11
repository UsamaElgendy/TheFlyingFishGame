package com.example.theflyingfishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgain;
    private TextView showScore;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        score = getIntent().getExtras().get("TotalScore").toString();
        showScore = findViewById(R.id.totalScore);
        startGameAgain = findViewById(R.id.play_again_btn);

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                 startActivity(intent);
            }
        });

        showScore.setText("Total score : " + score);
    }
}