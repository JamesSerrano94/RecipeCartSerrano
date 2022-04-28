package com.example.recipecartnew;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SearchFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 6;

    //FeedReaderDbHelper helper;
    DatabaseHelper myDB;
    List<recipeDescription> recipes = new ArrayList<>();
    RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchFragment newInstance(int columnCount) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //helper= new FeedReaderDbHelper(this.getContext());
        myDB = new DatabaseHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearch);

        //recipes = helper.loadHandler();
        myDB.insertDataGlobalRecipe("Miso-Butter Roast Chicken With Acorn Squash Panzanella",
                        "1 (3½–4-lb.) whole chicken, 2¾ tsp. kosher salt, 2 small acorn squash (about 3 lb. total), 2 Tbsp. finely chopped sage, 1 Tbsp. finely chopped rosemary, 6 Tbsp. unsalted butter, ¼ tsp. ground allspice, Pinch of crushed red pepper flakes, Freshly ground black pepper, ⅓ loaf good-quality sturdy white bread,  2 medium apples , 2 Tbsp. extra-virgin olive oil, ½ small red onion, 3 Tbsp. apple cider vinegar, 1 Tbsp. white miso, ¼ cup all-purpose flour, 2 Tbsp. unsalted butter, ¼ cup dry white wine, 2 cups unsalted chicken broth, 2 tsp. white miso, Kosher salt, freshly ground pepper",
                "Pat chicken dry with paper towels, season all over with 2 tsp. salt, and tie legs together with kitchen twine. Let sit at room temperature 1 hour.\n" +
                        "Meanwhile, halve squash and scoop out seeds. Run a vegetable peeler along ridges of squash halves to remove skin. Cut each half into ½\"-thick wedges; arrange on a rimmed baking sheet.\n" +
                        "Combine sage, rosemary, and 6 Tbsp. melted butter in a large bowl; pour half of mixture over squash on baking sheet. Sprinkle squash with allspice, red pepper flakes, and ½ tsp. salt and season with black pepper; toss to coat.\n" +
                        "Add bread, apples, oil, and ¼ tsp. salt to remaining herb butter in bowl; season with black pepper and toss to combine. Set aside.\n" +
                        "Place onion and vinegar in a small bowl; season with salt and toss to coat. Let sit, tossing occasionally, until ready to serve.\n" +
                        "Place a rack in middle and lower third of oven; preheat to 425°F. Mix miso and 3 Tbsp. room-temperature butter in a small bowl until smooth. Pat chicken dry with paper towels, then rub or brush all over with miso butter. Place chicken in a large cast-iron skillet and roast on middle rack until an instant-read thermometer inserted into the thickest part of breast registers 155°F, 50–60 minutes. (Temperature will climb to 165°F while chicken rests.) Let chicken rest in skillet at least 5 minutes, then transfer to a plate; reserve skillet.\n" +
                        "Meanwhile, roast squash on lower rack until mostly tender, about 25 minutes. Remove from oven and scatter reserved bread mixture over, spreading into as even a layer as you can manage. Return to oven and roast until bread is golden brown and crisp and apples are tender, about 15 minutes. Remove from oven, drain pickled onions, and toss to combine. Transfer to a serving dish.\n" +
                        "Using your fingers, mash flour and butter in a small bowl to combine.\n" +
                        "Set reserved skillet with chicken drippings over medium heat. You should have about ¼ cup, but a little over or under is all good. (If you have significantly more, drain off and set excess aside.) Add wine and cook, stirring often and scraping up any browned bits with a wooden spoon, until bits are loosened and wine is reduced by about half (you should be able to smell the wine), about 2 minutes. Add butter mixture; cook, stirring often, until a smooth paste forms, about 2 minutes. Add broth and any reserved drippings and cook, stirring constantly, until combined and thickened, 6–8 minutes. Remove from heat and stir in miso. Taste and season with salt and black pepper.\n" +
                        "Serve chicken with gravy and squash panzanella alongside.",R.drawable.miso_butter_roast_chicken_acorn_squash_panzanella);
        recipes = myDB.getAllGlobalRecipes();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SearchRecyclerViewAdapter(recipes));

        return view;
    }




//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.PantryButton:
//                getParentFragmentManager().beginTransaction().replace(this.getId(),
//                        new AddPantryFragment()).commit();
//                return;
//        }
//    }
}