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
}
