package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button categories;
    private Button start, startCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String CurrentCategory = GameHandler.getCurrentCategory();

        GameHandler.resetCustomWord();

        categories = findViewById(R.id.btnCat);
        categories.setText(CurrentCategory);

        start = findViewById(R.id.btnStart);

        startCustom = findViewById(R.id.btnStartCustom);

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CategoriesActivity.class);
                i.putExtra("parent", "MainActivity");
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        startCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Custom Word");
                alert.setMessage("You can choose a custom word, and play the games with your friends!");

                final EditText input = new EditText(MainActivity.this);
                input.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));


                input.setRawInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                alert.setView(input);

                alert.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (input.length() >= 3 && input.length() <= 9) {
                            String word = input.getText().toString();
                            GameHandler.setCustomWord(word);
                            Intent i = new Intent(MainActivity.this, GameActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "Enter word length between 3 and 9", Toast.LENGTH_SHORT).show();
                        }
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
}