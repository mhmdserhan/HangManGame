package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatrgoriesActivity extends AppCompatActivity {

    private ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catrgories);

        HashMap<String, Boolean> CategoryList = GameHandler.GetCategoryListSorted();
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
                Intent intent = new Intent(CatrgoriesActivity.this, MainActivity.class);
                GameHandler.SetCurrentCategory(array.getItem(i));
                startActivity(intent);
            }
        });
    }
}