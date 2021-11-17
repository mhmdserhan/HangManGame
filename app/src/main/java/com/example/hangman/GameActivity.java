package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView roundTv;
    private Button catBtn, returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String CurrentCategory = GameHandler.GetCurrentCategory();
        String CurrentWord = GameHandler.GetNextWord();

        roundTv = findViewById(R.id.roundTv);

        catBtn = findViewById(R.id.catBtn);
        returnBtn = findViewById(R.id.returnBtn);

        catBtn.setEnabled(false);
        catBtn.setText(CurrentCategory);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // buttons array
//        buttons = new Button[]{
//                findViewById(R.id.btnA), findViewById(R.id.btnB), findViewById(R.id.btnC),
//                findViewById(R.id.btnD), findViewById(R.id.btnE), findViewById(R.id.btnF),
//                findViewById(R.id.btnG), findViewById(R.id.btnH), findViewById(R.id.btnI),
//                findViewById(R.id.btnJ), findViewById(R.id.btnK), findViewById(R.id.btnL),
//                findViewById(R.id.btnD), findViewById(R.id.btnE), findViewById(R.id.btnF),
//                findViewById(R.id.btnG), findViewById(R.id.btnH), findViewById(R.id.btnI),
//                findViewById(R.id.btnM), findViewById(R.id.btnN), findViewById(R.id.btnO),
//                findViewById(R.id.btnP), findViewById(R.id.btnQ), findViewById(R.id.btnR),
//                findViewById(R.id.btnS), findViewById(R.id.btnT), findViewById(R.id.btnU),
//                findViewById(R.id.btnV), findViewById(R.id.btnW), findViewById(R.id.btnX),
//                findViewById(R.id.btnY), findViewById(R.id.btnZ)};

        // onclick letter use set textColor instead of setBackgroundColor.
    }
}