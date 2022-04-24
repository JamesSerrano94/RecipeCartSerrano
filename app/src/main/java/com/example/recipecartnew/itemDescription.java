package com.example.recipecartnew;

import androidx.annotation.NonNull;

public class itemDescription{
    String name;
    double amount;
    String unit;


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
        this.unit = unit; }

    public String getName(){
        return name; }

    @NonNull
    @Override
    public String toString() {
        StringBuilder fullDescription = new StringBuilder(name);

        fullDescription.append(" ");
        if (amount %1 < .01){
            fullDescription.append(String.valueOf((int)amount));
        } else {
            fullDescription.append(String.valueOf(amount)); }
        fullDescription.append(" ");
        fullDescription.append(String.valueOf(unit));

        return fullDescription.toString();
    }

    public double getAmount(){
        return amount;}

    public void setAmount(double amount){
        this.amount = amount;}
}