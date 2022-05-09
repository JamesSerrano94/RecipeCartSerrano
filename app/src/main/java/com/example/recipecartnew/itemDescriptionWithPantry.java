package com.example.recipecartnew;

public class itemDescriptionWithPantry{
    private itemDescription item;
    double number;

    public itemDescriptionWithPantry(itemDescription item, double number){
        this.item = item;
        this.number = number;
    }


    public String toString(){
        StringBuilder name = new StringBuilder(item.toString());
        name.append(" (");

        if (number %1 < .01 || number %1 > .99){
            name.append(String.valueOf((int)number));
        } else {
            name.append(String.format("%.2f", number)); }
        name.append(")");

        return name.toString();
    }


}
