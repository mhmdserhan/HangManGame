package com.example.hangman;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CategoryListAdapter extends ArrayAdapter<String> {

    private int resourceLayout;
    private Context mContext;

    private List<Boolean> availablity = new ArrayList<>();

    public CategoryListAdapter(Context context, int resource, List<String> items, List<Boolean> availablity) {
        super(context, resource, items);
        this.availablity = availablity;
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        String p = getItem(position);

        if (p != null) {
            TextView categoryButton = (TextView) v.findViewById(R.id.categoryText);
            CardView categoryCardView = (CardView) v.findViewById(R.id.categoryCardView);
            if(categoryCardView != null){
                categoryCardView.setCardBackgroundColor(Color.parseColor(generateRandomColor()));
            }
            if (categoryButton != null) {
                categoryButton.setText(p);
            }
        }

        return v;
    }

    @Override
    public boolean isEnabled(int position) {//To Disable Finished Categories
        return availablity.get(position);
    }

    private String generateRandomColor(){//Generate Random Dark Color
        Random obj = new Random();
        int rand_num = obj.nextInt(0x646464 + 1);
        return String.format("#%06x", rand_num);
    }

}
