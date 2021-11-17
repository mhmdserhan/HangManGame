package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);


        Intent currentIntent = getIntent();
        //Use these to know the data of the word (Preview it ex: The Word Was ...), And category to preview the old category before it change dif the words are finished, and know if the player won
        String category = currentIntent.getStringExtra("currentCategory");
        String word = currentIntent.getStringExtra("guessedWord");
        boolean won =Boolean.parseBoolean(currentIntent.getStringExtra("won"));
        int streak = GameHandler.GetStreak();//Current win streak

        String CurrentCategory = GameHandler.GetCurrentCategory();//Use this to get the current category and hte above one to get the old

    }

    @Override
    public void onBackPressed(){//To Prevent Loading Earlier Intent States
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

}