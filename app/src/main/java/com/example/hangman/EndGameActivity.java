package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EndGameActivity extends AppCompatActivity {

    private ImageView gif;
    private TextView result;
    private Button btnCateg, btnNewGame, btnNewGameExtra;
    private boolean won;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent currentIntent = getIntent();
        String word = currentIntent.getStringExtra("guessedWord");
        won = Boolean.parseBoolean(currentIntent.getStringExtra("won"));

        String CurrentCategory = GameHandler.GetCurrentCategory();//Use this to get the current category and hte above one to get the old

        GameHandler.ResetCustomWord();

        gif = findViewById(R.id.gif);
        checkResult(won);

        result = findViewById(R.id.result);
        getWord(won, word);

        if (won) {
            music = MediaPlayer.create(EndGameActivity.this, R.raw.win_music);
        } else {
            music = MediaPlayer.create(EndGameActivity.this, R.raw.lose_music);
        }
        music.setVolume(0.3f, 0.3f);
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

        btnNewGameExtra = findViewById(R.id.btnNewCustom);
        btnNewGameExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EndGameActivity.this);
                alert.setTitle("Custom Word");
                alert.setMessage("You can choose a custom word, and play the games with your friends!");

                final EditText input = new EditText(EndGameActivity.this);
                input.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));


                input.setRawInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                alert.setView(input);

                alert.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String word = input.getText().toString();
                        GameHandler.SetCustomWord(word);
                        Intent i = new Intent(EndGameActivity.this, GameActivity.class);
                        music.stop();
                        startActivity(i);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Nothing to do here, the dialog will close on its own
                    }
                });

                alert.show();
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