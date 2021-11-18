package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EndGameActivity extends AppCompatActivity {

    private ImageView gif;
    private TextView result;
    private Button btnCateg, btnNewGame;
    private boolean won;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent currentIntent = getIntent();
        //Use these to know the data of the word (Preview it ex: The Word Was ...), And category to preview the old category before it change dif the words are finished, and know if the player won
        //String category = currentIntent.getStringExtra("currentCategory");
        String word = currentIntent.getStringExtra("guessedWord");
        won = Boolean.parseBoolean(currentIntent.getStringExtra("won"));

        String CurrentCategory = GameHandler.GetCurrentCategory();//Use this to get the current category and hte above one to get the old

        gif = findViewById(R.id.gif);
        checkResult(won);

        result = findViewById(R.id.result);
        getWord(won, word);

        if (won) {
            music = MediaPlayer.create(EndGameActivity.this, R.raw.win_music);
        } else {
            music = MediaPlayer.create(EndGameActivity.this, R.raw.lose_music);
        }
        music.setVolume(0.1f, 0.1f);
        music.start();

        btnCateg = findViewById(R.id.btnCateg);
        btnCateg.setText(CurrentCategory);
        btnCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameActivity.this, CategoriesActivity.class);
                i.putExtra("parent", "EndGameActivity");
                music.stop();
                startActivity(i);
            }
        });

        btnNewGame = findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndGameActivity.this, GameActivity.class);
                music.stop();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){//To Prevent Loading Earlier Intent States
        Intent setIntent = new Intent(this, MainActivity.class);
        music.stop();
        startActivity(setIntent);
    }

    public void checkResult(boolean won) {
        if (won) {
            Glide.with(this).load(R.drawable.stickman_happy1).into(gif);
        } else {
            Glide.with(this).load(R.drawable.stickman_sad1).into(gif);
        }
        gif.getLayoutParams().width = 700;
    }

    public void getWord(boolean won, String word) {
        if (won) {
            result.setText("Good Job, Current Win Streak: " + GameHandler.GetStreak());
        } else {
            result.setText(Html.fromHtml("Game Over, The Word Was <font color='#FF0000'>" + word + "</font>"));
        }
    }
}