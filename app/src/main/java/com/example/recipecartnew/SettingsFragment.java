package com.example.recipecartnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private Spinner unitSpinnerUser;
    private String unit;
    private EditText password,confirmPass,username,email;
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container,false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unitSpinnerUser = (Spinner) view.findViewById(R.id.unitSpinnerUser);
        btnUpdate = (Button) view.findViewById(R.id.update);
        btnPhoto = (Button) view.findViewById(R.id.pfp);
        btnFavorites = (Button) view.findViewById(R.id.favorites);
        btnLogout = (Button) view.findViewById(R.id.Logout);
        password = (EditText) view.findViewById(R.id.new_password);
        username = (EditText) view.findViewById(R.id.new_username);
        email = (EditText) view.findViewById(R.id.new_email);
        confirmPass = (EditText) view.findViewById(R.id.new_confirm);

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
        currentUser.logout();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
    public void update(){
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();
        String e = email.getText().toString().trim();
        if(!user.isEmpty()){
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(user.equals(currentUser.getUsername())){
                        username.setError("Please enter new username");
                    }
                    else if(snapshot.hasChild(user)){
                        username.setError("Username already in use");
                    }
                    else {
                        databaseReference.child("users").child(user).child("email").setValue(currentUser.getEmail());
                        databaseReference.child("users").child(user).child("password").setValue(currentUser.getPassword());
                        databaseReference.child("users").child(user).child("measureType").setValue(currentUser.getMeasureType());
                        databaseReference.child("users").child(currentUser.getUsername()).removeValue();
                        currentUser.setUsername(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if(!e.isEmpty()){
            if(e.equals(currentUser.getEmail())){
                email.setError("Please enter a new email");
            }
            else {
                databaseReference.child("users").child(currentUser.getUsername()).child("email").setValue(e);
                currentUser.setEmail(e);
            }
        }

        if(!pass.isEmpty()){
            if(pass.equals(currentUser.getPassword())){
                password.setError("Please enter a new password");
            }

            else if(confirm.isEmpty()){
                confirmPass.setError("Confirmation cannot be empty");
            }

            else if(!pass.equals(confirm)){
                Toast.makeText(getActivity(), "Passwords are not matching", Toast.LENGTH_SHORT).show();
            }

            if(pass.length() < 6){
                password.setError("Password must be at least 6 characters");
            }

            else{
                databaseReference.child("users").child(currentUser.getUsername()).child("password").setValue(pass);
                currentUser.setPassword(pass);
            }
        }

        if(!currentUser.getMeasureType().equals(unit)){
            databaseReference.child("users").child(currentUser.getUsername()).child("measureType").setValue(unit);
            currentUser.setMeasureType(unit);
        }
    }
}