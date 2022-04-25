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

public class AddIngredientFragment extends Fragment {
    List<itemDescription> pantryItems;
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_ingredient, container,false);

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
                String itemName = AddPantryFragment.parseItem(item);
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName); }
                else {
                    newItem = new itemDescription(itemName, Double.valueOf(String.valueOf(qnty.getText())), categories.get(unitSpinner.getSelectedItemPosition())); }
                if (itemName.length() > 0 && !AddPantryFragment.isInList(itemName)){
                    pantryItems.add(newItem);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}
                else if (itemName.length() > 0){
                    double newAmount = pantryItems.get(AddPantryFragment.getIndexOf(itemName)).getAmount();
                    newAmount += newItem.getAmount();
                    pantryItems.get(AddPantryFragment.getIndexOf(itemName)).setAmount(newAmount);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}



                addItem.setText("");
                qnty.setText("");}
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = String.valueOf(addItem.getText());
                String itemName = AddPantryFragment.parseItem(item);
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName);
                } else {
                    newItem = new itemDescription(itemName,
                            Double.valueOf(String.valueOf(qnty.getText())),
                            categories.get(unitSpinner.getSelectedItemPosition()));
                }
                if (AddPantryFragment.isInList(itemName)) {
                    if (itemName.length() > 0) {
                        double newAmount = pantryItems.get(AddPantryFragment.getIndexOf(itemName)).getAmount();
                        newAmount -= newItem.getAmount();
                        if (newAmount <= .01) {
                            pantryItems.remove(AddPantryFragment.getIndexOf(itemName));
                        } else {
                            pantryItems.get(AddPantryFragment.getIndexOf(itemName)).setAmount(newAmount);
                        }
                        pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        pantryList.setAdapter(pantryAdapter);
                    }
                }


                addItem.setText("");
                qnty.setText("");
            }
        });




        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}