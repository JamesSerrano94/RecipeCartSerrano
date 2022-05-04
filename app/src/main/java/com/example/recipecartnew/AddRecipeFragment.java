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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddRecipeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddRecipeFragment extends Fragment  implements View.OnClickListener{
    static List<itemDescription> recipeItems;
    static ArrayList<itemDescription> recipeData;
    ArrayAdapter<itemDescription> recipeAdapter;
    ListView recipeList;
    List<String> categories;
    TextView addItem, qnty;
    Spinner unitSpinner;
    String item, itemName;
    itemDescription newItem;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddRecipeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddRecipeFragment newInstance(String param1, String param2) {
//        AddRecipeFragment fragment = new AddRecipeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        Button button;
        button = (Button)view.findViewById(R.id.upload);
        addItem = (TextView) view.findViewById(R.id.addItemTxtField2);
        qnty = (TextView) view.findViewById(R.id.qntyTxtField2);
        button.setOnClickListener((View.OnClickListener) this);
        Button button2;
        button2 = view.findViewById(R.id.addButton2);
        button2.setOnClickListener( this);
        Button button3 = view.findViewById(R.id.removeButton2);
        button3.setOnClickListener(this);
        unitSpinner = (Spinner) view.findViewById(R.id.unitSpinner2);
        User currentUser = User.getInstance();
        recipeList = (ListView) view.findViewById(R.id.recipeList2);

        System.out.println("A");
        recipeItems = new ArrayList<itemDescription>();
        recipeAdapter = new ArrayAdapter<itemDescription>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, recipeItems);
        System.out.println("B");
        recipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (recipeAdapter == null){System.out.println("Yes please\n");}
        if (recipeList == null){System.out.println("No please\n");}
        System.out.println("C");
        recipeList.setAdapter(recipeAdapter);

        System.out.println("D");
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
        if (unitSpinner == null){System.out.println("Spinner\n");}
        if (spinnerAdapter == null){System.out.println("adapter\n");}
        unitSpinner.setAdapter(spinnerAdapter);
        System.out.println("E");
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addButton2:
                item = String.valueOf(addItem.getText());
                itemName = AddPantryFragment.parseItem(item);
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
                qnty.setText("");
                return;
            case R.id.removeButton2:
                item = String.valueOf(addItem.getText());
                itemName = AddPantryFragment.parseItem(item);
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
                return;
            case R.id.upload:
                getParentFragmentManager().beginTransaction().replace(this.getId(),
                        new RecipeFragment()).commit();
                return;
        }

    }

}