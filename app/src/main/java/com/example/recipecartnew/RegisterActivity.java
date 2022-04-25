package com.example.recipecartnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextWatcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pantry-ae39f-default-rtdb.firebaseio.com/");
    private User currentUser = User.getInstance();
    private FirebaseAuth mAuth;
    private Button btnRegister;
    private TextView textLogin;
    private EditText name, username, confirmPass, password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.register_name);
        username = (EditText) findViewById(R.id.register_user);
        password = (EditText) findViewById(R.id.register_password);
        confirmPass = (EditText) findViewById(R.id.register_confirm);
        email = (EditText) findViewById(R.id.register_email);
        textLogin = (TextView) findViewById(R.id.text_login);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

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

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void register(){
        String user = username.getText().toString().trim();
        String person = name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();
        String e = email.getText().toString().trim();
        String end = "";
        if(e.isEmpty()){
            email.setError("Email can not be empty");
        }
        else {
            end = e.substring(e.length() - 3, e.length());
        }
        if(person.isEmpty()){
            name.setError("Name cannot be empty");
        }
        if(user.isEmpty()){
            username.setError("Username cannot be empty");
        }
        if(pass.isEmpty()){
            password.setError("Password cannot be empty");
        }
        if(confirm.isEmpty()){
            confirmPass.setError("Confirmation cannot be empty");
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            email.setError("Invalid Email Address");
        }
        else if(!end.equals("com") && !end.equals("edu") && !end.equals("gov") && !end.equals("net")){
            email.setError("Invalid email address");
        }
        else if(pass.length() < 6){
            password.setError("Password must be at least 6 characters");
        }
        else if(!pass.equals(confirm)){
            Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(user)){
                        Toast.makeText(RegisterActivity.this, "Username already used", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseReference.child("users").child(user).child("email").setValue(e);
                        databaseReference.child("users").child(user).child("name").setValue(person);
                        databaseReference.child("users").child(user).child("password").setValue(pass);
                        databaseReference.child("users").child(user).child("measureType").setValue("Imperial");
                        Toast.makeText(RegisterActivity.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                        currentUser.setName(person);
                        currentUser.setUsername(user);
                        currentUser.setPassword(pass);
                        currentUser.setEmail(snapshot.child(user).child("email").getValue(String.class));
                        currentUser.setMeasureType("Imperial");
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /*mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
}