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
public class RecipeViewFragment extends Fragment implements View.OnClickListener{



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView title;
    ImageView image;
    List<recipeDescription> recipes = new ArrayList<>();
    SearchRecyclerViewAdapter mSearchRecyclerViewAdapter;
    SearchRecyclerViewAdapter.OnNoteListener monNoteListener;
    recipeDescription thisRecipe;
    ArrayList<itemDescription> ingredients;
    List<itemDescription> ingredientsList = new ArrayList<>();
    List<itemDescriptionWithPantry> ingredientsListWithPantry = new ArrayList<>();
    ArrayAdapter<itemDescriptionWithPantry> itemAdapter;
    static ArrayList<itemDescription> pantryData;
    DatabaseHelper myDB;
    String currentUser = User.getInstance().getUsername();
    private Button returnBtn;



    public RecipeViewFragment() {
        // Required empty public constructor
    }

    public RecipeViewFragment(recipeDescription thisRecipe){
        this.thisRecipe = thisRecipe;
    }

    public RecipeViewFragment(SearchRecyclerViewAdapter.OnNoteListener obj){
        monNoteListener = obj;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeViewFragment newInstance(String param1, String param2) {
        RecipeViewFragment fragment = new RecipeViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



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

        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        //super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.viewRecipeTitle);
        ImageView image = view.findViewById(R.id.foodImage);
        TextView description = view.findViewById(R.id.description);
        ListView ingredientList = view.findViewById(R.id.recipeIngredientList);
        myDB = new DatabaseHelper(getActivity());
        myDB.getAllPantryData(currentUser);

        pantryData = myDB.getAllPantryData(currentUser);

        //mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(recipes, monNoteListener);
        Button returnRecipesBtn= (Button) view.findViewById(R.id.returnRecipesBtn);
        returnRecipesBtn.setOnClickListener(this::onClick);

        if (thisRecipe != null){
            title.setText(thisRecipe.getTitle());
            image.setImageResource(thisRecipe.getImageName());
            description.setText(thisRecipe.getInstructions());
            ingredients = thisRecipe.getItems();


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
            case R.id.returnRecipesBtn:
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new SearchFragment()).commit();
                return;
        }
    }

}