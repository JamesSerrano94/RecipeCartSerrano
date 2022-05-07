package com.example.recipecartnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class HomeFragment extends Fragment
{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 6;

    //FeedReaderDbHelper helper;
    DatabaseHelper myDB;
    List<recipeDescription> recipes = new ArrayList<>();
    String currentUser = User.getInstance().getUsername();

    RecyclerView recyclerView;
    TextView empty;
    private HomeRecyclerViewAdapter.OnNoteListener monNoteListener;
    private HomeRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {

    }

    public void acceptsInterfaceObject(HomeRecyclerViewAdapter.OnNoteListener obj){
        monNoteListener = obj;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        empty = view.findViewById(R.id.emptyRecipes);
        recipes = myDB.getRecommendedRecipes(currentUser);
        if(recipes.size()==0){
            empty.setText("No match, please add new recipes and pantry items");
        }else{
            empty.setText("");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (monNoteListener == null) {
            acceptsInterfaceObject(new HomeRecyclerViewAdapter.OnNoteListener(){

                @Override
                public void onNoteClick(int position) {

                    getParentFragmentManager().beginTransaction().replace(getId(),
                            new UserRecipeViewFragment(recipes.get(position))).commit();
                    return;

                }
            });
        }
        recyclerView.setAdapter(new HomeRecyclerViewAdapter(recipes, monNoteListener));

        return view;
    }



}