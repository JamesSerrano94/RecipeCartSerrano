package com.example.recipecartnew;

import java.util.ArrayList;

public class AddView {

    public void printIngredients(ArrayList<itemDescription> itemList){
        System.out.println("Item List: ");
        for(int i=0; i<itemList.size(); i++) {
            System.out.print("Name: " + itemList.get(i).getName() + " ");
            System.out.println("Amount of Ingredient: " + String.format("%.2f", itemList.get(i).getAmount())+ " ");
            System.out.println("Unit of Measure: " + itemList.get(i).getUnit());
        }
    }
}
