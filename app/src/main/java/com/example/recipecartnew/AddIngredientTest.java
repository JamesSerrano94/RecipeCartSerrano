package com.example.recipecartnew;

import java.util.ArrayList;

public class AddIngredientTest {

    ArrayList<itemDescription> itemList = new ArrayList<itemDescription>();
    public void add(String itemName, double amount, String unit){
        if(amount <= 0){
            amount = 1;
        }
        if(unit.equals("None")){
            unit = "";
        }
        itemDescription newItem = new itemDescription(itemName, amount, unit);
        if(itemName.length() > 0 && inList(itemList, newItem)){
            double newAmount = itemList.get(indexOf(itemList,newItem)).getAmount()+amount;
            itemList.get(indexOf(itemList,newItem)).setAmount(newAmount);
        }

        else if(itemName.length() > 0) {
            itemList.add(newItem);
        }
    }

    public boolean inList(ArrayList<itemDescription> itemList, itemDescription newItem) {
        for(int i=0; i<itemList.size(); i++){
            if(itemList.get(i).getName().equals(newItem.getName())){
                return true;
            }
        }
        return false;
    }

    public void add(String itemName){
        double amount = 1;
        itemDescription newItem = new itemDescription(itemName, amount, "");
        if(itemName.length() > 0 && inList(itemList,newItem)){
            double newAmount = itemList.get(indexOf(itemList,newItem)).getAmount()+amount;
            itemList.get(indexOf(itemList,newItem)).setAmount(newAmount);
        }

        else if(itemName.length() > 0) {
            itemList.add(newItem);
        }
    }

    private int indexOf(ArrayList<itemDescription> itemList, itemDescription newItem) {
        for(int i=0; i<itemList.size(); i++){
            if(itemList.get(i).getName().equals(newItem.getName())){
                return i;
            }
        }
        return -1;
    }

    public void remove(String itemName, double amount, String unit){
        itemDescription newItem = new itemDescription(itemName, amount, unit);
        if(itemName.length() > 0) {
            if (inList(itemList,newItem) && itemList.get(indexOf(itemList,newItem)).getUnit().equals(newItem.getUnit())) {
                double newAmount = itemList.get(indexOf(itemList,newItem)).getAmount() - newItem.getAmount();
                if (newAmount <= .01){
                    itemList.remove(itemList.get(indexOf(itemList,newItem)));
                }
                else{
                    itemList.get(indexOf(itemList,newItem)).setAmount(newAmount);
                }
            }
            else{
                System.err.println("Please enter an item within the pantry to remove");
            }
        }
    }

    public void remove(String itemName){
        itemDescription newItem = new itemDescription(itemName, 1, "");
        if(itemName.length() > 0) {
            if (inList(itemList,newItem) && itemList.get(indexOf(itemList,newItem)).getUnit().equals(newItem.getUnit())) {
                double newAmount = itemList.get(indexOf(itemList,newItem)).getAmount() - newItem.getAmount();
                if (newAmount <= .01){
                    itemList.remove(itemList.get(indexOf(itemList,newItem)));
                }
                else{
                    itemList.get(indexOf(itemList,newItem)).setAmount(newAmount);
                }
            }

            else{
                System.err.println("Please enter an item within the pantry to remove");
            }
        }
    }

    public void setItemList(ArrayList<itemDescription> itemList){this.itemList = itemList;}
    public ArrayList<itemDescription> getItemList(){
        return this.itemList;
    }
}
