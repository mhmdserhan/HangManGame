package com.example.hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameHandler {

    static int currentGameStreak = 0; // win streak

    static boolean areSoundsOn = true;

    static String currentCustomWord = "";

    static HashMap<String, ArrayList<String>> gameWordDataCache = new HashMap<String, ArrayList<String>>(); // words found
    static HashMap<String, ArrayList<String>> gameWordData = new HashMap<String, ArrayList<String>>(){
        {
            put("Animals", new ArrayList<String>() {
                {
                    add("dog");
                    add("cat");
                    add("frog");
                    add("wolf");
                    add("bear");
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
                    add("wheat");
                    add("brown");
                }
            });
        }
    };

    static String currentGameCategory = generateRandomCategory();


    public static void setCustomWord(String word){
        currentCustomWord = word;
    }

    public static void resetCustomWord(){
        currentCustomWord = "";
    }

    public static int getStreak(){
        return currentGameStreak;
    }

    public static void updateStreak(boolean won){
        if(won) currentGameStreak++;
        else currentGameStreak = 0;
    }

    /**
     * This method get the next word in the category
     * @return String next word
     */
    public static String getNextWord(){
        if(!currentCustomWord.isEmpty()) return currentCustomWord;
        if(!gameWordDataCache.containsKey(currentGameCategory)) gameWordDataCache.put(currentGameCategory, new ArrayList<String>());
        Random rand = new Random();
        ArrayList<String> words = getCategoryWords(currentGameCategory);
        String word = words.get(rand.nextInt(words.size()));
        while(gameWordDataCache.get(currentGameCategory).contains(word)){
            word = words.get(rand.nextInt(words.size()));
        }
        gameWordDataCache.get(currentGameCategory).add(word);
        return word;
    }

    /**
     * This method gets the words from the category
     * @param category current category
     * @return ArrayList<String> list of words
     */
    public static ArrayList<String> getCategoryWords(String category){
        ArrayList<String> names = new ArrayList<String>();
        for(String name : gameWordData.get(category)){
            names.add(name);
        }
        return names;
    }

    /**
     * This method gets the categories from gameWordData map
     * @return ArrayList<String> list of categories names
     */
    public static ArrayList<String> getCategoryNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(String name : gameWordData.keySet()){
            names.add(name);
        }
        return names;
    }

    /**
     * This method gets available categories
     * @return HashMap<String, Boolean> categories names
     */
    public static HashMap<String, Boolean> getCategoryList(){
        HashMap<String, Boolean> names = new HashMap<String, Boolean>();
        for(String name : gameWordData.keySet()){
            names.put(name, isCategoryAvailable(name));
        }
        return names;
    }

    /**
     * This method gets the category in progress
     * @return String current category
     */
    public static String getCurrentCategory(){
        if(!isCategoryAvailable(currentGameCategory)){
            if(isAnyCategoryAvailable()){
                String category = generateRandomCategory();
                currentGameCategory = category;
                return category;
            }else {
                currentGameCategory = "NO_CATEGORIES";
                return "NO_CATEGORIES";//Game Ended
            }
        }
        return currentGameCategory;
    }

    /**
     * This method picks a random category from categories list
     * @return String category
     */
    public static String generateRandomCategory(){
        Random rand = new Random();
        ArrayList<String> categories = getCategoryNames();
        String category = categories.get(rand.nextInt(categories.size()));
        while(!isCategoryAvailable(category)){
            category = categories.get(rand.nextInt(categories.size()));
        }
        return category;
    }

    public static void setCurrentCategory(String category){
        if(!isCategoryAvailable(category)) return;
        currentGameCategory = category;
    }

    /**
     * This method checks if category is available
     * @param category category to check it
     * @return boolean if available
     */
    public static boolean isCategoryAvailable(String category){
        if(currentGameCategory == "NO_CATEGORIES") return false;
        if(gameWordData.containsKey(category)){
            if(gameWordDataCache.containsKey(category)){
                if(gameWordData.get(category).size() == gameWordDataCache.get(category).size()){
                    return false;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * This method checks if any category available
     * @return boolean if available
     */
    public static boolean isAnyCategoryAvailable(){
        if(currentGameCategory == "NO_CATEGORIES") return false;
        boolean result = false;
        for(String category : gameWordData.keySet()){
            if(gameWordData.containsKey(category)){
                if(gameWordDataCache.containsKey(category)){
                    if(gameWordData.get(category).size() != gameWordDataCache.get(category).size()){
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
