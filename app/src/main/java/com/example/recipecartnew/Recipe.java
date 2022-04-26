package com.example.recipecartnew;

public class Recipe {

    private String name, description, instructions, imageURL;
    private boolean anon;
    private itemDescription[] ingredients;
    private User author;


    private Recipe(){ }


    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setInstructions(String instructions){
        this.instructions = instructions;
    }
    public String getInstructions(){
        return this.instructions;
    }

    public void setAnon(Boolean anon){
        this.anon = anon;
    }
    public Boolean isAnon(){
        return this.anon;
    }

    public void setImageURL(String URL){ this.imageURL = URL.trim();}
    public String getImageURL(){return this.imageURL;}

    public void setAuthor(User author){this.author = author;}
    public User getAuthor(){return this.author;}

    public void setIngredients(itemDescription[] ingredients){this.ingredients = ingredients;}
    public itemDescription[] getIngredients(){return ingredients;}
}
