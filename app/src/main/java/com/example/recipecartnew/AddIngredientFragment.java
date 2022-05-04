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
    static List<itemDescription> recipeItems;
    static ArrayList<itemDescription> recipeData;
    ArrayAdapter<itemDescription> recipeAdapter;
    ListView recipeList;
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_ingredient, container,false);

    }

    protected static boolean isInRecipeDatabase(String item){
        for (int i = 0; i < recipeItems.size(); i++){
            if (recipeItems.get(i).getName().equals(item)){
                return true; }
        }
        return false;}
    protected static double getRecipeAmount(String item){
        for (int i = 0; i<recipeItems.size();i++){
            if (recipeItems.get(i).getName().equals(item)){
                return recipeItems.get(i).amount; }
        }
        return -1;
    }
    protected static boolean isInRecipeList(String item){
        for (int i = 0; i < recipeItems.size(); i++){
            if (recipeItems.get(i).getName().equals(item)){
                return true; }
        }
        return false;}
    protected static int getRecipeIndexOf(String item){
        for (int i = 0; i < recipeItems.size(); i++){
            if (recipeItems.get(i).getName().equals(item)){
                return i; }
        }
        return -1;}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.unitSpinner);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        ListView recipeList = (ListView) view.findViewById(R.id.recipeList2);
        TextView addItem = (TextView) view.findViewById(R.id.addItemTxtField);
        TextView qnty = (TextView) view.findViewById(R.id.qntyTxtField);
        Button doneButton = (Button) view.findViewById(R.id.doneButton);
        Button removeButton = (Button) view.findViewById(R.id.removeButton);
        User currentUser = User.getInstance();

        recipeItems = new ArrayList<itemDescription>();
        recipeAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, recipeItems);

        recipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        recipeList.setAdapter(recipeAdapter);



        categories = new ArrayList<String>();
        categories.add(" ");
        if (currentUser.getMeasureType().equals("Metric")){
            categories.add("Kgs");
            categories.add("L");}
        else {
            categories.add("Lbs");
            categories.add("Gallon"); }
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, categories);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item,categories);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(spinnerAdapter);







        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = String.valueOf(addItem.getText());
                String itemName = AddPantryFragment.parseItem(item);
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName); }
                else {
                    newItem = new itemDescription(itemName, Double.valueOf(String.valueOf(qnty.getText())), categories.get(unitSpinner.getSelectedItemPosition())); }
                if (itemName.length() > 0 && !isInRecipeDatabase(itemName)){
                    recipeItems.add(newItem);
                    recipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    recipeList.setAdapter(recipeAdapter);
                }
                else if (itemName.length() > 0){
                    double newAmount = getRecipeAmount(itemName);
                    newAmount += newItem.getAmount();
                    recipeItems.get(getRecipeIndexOf(itemName)).setAmount(newAmount);
                    recipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    recipeList.setAdapter(recipeAdapter);
                }



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
                if (isInRecipeDatabase(itemName)) {
                    if (itemName.length() > 0) {
                        double newAmount = recipeItems.get(getRecipeIndexOf(itemName)).getAmount();
                        newAmount -= newItem.getAmount();
                        if (newAmount <= .01) {
                            recipeItems.remove(getRecipeIndexOf(itemName));
                        } else {
                            recipeItems.get(getRecipeIndexOf(itemName)).setAmount(newAmount);
                        }
                        recipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        recipeList.setAdapter(recipeAdapter);
                    }
                }


                addItem.setText("");
                qnty.setText("");
            }
        });




        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new AddRecipeFragment()).commit();

            }
        });
    }
}