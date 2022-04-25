package com.example.recipecartnew;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;



public class AddFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container,false);
        Button button;
        button = (Button)view.findViewById(R.id.PantryButton);
        button.setOnClickListener((View.OnClickListener) this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PantryButton:
                getParentFragmentManager().beginTransaction().replace(this.getId(),
                        new AddPantryFragment()).commit();
                return;
//                NavHostFragment navHostFragment =
//                        (NavHostFragment) getParentFragmentManager()
//                                .findFragmentById(R.id.nav_host_fragment_activity_main);
//                NavController navController = navHostFragment.getNavController();
//                Navigation.findNavController(v).navigate(R.id.action_navigation_add_to_addPantryFragment2);
        }

    }



}