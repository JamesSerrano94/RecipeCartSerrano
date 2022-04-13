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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.*;
import java.lang.*;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
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

    private class itemDescription {

        private String name;
        private double amount;
        private String unit;


        private itemDescription(String name) {
            this.name = name;
            amount = 1;
            unit = " ";
        }

        private itemDescription(String name, double amount, String unit) {
            this.name = name;
            if (amount <= 0) {
                this.amount = 1;
            } else {
                this.amount = amount;
            }
            this.unit = unit;
        }

        private String getName() {
            return name;
        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder fullDescription = new StringBuilder(name);

            fullDescription.append(" ");
            if (amount - Math.floor(amount) < .01) {
                fullDescription.append(String.valueOf((int) amount));
            } else {
                fullDescription.append(String.valueOf(amount));
            }
            fullDescription.append(" ");
            fullDescription.append(String.valueOf(unit));


            return fullDescription.toString();
        }

        private double getAmount() {
            return amount;
        }

        private void setAmount(double amount) {
            this.amount = amount;
        }
    }

    private boolean isInList(String item) {
        for (int i = 0; i < pantryItems.size(); i++) {
            if (pantryItems.get(i).getName().equals(item)) {
                return true;
            }
        }
        return false;
    }

    private int getIndexOf(String item) {
        for (int i = 0; i < pantryItems.size(); i++) {
            if (pantryItems.get(i).getName().equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);
        Button logout = (Button) findViewById(R.id.logout);
        Button settings = (Button) findViewById(R.id.settings);
        ListView pantryList = (ListView) findViewById(R.id.pantryList);
        TextView addItem = (TextView) findViewById(R.id.addItemTxtField);
        TextView qnty = (TextView) findViewById(R.id.qntyTxtField);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        mAuth = FirebaseAuth.getInstance();

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
                String itemName = parseItem(item);
                itemDescription newItem;
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName);
                } else {
                    newItem = new itemDescription(itemName,
                            Double.valueOf(String.valueOf(qnty.getText())),
                            categories.get(unitSpinner.getSelectedItemPosition()));
                }
                if (itemName.length() > 0 && !isInList(itemName)) {
                    pantryItems.add(newItem);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);
                } else if (itemName.length() > 0) {
                    double newAmount = pantryItems.get(getIndexOf(itemName)).getAmount();
                    newAmount += newItem.getAmount();
                    pantryItems.get(getIndexOf(itemName)).setAmount(newAmount);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);
                }

                addItem.setText("");
                qnty.setText("");
            }
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


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
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

        @Override
        public void onStart() {
            super.onStart();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        }

        public void logout() {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}