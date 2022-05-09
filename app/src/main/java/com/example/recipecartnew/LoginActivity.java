package com.example.recipecartnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pantry-ae39f-default-rtdb.firebaseio.com/");
    User currentUser = User.getInstance();
    DatabaseHelper myDB;
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private TextView textRegister;
    private EditText password,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.login_user);
        password = (EditText) findViewById(R.id.login_password);
        textRegister = (TextView) findViewById(R.id.text_register);
        myDB = new DatabaseHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    private void login(){
        final String user = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        //myDB.dropTables();
        if(user.isEmpty()){
            username.setError("Username can not be empty");
            if(pass.isEmpty()){
                password.setError("Password can not be empty");
            }
        }
        else if(pass.isEmpty()){
            password.setError("Password can not be empty");
        }
        else{


            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(user)) {
                        System.out.println("A");
                        final String getPass = snapshot.child(user).child("password").getValue(String.class);
                        if (getPass.equals(pass)) {
                            System.out.println("PASSA");
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            currentUser.setUsername(user);
                            currentUser.setPassword(pass);
                            System.out.println("PASSB");
                            currentUser.setName(snapshot.child(user).child("name").getValue(String.class));
                            currentUser.setEmail(snapshot.child(user).child("email").getValue(String.class));
                            currentUser.setMeasureType(snapshot.child(user).child("measureType").getValue(String.class));if(snapshot.child(user).hasChild("imageURI")) {
                                currentUser.setImageURL(snapshot.child(user).child("imageURI").getValue(String.class));
                            }
                            System.out.println("PASSC");
                            //myDB.dropTables();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            System.out.println("PASSD");
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println("B");
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            /*mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
}