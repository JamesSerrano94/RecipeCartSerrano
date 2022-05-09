package com.example.recipecartnew;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AddPantryFragment extends Fragment {
    static List<itemDescription> pantryItems;
    static ArrayList<itemDescription> pantryData;
    ArrayAdapter<itemDescription> pantryAdapter;

    static List<barcodeInfoStore> barcodeInfoStoreList;

    ListView pantryList;
    DatabaseHelper myDB;
    List<String> categories;
    String currentUser = User.getInstance().getUsername();
    User user = User.getInstance();
    protected static String trimString(String item) {
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
    protected static String parseItem(String item) {
        item = trimString(item);
        int count = item.length() - item.replace(" ", "").length();
        String[] newItem = item.split(" ", count + 1);
        StringBuilder str = new StringBuilder();
        for (String s : newItem) {
            if (s.length() > 0) {
                StringBuilder substr = new StringBuilder(s);
                substr.setCharAt(0, Character.toUpperCase(s.charAt(0)));
                for (int j = 1; j < s.length(); j++) {
                    substr.setCharAt(j, Character.toLowerCase(s.charAt(j)));
                }
                if (str.length() > 0) {
                    str.append(" ");
                }
                str.append(substr);

            }
        }



        return str.toString();

    }
    protected static boolean isInDatabase(String item){
        for (int i = 0; i < pantryData.size(); i++){
            if (pantryData.get(i).getName().equals(item)){
                return true; }
        }
        return false;}
    protected static double getAmount(String item){
        for (int i = 0; i<pantryData.size();i++){
            if (pantryData.get(i).getName().equals(item)){
                return pantryData.get(i).amount; }
        }
        return -1;
    }

    protected static boolean isInList(String item){
        for (int i = 0; i < pantryItems.size(); i++){
            if (pantryItems.get(i).getName().equals(item)){
                return true; }
        }
        return false;}
    protected static int getIndexOf(String item){
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
        myDB = new DatabaseHelper(getContext());
        ListView pantryList = (ListView) view.findViewById(R.id.recipeList2);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.unitSpinner);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        TextView addItem = (TextView) view.findViewById(R.id.addItemTxtField);
        TextView qnty = (TextView) view.findViewById(R.id.qntyTxtField);
        Button clearButton = (Button) view.findViewById(R.id.doneButton);
        Button removeButton = (Button) view.findViewById(R.id.removeButton);
        Button returnButton = (Button) view.findViewById(R.id.returnButton);
        ImageButton scanButton = (ImageButton) view.findViewById(R.id.imageButton4);
        TextView barcodeNumber = (TextView) view.findViewById(R.id.editTextBarcodeNumber);

        categories = new ArrayList<String>();
        categories.add(" ");
        if (user.getMeasureType().equals("Metric")){
            categories.add("Kgs");
            categories.add("L");}
        else {
            categories.add("Lbs");
            categories.add("Gallon"); }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(spinnerAdapter);

        pantryItems = new ArrayList<itemDescription>();
        pantryData = new ArrayList<itemDescription>();
        pantryData = myDB.getAllPantryData(currentUser);
        pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, pantryData);
        pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pantryList.setAdapter(pantryAdapter);

        barcodeInfoStoreList = new ArrayList<barcodeInfoStore>();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class BarcodeScanner extends Activity {
                    public void onCreate (Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.fragment_add_pantry);

                        try {
                            ImageButton scanButton = findViewById(R.id.imageButton4);
                            scanButton.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v) {
                                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                                    intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                                    startActivityForResult(intent, 0);
                                }
                            });
                        } catch (ActivityNotFoundException anfe) {
                            Log.e("onCreate", "Scanner Not Found", anfe);
                        }
                    }


                    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
                        String item = String.valueOf(addItem.getText());
                        String itemName = parseItem(item);

                        if (requestCode == 0) {
                            if (resultCode == RESULT_OK) {
                                String contents = intent.getStringExtra("SCAN_RESULT");
                                TextView barcodeDisplay = (TextView) findViewById(R.id.editTextBarcodeNumber);
                                Double num;
                                barcodeDisplay.setText(contents);

                                for (int i = 0; i < barcodeInfoStoreList.size(); i++) {
                                    if (contents.equals(barcodeInfoStoreList.get(i).getBarcode())) {
                                        // set info stored in barcodeInfoStore to the text fields on the pantry item page
                                        addItem.setText(barcodeInfoStoreList.get(i).getItemName());
                                        num = barcodeInfoStoreList.get(i).getNumber();
                                        qnty.setText(barcodeInfoStore.toString(num));
                                    }
                                }

                            } else if (resultCode == RESULT_CANCELED) {
                                TextView barcodeDisplay = (TextView) findViewById(R.id.editTextBarcodeNumber);
                                barcodeDisplay.setText("INVALID BARCODE");
                            }
                        }
                    }

                }

                BarcodeScanner scanner = new BarcodeScanner();
                scanner.onCreate(savedInstanceState);
            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = String.valueOf(addItem.getText());
                String itemName = parseItem(item);
                itemDescription newItem = null;

                for (int i = 0; i < barcodeInfoStoreList.size(); i++) {

                    if (barcodeNumber.equals(barcodeInfoStoreList.get(i).getBarcode())) {
                        newItem = new itemDescription(barcodeInfoStoreList.get(i).getItemName(),
                                barcodeInfoStoreList.get(i).getNumber(), categories.get(unitSpinner.getSelectedItemPosition()));
                    }
                }

                if (newItem == null) {
                    barcodeInfoStoreList.add(new barcodeInfoStore(barcodeNumber.getText().toString(), itemName,
                            Double.valueOf(String.valueOf(qnty.getText())), categories.get(unitSpinner.getSelectedItemPosition())));

                    if (String.valueOf(qnty.getText()).equals("")) {
                        newItem = new itemDescription(itemName);
                    } else {
                        newItem = new itemDescription(itemName, Double.valueOf(String.valueOf(qnty.getText())),
                                categories.get(unitSpinner.getSelectedItemPosition()));
                    }
                }

                if (itemName.length() > 0 && !isInDatabase(itemName)){
                    pantryItems.add(newItem);
                    myDB.insertDataPantry(currentUser,newItem.name,newItem.amount,newItem.unit);
                    pantryData = myDB.getAllPantryData(currentUser);
                    pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item, pantryData);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}
                else if (itemName.length() > 0){
                    double newAmount = getAmount(itemName);
                    newAmount += newItem.getAmount();

                    //pantryItems.get(getIndexOf(itemName)).setAmount(newAmount);
                    myDB.updatePantryData(currentUser,newItem.name, newAmount,newItem.unit);
                    pantryData= myDB.getAllPantryData(currentUser);

                    pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item, pantryData);
                    pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pantryList.setAdapter(pantryAdapter);}


                addItem.setText("");
                qnty.setText("");}
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
                if (isInDatabase(itemName)) {
                    if (itemName.length() > 0) {
                        double newAmount = getAmount(itemName);
                        newAmount -= newItem.getAmount();

                        if (newAmount <= .01) {
                            //pantryItems.remove(getIndexOf(itemName));
                            myDB.deletePantryData(currentUser,itemName);
                            pantryData= myDB.getAllPantryData(currentUser);
                        } else {
                            //pantryData.get(getIndexOf(itemName)).setAmount(newAmount);
                            myDB.updatePantryData(currentUser,newItem.name, newAmount,newItem.unit);
                            pantryData= myDB.getAllPantryData(currentUser);

                        }
                        pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(),
                                android.R.layout.simple_spinner_item, pantryData);
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
                //pantryItems.removeAll(pantryItems);
                myDB.clearUserPantry(currentUser);
                pantryData = myDB.getAllPantryData(currentUser);
                pantryAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_item, pantryData);
                pantryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pantryList.setAdapter(pantryAdapter);
                addItem.setText("");
                qnty.setText("");
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new PantryFragment()).commit();

            }
        });
    }


}