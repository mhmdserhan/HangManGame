package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private ListView listV;

    private String returnToParent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Intent currentIntent = getIntent();
        returnToParent = currentIntent.getStringExtra("parent");


        HashMap<String, Boolean> CategoryList = GameHandler.getCategoryList();
        List<String> CategoryNames = new ArrayList<>();
        List<Boolean> CategoryAvailability = new ArrayList<>();
        for(String name : CategoryList.keySet()){
            CategoryAvailability.add(CategoryList.get(name));
            CategoryNames.add(name);
        }
        listV = findViewById(R.id.list);
        CategoryListAdapter array = new CategoryListAdapter(this, R.layout.categorylistitem, CategoryNames, CategoryAvailability);

        listV.setAdapter(array);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(returnToParent.equalsIgnoreCase("EndGameActivity")){
                    Intent intent = new Intent(CategoriesActivity.this, GameActivity.class);
                    GameHandler.setCurrentCategory(array.getItem(i));
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
                    GameHandler.setCurrentCategory(array.getItem(i));
                    startActivity(intent);
                }


            }
        });
    }


}