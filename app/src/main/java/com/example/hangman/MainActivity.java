package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button categories;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String CurrentCategory = GameHandler.GetCurrentCategory();

        categories = findViewById(R.id.btnCat);
        categories.setText(CurrentCategory);

        start = findViewById(R.id.btnStart);

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CatrgoriesActivity.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra("currentCategory", CurrentCategory);
                i.putExtra("parent", "MainActivity");
                startActivity(i);
            }
        });
    }
}