package com.example.recipecartnew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pantry-ae39f-default-rtdb.firebaseio.com/");
    User currentUser = User.getInstance();
    DatabaseHelper myDB;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pantry-ae39f.appspot.com");
    static ArrayList<itemDescription> pantryData;
    private Button btnUpdate, btnPhoto, btnFavorites, btnLogout;
    private Spinner unitSpinnerUser;
    private EditText password,confirmPass,name,email;
    private boolean updated=false;
    private ImageView imageView;
    List<String> categories;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container,false);
        btnPhoto = (Button) view.findViewById(R.id.pfp);
        btnPhoto.setOnClickListener(this::onClick);
        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unitSpinnerUser = (Spinner) view.findViewById(R.id.unitSpinnerUser);
        btnUpdate = (Button) view.findViewById(R.id.update);
        btnFavorites = (Button) view.findViewById(R.id.favorites);
        btnLogout = (Button) view.findViewById(R.id.Logout);
        password = (EditText) view.findViewById(R.id.new_password);
        name = (EditText) view.findViewById(R.id.new_name);
        email = (EditText) view.findViewById(R.id.new_email);
        confirmPass = (EditText) view.findViewById(R.id.new_confirm);
        imageView = (ImageView) view.findViewById(R.id.picture);
        myDB = new DatabaseHelper(getContext());
        categories = new ArrayList<String>();
        categories.add("Imperial");
        categories.add("Metric");

        if(currentUser.getImageURL() != null){
            imageView.setImageURI(Uri.parse(currentUser.getImageURL()));
        }
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinnerUser.setAdapter(spinnerAdapter);
        if (currentUser.getMeasureType().equals("Metric")){
            unitSpinnerUser.setSelection(1);}
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        confirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPass.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

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
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    public void update(){
        String person = name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();
        String e = email.getText().toString().trim();
        String unit = unitSpinnerUser.getSelectedItem().toString();
        if(!person.isEmpty()){
            if(person.equals(currentUser.getName())){
                name.setError("Please enter a new name");
            }
            else {
                databaseReference.child("users").child(currentUser.getUsername()).child("name").setValue(person);
                currentUser.setName(person);
                updated = true;
                name.getText().clear();
            }
        }

        if(!e.isEmpty()){
            String end = e.substring(e.length()-3, e.length());
            if(e.equals(currentUser.getEmail())){
                email.setError("Please enter a new email");
            }

            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()){
                email.setError("Invalid email address");
            }

            else if((!end.equals("com") && (!end.equals("edu")) && (!end.equals("gov")) && (!end.equals("net")))){
                email.setError("Invalid email address");
            }
            else {
                databaseReference.child("users").child(currentUser.getUsername()).child("email").setValue(e);
                currentUser.setEmail(e);
                email.getText().clear();
                updated = true;
            }
        }

        if(!pass.isEmpty()){
            if(pass.equals(currentUser.getPassword())){
                password.setError("Please enter a new password");
            }

            else if(pass.length() < 6){
                password.setError("Password must be at least 6 characters");
            }

            else if(confirm.isEmpty()){
                confirmPass.setError("Confirmation cannot be empty");
            }

            else if(!pass.equals(confirm)){
                Toast.makeText(getActivity(), "Passwords are not matching", Toast.LENGTH_SHORT).show();
            }

            else{
                databaseReference.child("users").child(currentUser.getUsername()).child("password").setValue(pass);
                currentUser.setPassword(pass);
                password.getText().clear();
                confirmPass.getText().clear();
                updated = true;
            }
        }
        if(!unit.equals(currentUser.getMeasureType())){
            pantryData = new ArrayList<itemDescription>();
            pantryData = myDB.getAllPantryData(currentUser.getUsername());
            if(currentUser.getMeasureType().equals("Imperial")){
                for(int i=0; i<pantryData.size(); i++){
                    //Log.d("OLD DATABASE", pantryData.get(i).toString());
                    if(pantryData.get(i).getUnit().equals("Lbs")){
                        pantryData.get(i).setUnit("Kgs");
                        pantryData.get(i).setAmount(lbsToKg(pantryData.get(i).getAmount()));
                        myDB.updatePantryData(currentUser.getUsername(),pantryData.get(i).getName(),pantryData.get(i).getAmount(),pantryData.get(i).getUnit());
                        //Log.d("UPDATED DATABASE", pantryData.get(i).toString());
                    }
                    else if(pantryData.get(i).getUnit().equals("Gallon")){
                        pantryData.get(i).setUnit("L");
                        pantryData.get(i).setAmount(galToL(pantryData.get(i).getAmount()));
                        myDB.updatePantryData(currentUser.getUsername(),pantryData.get(i).getName(),pantryData.get(i).getAmount(),pantryData.get(i).getUnit());
                        //Log.d("UPDATED DATABASE", pantryData.get(i).toString());
                    }
                }
            }

            else if(currentUser.getMeasureType().equals("Metric")){
                for(int i=0; i<pantryData.size(); i++){
                    if(pantryData.get(i).getUnit().equals("Kgs")){
                        pantryData.get(i).setUnit("Lbs");
                        pantryData.get(i).setAmount(kgsToLbs(pantryData.get(i).getAmount()));
                        myDB.updatePantryData(currentUser.getUsername(),pantryData.get(i).getName(),pantryData.get(i).getAmount(),pantryData.get(i).getUnit());
                        //Log.d("UPDATED DATABASE", pantryData.get(i).toString());

                    }
                    else if(pantryData.get(i).getUnit().equals("L")){
                        pantryData.get(i).setUnit("Gallon");
                        pantryData.get(i).setAmount(lToGal(pantryData.get(i).getAmount()));
                        myDB.updatePantryData(currentUser.getUsername(),pantryData.get(i).getName(),pantryData.get(i).getAmount(),pantryData.get(i).getUnit());
                        //Log.d("UPDATED DATABASE", pantryData.get(i).toString());
                    }
                }
            }
            databaseReference.child("users").child(currentUser.getUsername()).child("measureType").setValue(unit);
            currentUser.setMeasureType(unit);
            updated = true;
        }
        if(updated){
            updated = false;
            Toast.makeText(getActivity(), "Update Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.pfp:
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new ProfilePictureFragment()).commit();
                return; }
    }
    public static double kgsToLbs(double kgs){
        return kgs*2.205;
    }

    public static double lbsToKg(double lbs){
        return lbs/2.205;
    }

    public static double lToGal(double l){
        return l/3.785;
    }

    public static double galToL(double gal){
        return gal*3.785;
    }
}