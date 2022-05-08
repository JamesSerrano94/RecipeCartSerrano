package com.example.recipecartnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRecipeViewFragment extends Fragment implements View.OnClickListener{
    TextView title;
    ImageView image;
    List<recipeDescription> recipes = new ArrayList<>();
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    HomeRecyclerViewAdapter.OnNoteListener monNoteListener;
    recipeDescription thisRecipe;
    ArrayList<itemDescription> ingredients;
    List<itemDescription> ingredientsList = new ArrayList<>();
    List<itemDescriptionWithPantry> ingredientsListWithPantry = new ArrayList<>();
    ArrayAdapter<itemDescriptionWithPantry> itemAdapter;
    static ArrayList<itemDescription> pantryData;
    DatabaseHelper myDB;
    String currentUser = User.getInstance().getUsername();



    public UserRecipeViewFragment() {
        // Required empty public constructor
    }

    public UserRecipeViewFragment(recipeDescription thisRecipe){
        this.thisRecipe = thisRecipe;
    }

    public UserRecipeViewFragment(HomeRecyclerViewAdapter.OnNoteListener obj){
        monNoteListener = obj;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_recipe_view, container, false);
        //super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.viewRecipeTitle2);
        ImageView image = view.findViewById(R.id.foodImage2);
        TextView description = view.findViewById(R.id.description2);
        ListView ingredientList = view.findViewById(R.id.recipeIngredientList2);
        myDB = new DatabaseHelper(getActivity());
        myDB.getAllPantryData(currentUser);

        pantryData = myDB.getAllPantryData(currentUser);

        //mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(recipes, monNoteListener);
        Button returnRecipesBtn2= (Button) view.findViewById(R.id.returnRecipesBtn2);
        returnRecipesBtn2.setOnClickListener(this::onClick);


        if (thisRecipe != null){
            title.setText(thisRecipe.getTitle());

            description.setText(thisRecipe.getInstructions());
            if(thisRecipe.getImageName()==-1){
                ingredients = thisRecipe.getItems(0);
            }else{
                image.setImageResource(thisRecipe.getImageName());
                ingredients =thisRecipe.getItems(0);
            }



            for (int i = 0; i < ingredients.size(); i++){
                //ingredientsList.add(ingredients[i]);
                String itemName = ingredients.get(i).getName();
                itemDescriptionWithPantry newItem;
                System.out.println(i + " " + ingredients.get(i).toString());
                if (!isInDatabase(itemName)){
                    newItem = new itemDescriptionWithPantry(ingredients.get(i), 0);
                }
                else {
                    double number = getAmount(itemName);
                    newItem = new itemDescriptionWithPantry(ingredients.get(i), number);
                }
                ingredientsListWithPantry.add(newItem);
            }
            itemAdapter = new ArrayAdapter<itemDescriptionWithPantry>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, ingredientsListWithPantry);
            itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ingredientList.setAdapter(itemAdapter);
        }
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnRecipesBtn2:
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new RecipeFragment()).commit();
                return;
        }
    }

}