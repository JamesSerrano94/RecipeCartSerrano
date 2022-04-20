package com.example.recipecartnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AddPantryFragment extends Fragment {
    List<itemDescription> pantryItems;
    List<String> categories;
    private String trimString(String item) {
        StringBuilder str = new StringBuilder(item);
        for (int i = 0; i < item.length(); i++) {
            if (str.charAt(0) == ' ') {
                str = str.deleteCharAt(0);
            } else {
                i = 2 * item.length();
            }
        }

        return str.toString();
    }
    private String parseItem(String item) {
        item = trimString(item);
        int count = item.length() - item.replace(" ", "").length();
        String[] newItem = item.split(" ", count + 1);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < newItem.length; i++) {
            if (newItem[i].length() > 0) {
                StringBuilder substr = new StringBuilder(newItem[i]);
                substr.setCharAt(0, Character.toUpperCase(newItem[i].charAt(0)));
                for (int j = 1; j < newItem[i].length(); j++) {
                    substr.setCharAt(j, Character.toLowerCase(newItem[i].charAt(j)));
                }
                if (str.length() > 0) {
                    str.append(" ");
                }
                str.append(substr);

            }
        }



        return str.toString();

    }
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_pantry, container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.unitSpinner);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        ListView pantryList = (ListView) view.findViewById(R.id.pantryList);
        TextView addItem = (TextView) view.findViewById(R.id.addItemTxtField);
        TextView qnty = (TextView) view.findViewById(R.id.qntyTxtField);
        Button clearButton = (Button) view.findViewById(R.id.clearButton);
        Button removeButton = (Button) view.findViewById(R.id.removeButton);

        categories = new ArrayList<String>();
        categories.add(" ");
        categories.add("Kgs");
        categories.add("L");
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(spinnerAdapter);

        pantryItems = new ArrayList<itemDescription>();
        ArrayAdapter<itemDescription> pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, pantryItems);
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
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = String.valueOf(addItem.getText());
                String itemName = parseItem(item);
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName);
                } else {
                    newItem = new itemDescription(itemName,
                            Double.valueOf(String.valueOf(qnty.getText())),
                            categories.get(unitSpinner.getSelectedItemPosition()));
                }
                if (itemName.length() > 0) {
                    double newAmount = pantryItems.get(getIndexOf(itemName)).getAmount();
                    newAmount -= newItem.getAmount();
                    if (newAmount <= .01) {
                        pantryItems.remove(getIndexOf(itemName));
                    } else {
                        pantryItems.get(getIndexOf(itemName)).setAmount(newAmount);
                    }
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);
                }

                addItem.setText("");
                qnty.setText("");
            }
        });




        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pantryItems.removeAll(pantryItems);
                pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pantryList.setAdapter(pantryAdapter);
                addItem.setText("");
                qnty.setText("");
            }
        });
    }
}