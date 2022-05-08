package com.example.recipecartnew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddRecipeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddRecipeFragment extends Fragment implements View.OnClickListener{
    static List<itemDescription> recipeItems;
    static ArrayList<recipeDescription> recipeData;
    DatabaseHelper myDB;
    String currentUser = User.getInstance().getUsername();
    ArrayAdapter<itemDescription> recipeAdapter;
    UploadTask uploadTask;
    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pantry-ae39f.appspot.com");
    ListView recipeList;
    List<String> categories;
    TextView recipeTitle, recipeInstructions;
    TextView addItem, qnty;
    ImageView imageView;
    Uri imageURI;
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

    /**
     * Check if item already exists in the list.
     * @param item String
     * @return true if item already added
     */
    protected static boolean isInRecipeItems(String item){
        for (int i = 0; i < recipeItems.size(); i++){
            if (recipeItems.get(i).getName().equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get amount of item in ingredient list.
     * @param item
     * @return
     */
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

    /**
     * Get index of item in recipe item list.
     * @param item String
     * @return int index
     */
    protected static int getRecipeIndexOf(String item){
        for (int i = 0; i < recipeItems.size(); i++){
            if (recipeItems.get(i).getName().equals(item)){
                return i;
            }
        }
        return -1;
    }

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
        recipeTitle = (TextView) view.findViewById(R.id.RecipeName) ;
        imageView = (ImageView) view.findViewById(R.id.RecipeImage);
        recipeInstructions = (TextView) view.findViewById(R.id.Instructions);

        button.setOnClickListener((View.OnClickListener) this);
        Button button2;
        button2 = view.findViewById(R.id.addButton2);
        button2.setOnClickListener( this);
        Button button3 = view.findViewById(R.id.removeButton2);
        button3.setOnClickListener(this);
        Button button4 = view.findViewById(R.id.picture);
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
        categories.add("");
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
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddRecipeFragment.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addButton2:
                item = addItem.getText().toString();
                itemName = AddPantryFragment.parseItem(item);
                if (String.valueOf(qnty.getText()).equals("")) {
                    newItem = new itemDescription(itemName);
                    newItem.setUnit(categories.get(unitSpinner.getSelectedItemPosition()));
                }
                else {
                    newItem = new itemDescription(itemName, Double.valueOf(String.valueOf(qnty.getText())), categories.get(unitSpinner.getSelectedItemPosition())); }
                if (itemName.length() > 0 && !isInRecipeItems(itemName)){
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
                    newItem.setUnit(categories.get(unitSpinner.getSelectedItemPosition()));
                } else {
                    newItem = new itemDescription(itemName,
                            Double.valueOf(String.valueOf(qnty.getText())),
                            categories.get(unitSpinner.getSelectedItemPosition()));
                }
                if (isInRecipeItems(itemName)) {
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

            case R.id.picture:
                ImagePicker.Companion.with(AddRecipeFragment.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
                return;
            case R.id.upload:
                if(recipeTitle.getText().toString().isEmpty()){
                    recipeTitle.setError("Title cannot be empty");
                    if(recipeInstructions.getText().toString().isEmpty()){
                        recipeInstructions.setError("Instructions cannot be empty");
                    }
                    return;
                }
                if(recipeInstructions.getText().toString().isEmpty()){
                    recipeInstructions.setError("Instructions cannot be empty");
                    return;
                }

                String itemList = String.valueOf(recipeItems).substring(1, String.valueOf(recipeItems).length()-1);
                myDB = new DatabaseHelper(this.getContext());
                if (uploadProfileImage()) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myDB.insertDataUserRecipe(currentUser,String.valueOf(recipeTitle.getText()),itemList, String.valueOf(recipeInstructions.getText()),imageURI.getLastPathSegment());
                    recipeItems = new ArrayList<>();
                    getParentFragmentManager().beginTransaction().replace(this.getId(),
                            new RecipeFragment()).commit();
                    return;
                }
                myDB.insertDataUserRecipe(currentUser,String.valueOf(recipeTitle.getText()),itemList, String.valueOf(recipeInstructions.getText()), String.valueOf(R.drawable.ic_baseline_search_24));
                recipeItems = new ArrayList<>();
                getParentFragmentManager().beginTransaction().replace(this.getId(),
                        new RecipeFragment()).commit();
                return;
        }
    }

    private boolean uploadProfileImage() {
        if(imageURI != null) {
            uploadTask = storageReference.child("recipe_images/" + imageURI.getLastPathSegment()).putFile(imageURI);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    return;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            imageURI = data.getData();
            imageView.setImageURI(imageURI);
        }
    }
}