package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView roundTv, wordTv;
    private Button catBtn;
    private ImageButton soundBtn, home;

    private ImageView hangManImage;

    private int currentHangManState = 1;

    private MediaPlayer music;

    String CurrentWord = "", CurrentCategory = "";

    List<Character> GuessedWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        CurrentCategory = GameHandler.GetCurrentCategory();
        CurrentWord = GameHandler.GetNextWord().toUpperCase();

        roundTv = findViewById(R.id.roundTv);

        roundTv.setText("Streak: " + GameHandler.GetStreak());

        wordTv = findViewById(R.id.word);

        catBtn = findViewById(R.id.catBtn);

        catBtn.setEnabled(false);
        catBtn.setText(CurrentCategory);

        hangManImage = findViewById(R.id.hangImage);

        UpdateWord();

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameActivity.this, MainActivity.class);
                music.stop();
                startActivity(i);
            }
        });

        Button[] buttons = new Button[]{
                findViewById(R.id.btnA), findViewById(R.id.btnB), findViewById(R.id.btnC),
                findViewById(R.id.btnD), findViewById(R.id.btnE), findViewById(R.id.btnF),
                findViewById(R.id.btnG), findViewById(R.id.btnH), findViewById(R.id.btnI),
                findViewById(R.id.btnJ), findViewById(R.id.btnK), findViewById(R.id.btnL),
                findViewById(R.id.btnD), findViewById(R.id.btnE), findViewById(R.id.btnF),
                findViewById(R.id.btnG), findViewById(R.id.btnH), findViewById(R.id.btnI),
                findViewById(R.id.btnM), findViewById(R.id.btnN), findViewById(R.id.btnO),
                findViewById(R.id.btnP), findViewById(R.id.btnQ), findViewById(R.id.btnR),
                findViewById(R.id.btnS), findViewById(R.id.btnT), findViewById(R.id.btnU),
                findViewById(R.id.btnV), findViewById(R.id.btnW), findViewById(R.id.btnX),
                findViewById(R.id.btnY), findViewById(R.id.btnZ)};

        for(Button btn : buttons){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    char character = btn.getText().toString().charAt(0);
                    if(IsCharValid(character)){
                        btn.setTextColor(Color.parseColor("#88E684"));
                        GuessedWords.add(character);
                        UpdateWord();
                    }else {
                        currentHangManState++;
                        btn.setTextColor(Color.parseColor("#C9132A"));
                        UpdateImage();

                        //Vibrate On Wrong Character
                        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        if (!(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT)) {
                            Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(500);
                            }
                        }
                    }
                    btn.setEnabled(false);
                    CheckGameState();//Make Sure The Game Hasn't Ended
                }
            });
        }

        music = MediaPlayer.create(GameActivity.this, R.raw.rope_music);
        music.setVolume(0.3f, 0.3f);
        music.start();
        music.setLooping(true);

        soundBtn = findViewById(R.id.btnSound);

        if (GameHandler.AreSoundsOn == false) {
            music.pause();
            soundBtn.setImageResource(R.drawable.sound_off);
        }

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameHandler.AreSoundsOn) {
                    music.pause();
                    soundBtn.setImageResource(R.drawable.sound_off);
                    GameHandler.AreSoundsOn = false;
                } else {
                    music.start();
                    soundBtn.setImageResource(R.drawable.sound_on);
                    GameHandler.AreSoundsOn = true;
                }
            }
        });
    }

    public void UpdateImage(){
        switch(currentHangManState){
            case 2: hangManImage.setImageResource(R.drawable.hangman2); break;
            case 3: hangManImage.setImageResource(R.drawable.hangman3); break;
            case 4: hangManImage.setImageResource(R.drawable.hangman4); break;
            case 5: hangManImage.setImageResource(R.drawable.hangman5); break;
            case 6: hangManImage.setImageResource(R.drawable.hangman6); break;
            case 7: hangManImage.setImageResource(R.drawable.hangman7); break;
            case 8: hangManImage.setImageResource(R.drawable.hangman8); break;
            default: hangManImage.setImageResource(R.drawable.hangman1); break;
        }

    }

    public Boolean IsCharValid(char character){
        char[] charArray = CurrentWord.toCharArray();
        for(char c : charArray){
            if(c == character){
                return true;
            }
        }
        return false;
    }

    public void UpdateWord(){
        String text = "";
        for(int i = 0; i < CurrentWord.length(); i++){
            if(GuessedWords.contains(CurrentWord.charAt(i))){
                text += CurrentWord.charAt(i) + " ";
                wordTv.setText(text);
            }else{
                text += "_ ";
                wordTv.setText(text);
            }
        }
    }

    public void CheckGameState(){
        if(CurrentWord.equalsIgnoreCase(wordTv.getText().toString().replace(" ", ""))){
            Intent i = new Intent(GameActivity.this, EndGameActivity.class);
            i.putExtra("guessedWord", CurrentWord);
            i.putExtra("won", "true");
            GameHandler.UpdateStreak(true);
            music.stop();
            startActivity(i);
        }else if(currentHangManState == 8){//Game Lost
            //Vibrate On Wrong Character
            Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(1000);
            }
            Intent i = new Intent(GameActivity.this, EndGameActivity.class);
            i.putExtra("guessedWord", CurrentWord);
            i.putExtra("won", "false");
            GameHandler.UpdateStreak(false);
            music.stop();
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){//To Prevent Loading Earlier Intent States
        Intent setIntent = new Intent(this, MainActivity.class);
        music.stop();
        startActivity(setIntent);
    }

}