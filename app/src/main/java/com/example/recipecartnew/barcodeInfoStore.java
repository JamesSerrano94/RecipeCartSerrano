package com.example.recipecartnew;

public class barcodeInfoStore {
    private String barcode;
    private String itemName;
    private double number;
    private String unit;

    public barcodeInfoStore(String barcode) {
        this.barcode = barcode;
    }


    public barcodeInfoStore(String barcode, String itemName, double number, String unit){
        this.barcode = barcode;
        this.itemName = itemName;
        this.number = number;
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(CharSequence number) {
        if(number.length() == 0){
            this.number = 1;
        }
        else {
            this.number = Double.valueOf(String.valueOf(number));
        }
    }

    public static String toString(double number) {
        return Double.toString(number);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
