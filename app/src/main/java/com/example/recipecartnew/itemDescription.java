package com.example.recipecartnew;

public class itemDescription {
    String name;
    double amount;
    String unit;



    public itemDescription() {
    }

    public itemDescription(String name){
        this.name = name;
        amount = 1;
        unit = " "; }


    public itemDescription(String name, double amount, String unit){
        this.name = name;
        if (amount <= 0){
            this.amount = 1; }
        else {
            this.amount = amount; }
        if (unit.equals("None")){
            this.unit = ""; }
        else {
            this.unit = unit; }
    }

    public String getName(){
        return name; }




    public String toString() {
        StringBuilder fullDescription = new StringBuilder(name);

        fullDescription.append(" ");
        if (amount %1 < .01 || amount %1 > .99){
            fullDescription.append(String.valueOf((int)amount));
        } else {
            fullDescription.append(String.format("%.2f", amount)); }
        fullDescription.append(" ");
        fullDescription.append(String.valueOf(unit));

        return fullDescription.toString();
    }

    public double getAmount(){
        return amount;}

    public void setAmount(double amount){
        this.amount = amount;}

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getUnit(){
        return this.unit;
    }
}