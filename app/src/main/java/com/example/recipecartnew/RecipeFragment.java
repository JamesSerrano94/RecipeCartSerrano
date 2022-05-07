package com.example.recipecartnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;

    DatabaseHelper myDB;
    String currentUser = User.getInstance().getUsername();
    List<recipeDescription> recipe;
    TextView empty;

    private MyRecipeRecyclerViewAdapter.OnNoteListener monNoteListener;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }
    public void acceptsInterfaceObject(MyRecipeRecyclerViewAdapter.OnNoteListener obj){
        monNoteListener = obj;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeFragment newInstance(int columnCount) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DatabaseHelper(getActivity());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        FloatingActionButton button;
        button = (FloatingActionButton) view.findViewById(R.id.RecipeButton);
        button.setOnClickListener( this);
        empty = view.findViewById(R.id.emptyUserRecipes);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewRecipe);
        recipe = myDB.getAllUserRecipes(currentUser);
        if(recipe.size()==0){
            empty.setText("Please add new recipes");
        }else{
            empty.setText("");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (monNoteListener == null) {
            acceptsInterfaceObject(new MyRecipeRecyclerViewAdapter.OnNoteListener(){



                @Override
                public void onNoteClick(int position) {
                    //recipes.get(position);

                    getParentFragmentManager().beginTransaction().replace(getId(),
                            new RecipeViewFragment(recipe.get(position))).commit();
                    return;

                }
            });
        }
        recyclerView.setAdapter(new MyRecipeRecyclerViewAdapter(recipe,monNoteListener));

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RecipeButton:
                getParentFragmentManager().beginTransaction().replace(this.getId(),
                        new AddRecipeFragment()).commit();
                return;
        }
    }
}