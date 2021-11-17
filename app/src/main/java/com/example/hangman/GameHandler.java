package com.example.hangman;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GameHandler {

    static int CurrentGameStreak = 0;

    static HashMap<String, ArrayList<String>> GameWordDataCache = new HashMap<String, ArrayList<String>>();
    static HashMap<String, ArrayList<String>> GameWordData = new HashMap<String, ArrayList<String>>(){
        {
            put("Animals", new ArrayList<String>() {
                {
                    add("dog");
                    add("cat");
                    add("frog");
                    add("wolf");
                    add("bear");
                    add("elephant");
                    add("donkey");
                    add("fish");
                    add("goat");
                    add("sheep");
                    add("lion");
                    add("tiger");
                    add("mouse");
                    add("snake");
                    add("monkey");
                    add("chicken");
                    add("cow");
                }
            });
            put("Sports", new ArrayList<String>() {
                {
                    add("baseball");
                    add("football");
                    add("tennis");
                    add("pong");
                    add("soccer");
                }
            });
            put("Food", new ArrayList<String>() {
                {
                    add("pasta");
                    add("steak");
                    add("lasagna");
                    add("pizza");
                    add("cheese");
                    add("butter");
                    add("sandwich");
                    add("bread");
                    add("egg");
                    add("bacon");
                    add("fish");
                }
            });
            put("Countries", new ArrayList<String>() {
                {
                    add("lebanon");
                    add("america");
                    add("africa");
                    add("germany");
                    add("italy");
                    add("spain");
                    add("belgium");
                    add("netherlands");
                    add("turkey");
                    add("china");
                    add("japan");
                    add("korea");
                    add("jordan");
                    add("egypt");
                    add("morocco");
                    add("tunisia");
                    add("qatar");
                }
            });
            put("Organs", new ArrayList<String>() {
                {
                    add("heart");
                    add("lungs");
                    add("brain");
                    add("eyes");
                    add("muscles");
                    add("liver");
                    add("kidneys");
                    add("skeleton");
                    add("stomach");
                    add("pancreas");
                    add("veins");
                }
            });
            put("Colors", new ArrayList<String>() {
                {
                    add("red");
                    add("green");
                    add("purple");
                    add("black");
                    add("white");
                    add("blue");
                    add("yellow");
                    add("grey");
                    add("pink");
                    add("gold");
                    add("cyan");
                    add("turquoise");
                    add("wheat");
                    add("brown");
                }
            });
        }
    };

    static String CurrentGameCategory = GenerateRandomCategory();

    public static int GetStreak(){
        return CurrentGameStreak;
    }

    public static void UpdateStreak(boolean won){
        if(won) CurrentGameStreak++;
        else CurrentGameStreak = 0;
    }

    public static String GetNextWord(){
        if(!GameWordDataCache.containsKey(CurrentGameCategory)) GameWordDataCache.put(CurrentGameCategory, new ArrayList<String>());
        Random rand = new Random();
        ArrayList<String> words = GetCategoryWords(CurrentGameCategory);
        String word = words.get(rand.nextInt(words.size()));
        while(GameWordDataCache.get(CurrentGameCategory).contains(word)){
            word = words.get(rand.nextInt(words.size()));
        }
        GameWordDataCache.get(CurrentGameCategory).add(word);
        return word;
    }

    public static ArrayList<String> GetCategoryWords(String category){
        ArrayList<String> names = new ArrayList<String>();
        for(String name : GameWordData.get(category)){
            names.add(name);
        }
        return names;
    }

    public static ArrayList<String> GetCategoryNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(String name : GameWordData.keySet()){
            names.add(name);
        }
        return names;
    }

    public static HashMap<String, Boolean> GetCategoryList(){
        HashMap<String, Boolean> names = new HashMap<String, Boolean>();
        for(String name : GameWordData.keySet()){
            names.put(name, IsCategoryAvailable(name));
        }
        return names;
    }

    public static String GetCurrentCategory(){
        if(!IsCategoryAvailable(CurrentGameCategory)){
            if(IsAnyCategoryAvailable()){
                String category = GenerateRandomCategory();
                CurrentGameCategory = category;
                return category;
            }else {
                CurrentGameCategory = "NO_CATEGORIES";
                return "NO_CATEGORIES";//Game Ended
            }
        }
        return CurrentGameCategory;
    }

    public static String GenerateRandomCategory(){
        Random rand = new Random();
        ArrayList<String> categories = GetCategoryNames();
        String category = categories.get(rand.nextInt(categories.size()));
        while(!IsCategoryAvailable(category)){
            category = categories.get(rand.nextInt(categories.size()));
        }
        return category;
    }

    public static void SetCurrentCategory(String category){
        if(!IsCategoryAvailable(category)) return;
        CurrentGameCategory = category;
    }

    public static boolean IsCategoryAvailable(String category){
        if(CurrentGameCategory == "NO_CATEGORIES") return false;
        if(GameWordData.containsKey(category)){
            if(GameWordDataCache.containsKey(category)){
                if(GameWordData.get(category).size() == GameWordDataCache.get(category).size()){
                    return false;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean IsAnyCategoryAvailable(){
        if(CurrentGameCategory == "NO_CATEGORIES") return false;
        boolean result = false;
        for(String category : GameWordData.keySet()){
            if(GameWordData.containsKey(category)){
                if(GameWordDataCache.containsKey(category)){
                    if(GameWordData.get(category).size() != GameWordDataCache.get(category).size()){
                        result = true;
                    }
                }else {
                    result = true;
                }
            }
        }
        return result;
    }

}
