package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

public class CatrgoriesActivity extends AppCompatActivity {

    private ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catrgories);

        HashMap<String, Boolean> CategoryList = GameHandler.GetCategoryList();
        listV = findViewById(R.id.list);
        ArrayAdapter<String> array = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        for(String name : CategoryList.keySet()){
            //Name is category name
            boolean isAvailable = CategoryList.get(name);
            array.add(name);
        }

        listV.setAdapter(array);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CatrgoriesActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}