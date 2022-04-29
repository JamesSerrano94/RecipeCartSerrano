package com.example.recipecartnew;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class recipeDescription{
    private String title;
    private String Ingredients;
    private String Instructions;
    private int ImageName;



    public recipeDescription(){

    }
    public recipeDescription(String title, String Ingredients, String Instructions, int ImageName) {
        this.title = title;
        this.Ingredients = Ingredients;
        this.Instructions = Instructions;
        this.ImageName = ImageName;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder fullDescription = new StringBuilder(title);
        fullDescription.append(" ");
        fullDescription.append(Ingredients);
        fullDescription.append(" ");
        fullDescription.append(Instructions);

        return fullDescription.toString();
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageName() {
        return ImageName;
    }

    public void setImageName(int imageName) {
        ImageName = imageName;
    }

    public ArrayList<itemDescription> getItems(){
        int count = Ingredients.length() - Ingredients.replace("!", "").length();
        String[] items = Ingredients.split("!", count + 1);

        ArrayList<itemDescription> allIngredients = new ArrayList<>();

        for (int i = 0; i < items.length; i++){
            allIngredients.add(translateItem(items[i]));
        }

        return allIngredients;}

    public itemDescription translateItem(String items){
        //System.out.println(items);
        int count = items.length() - items.replace(" ", "").length();
        String[] words = items.split(" ", count + 1);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < words.length - 2; i++){
            name.append(words[i]);
            if (i != words.length - 3){
                name.append(" ");
            }
        }

        itemDescription thisItem = new itemDescription(name.toString(), Double.parseDouble(words[count - 1]), words[count]);
        return thisItem;
    }

}
