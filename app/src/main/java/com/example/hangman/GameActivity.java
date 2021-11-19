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

    private int currentHangManState = 1; // nb of fails

    private MediaPlayer music;

    String CurrentWord = "", CurrentCategory = "";

    List<Character> GuessedWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        CurrentCategory = GameHandler.getCurrentCategory();
        CurrentWord = GameHandler.getNextWord().toUpperCase();

        roundTv = findViewById(R.id.roundTv);

        roundTv.setText("Streak: " + GameHandler.getStreak());

        wordTv = findViewById(R.id.word);

        catBtn = findViewById(R.id.catBtn);

        catBtn.setEnabled(false);

        String word = GameHandler.getNextWord();
        ArrayList<String> list = GameHandler.getCategoryWords(CurrentCategory);
        if (list.contains(word)) {
            catBtn.setText(CurrentCategory);
        } else {
            catBtn.setText("Custom");
        }

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

        if (GameHandler.areSoundsOn == false) {
            music.pause();
            soundBtn.setImageResource(R.drawable.sound_off);
        }

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameHandler.areSoundsOn) {
                    music.pause();
                    soundBtn.setImageResource(R.drawable.sound_off);
                    GameHandler.areSoundsOn = false;
                } else {
                    music.start();
                    soundBtn.setImageResource(R.drawable.sound_on);
                    GameHandler.areSoundsOn = true;
                }
            }
        });
    }

    /**
     * This method updates the image after each fail
     */
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

    /**
     * This method checks if the character entered by user is valid or not
     * @param character character entered by user
     * @return boolean if valid or not
     */
    public Boolean IsCharValid(char character){
        char[] charArray = CurrentWord.toCharArray();
        for(char c : charArray){
            if(c == character){
                return true;
            }
        }
        return false;
    }

    /**
     * This method updates word after each character entered by user is correct
     */
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

    /**
     * This method checks if the user won or lose
     */
    public void CheckGameState(){
        if(CurrentWord.equalsIgnoreCase(wordTv.getText().toString().replace(" ", ""))){
            Intent i = new Intent(GameActivity.this, EndGameActivity.class);
            i.putExtra("guessedWord", CurrentWord);
            i.putExtra("won", "true");
            GameHandler.updateStreak(true);
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
            GameHandler.updateStreak(false);
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

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        music.start();
    }
}