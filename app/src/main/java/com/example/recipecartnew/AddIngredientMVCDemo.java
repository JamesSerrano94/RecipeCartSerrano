package com.example.recipecartnew;

import java.util.ArrayList;

public class AddIngredientMVCDemo{

    public static void main(String[] args){
        AddIngredientTest model = new AddIngredientTest();
        model.add("Bread");
        model.add("Milk", 1, "L");
        model.add("Chicken", 3, "Kgs");
        AddView view = new AddView();
        AddController controller = new AddController(model, view);
        controller.updateView();
        System.out.println("");

        controller.add("Bread");
        controller.add("Juice", 3, "L");
        controller.add("Chicken", 2.1, "Kgs");
        System.out.println("After adding more Bread, Chicken, and Juice: ");
        controller.updateView();
        System.out.println("");


        System.out.println("After Removing Some Bread, all Chicken, and Soda");
        controller.remove("Bread");
        controller.remove("Chicken", 7, "Kgs");
        controller.remove("Soda", .5, "L");
        controller.updateView();
        if(controller.getItemList() != model.getItemList()){
            System.err.println("ERROR GETTING DATA");
        }
        ArrayList<itemDescription> newList = new ArrayList<itemDescription>();
        newList.add(new itemDescription("Sugar", 3, "Lbs"));
        newList.add(new itemDescription("Strawberries", 5, ""));
        controller.setItemList(newList);
        model.setItemList(newList);
        if(controller.getItemList() != newList){
            System.err.println("ERROR GETTING DATA");
        }
        
    }
}
