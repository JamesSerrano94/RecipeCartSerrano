package com.example.pantry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    List<itemDescription> pantryItems;
    List<String> categories;

    private class itemDescription{
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
            fullDescription.append(String.valueOf(amount));
            fullDescription.append(" ");
            fullDescription.append(String.valueOf(unit));

            return fullDescription.toString();
        }

        private double getAmount(){
            return amount;}

        private void setAmount(double amount){
            this.amount = amount;}
    }

    private boolean isInList(String item){
        for (int i = 0; i < pantryItems.size(); i++){
            if (pantryItems.get(i).getName().equals(item)){
                return true; }
        }
        return false;}

    private int getIndexOf(String item){
        for (int i = 0; i < pantryItems.size(); i++){
            if (pantryItems.get(i).getName().equals(item)){
                return i; }
        }
        return -1;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        Button addButton = (Button) findViewById(R.id.addButton);
        ListView pantryList = (ListView) findViewById(R.id.pantryList);
        TextView addItem = (TextView) findViewById(R.id.addItemTxtField);
        TextView qnty = (TextView) findViewById(R.id.qntyTxtField);

        categories = new ArrayList<String>();
        categories.add(" ");
        categories.add("Kgs");
        categories.add("L");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(spinnerAdapter);

        pantryItems = new ArrayList<itemDescription>();
        ArrayAdapter<itemDescription> pantryAdapter = new ArrayAdapter<itemDescription>(this, android.R.layout.simple_spinner_item, pantryItems);
        pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pantryList.setAdapter(pantryAdapter);



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = String.valueOf(addItem.getText());
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(item); }
                else {
                    newItem = new itemDescription(item, Double.valueOf(String.valueOf(qnty.getText())), categories.get(unitSpinner.getSelectedItemPosition())); }
                if (item.length() > 0 && !isInList(item)){
                    pantryItems.add(newItem);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}
                else if (item.length() > 0){
                    double newAmount = pantryItems.get(getIndexOf(item)).getAmount();
                    newAmount += newItem.getAmount();
                    pantryItems.get(getIndexOf(item)).setAmount(newAmount);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}

                addItem.setText(""); }
        });



    }
}