package com.example.recipecartnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pantry-ae39f-default-rtdb.firebaseio.com/");
    User currentUser = User.getInstance();
    private Button btnUpdate, btnPhoto, btnFavorites, btnLogout;
    private EditText password,username,email;
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container,false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner unitSpinnerUser = (Spinner) view.findViewById(R.id.unitSpinnerUser);
        btnUpdate = (Button) view.findViewById(R.id.update);
        btnPhoto = (Button) view.findViewById(R.id.pfp);
        btnFavorites = (Button) view.findViewById(R.id.favorites);
        btnLogout = (Button) view.findViewById(R.id.Logout);
        password = (EditText) view.findViewById(R.id.new_password);
        username = (EditText) view.findViewById(R.id.new_username);
        email = (EditText) view.findViewById(R.id.new_email);

        categories = new ArrayList<String>();
        categories.add("Imperial");
        categories.add("Metric");
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinnerUser.setAdapter(spinnerAdapter);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });



    }
    public void logout(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
    public void update(){
        final String user = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String e = email.getText().toString().trim();
        if(!user.isEmpty()){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(user)){
                        Toast.makeText(getActivity(), "Username already used", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        databaseReference.child("users").child(user).child("email").setValue(currentUser.getEmail());
                        databaseReference.child("users").child(user).child("password").setValue(currentUser.getPassword());
                        databaseReference.child("users").child(user).child("measureType").setValue(currentUser.getMeasureType());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            currentUser.setUsername(user);
        }
    }
}