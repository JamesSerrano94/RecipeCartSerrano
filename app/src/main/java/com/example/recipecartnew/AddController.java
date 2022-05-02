package com.example.recipecartnew;

import java.util.ArrayList;

public class AddController {
    private AddIngredientTest model;
    private AddView view;

    public AddController(AddIngredientTest model, AddView view){
        this.model = model;
        this.view = view;
    }

    public void add(String itemName, double amount, String unit){
        this.model.add(itemName, amount, unit);
    }

    public void add(String itemName){
        this.model.add(itemName);
    }

    public void remove(String itemName, double amount, String unit){
        this.model.remove(itemName, amount, unit);
    }

    public void remove(String itemName){
        this.model.remove(itemName);
    }

    public void setItemList(ArrayList<itemDescription> itemList){
        this.model.setItemList(itemList);
    }

    public ArrayList<itemDescription> getItemList(){
        return this.model.getItemList();
    }

    public void updateView(){
        view.printIngredients(model.getItemList());
    }
}
