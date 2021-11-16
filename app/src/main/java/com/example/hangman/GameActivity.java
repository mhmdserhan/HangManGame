package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView catView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String CurrentWord = GameHandler.GetNextWord();

        String currentCategory = "";
        catView = findViewById(R.id.CatView);
        currentCategory = getIntent().getStringExtra("currentCategory");
        catView.setText(currentCategory);
    }
}