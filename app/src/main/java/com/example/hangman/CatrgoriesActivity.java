package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.HashMap;

public class CatrgoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catrgories);

        HashMap<String, Boolean> CategoryList = GameHandler.GetCategoryList();
        for(String name : CategoryList.keySet()){
            //Name is category name
            boolean isAvailable = CategoryList.get(name);
        }
    }
}