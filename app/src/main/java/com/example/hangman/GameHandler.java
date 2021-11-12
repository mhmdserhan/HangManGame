package com.example.hangman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class GameHandler {

    public static String CurrentGameCategory = "Animals";

    static HashMap<String, ArrayList<String>> Words = new HashMap<String, ArrayList<String>>(){
        {
            put("Animals", new ArrayList<String>() {
                {
                    add("dog");
                    add("cat");
                    add("frog");
                    add("wolf");
                }
            });
            put("Sports", new ArrayList<String>() {
                {
                    add("baseball");
                    add("basketball");
                    add("football");
                    add("soccer");
                }
            });
            put("Food", new ArrayList<String>() {
                {
                    add("pasta");
                    add("steak");
                    add("lasagna");
                    add("pizza");
                }
            });
            put("Countries", new ArrayList<String>() {
                {
                    add("lebanon");
                    add("america");
                    add("africa");
                    add("germany");
                }
            });
            put("Organs", new ArrayList<String>() {
                {
                    add("heart");
                    add("lungs");
                    add("brain");
                    add("eyes");
                }
            });
            put("Colors", new ArrayList<String>() {
                {
                    add("red");
                    add("green");
                    add("purple");
                    add("black");
                }
            });
        }
    };

    public static void Test(){
        
    }

}
