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


/**
 * A fragment representing a list of Items.
 */
public class PantryFragment extends Fragment implements View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;

    DatabaseHelper myDB;
    String currentUser = User.getInstance().getUsername();
    String currentUserName = User.getInstance().getName();
    ArrayList<itemDescription> pantry;
    TextView empty;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PantryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PantryFragment newInstance(int columnCount) {
        PantryFragment fragment = new PantryFragment();
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
        View view = inflater.inflate(R.layout.fragment_pantry_list, container, false);
        FloatingActionButton button;
        TextView UserName = view.findViewById(R.id.yourPantry);
        button = (FloatingActionButton) view.findViewById(R.id.PantryButton);
        UserName.setText(currentUserName+"'s Pantry");
        button.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        empty = view.findViewById(R.id.emptyPantry);
        pantry = myDB.getAllPantryData(currentUser);
        if(pantry.size()==0){
            empty.setText("No match, please add new pantry items");
        }else{
            empty.setText("");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyPantryRecyclerViewAdapter(pantry));

        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PantryButton:
                getParentFragmentManager().beginTransaction().replace(this.getId(),
                        new AddPantryFragment()).commit();
                return;
        }
    }
}