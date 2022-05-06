package com.example.recipecartnew;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class recipeDescription{
    private String title;
    private String Ingredients;
    private String Instructions;
    private User currentUser= User.getInstance();
    private int ImageName;
    private String Description;



    public recipeDescription(){

    }
    public recipeDescription(String title, String Ingredients, String Instructions, int ImageName) {
        this.title = title;
        this.Ingredients = Ingredients;
        this.Instructions = Instructions;
        this.ImageName = ImageName;
    }
    public recipeDescription(String title, String Ingredients, String Instructions) {
        this.title = title;
        this.Ingredients = Ingredients;
        this.Instructions = Instructions;
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
    public void setDescription(String description){
        Description = description;
    }
    public String getDescription(){
        return Description;
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
        Double amount= Double.parseDouble(words[count-1]);
        if(currentUser.getMeasureType().equals("Metric")){
            if(words[count].equals("Gallon")){
                amount = galToL(amount);
                words[count] = "L";
            }
            else if(words[count].equals("Lbs")){
                amount = lbsToKg(amount);
                words[count] = "Kgs";
            }
        }

        else if(currentUser.getMeasureType().equals("Imperial")){
            if(words[count].equals("L")){
                amount = lToGal(amount);
                words[count] = "Gallon";
            }
            else if(words[count].equals("Kgs")){
                amount = kgsToLbs(amount);
                words[count] = "Lbs";
            }
        }
        for (int i = 0; i < words.length - 2; i++){
            name.append(words[i]);
            if (i != words.length - 3){
                name.append(" ");
            }
        }

        itemDescription thisItem = new itemDescription(name.toString(), amount, words[count]);
        return thisItem;
    }

    public double kgsToLbs(double kgs){
        return kgs*2.205;
    }

    public double lbsToKg(double lbs){
        return lbs/2.205;
    }

    public double lToGal(double l){
        return l/3.785;
    }

    public double galToL(double gal){
        return gal*3.785;
    }

}
