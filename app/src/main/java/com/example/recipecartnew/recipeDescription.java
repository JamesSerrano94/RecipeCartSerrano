package com.example.recipecartnew;

import androidx.annotation.NonNull;


public class recipeDescription{
    private String title;
    private itemDescription[] Ingredients;
    private String Instructions;
    private int ImageName;


    public recipeDescription(){

    }
    public recipeDescription(String title, itemDescription[] Ingredients, String Instructions, int ImageName) {
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

    public itemDescription[] getIngredients() {
        return Ingredients;
    }

    public void setIngredients(itemDescription[] ingredients) {
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
