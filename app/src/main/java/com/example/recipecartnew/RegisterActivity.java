package com.example.recipecartnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnRegister;
    private TextView textLogin;
    private EditText password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = (Button) findViewById(R.id.register);
        password = (EditText) findViewById(R.id.register_password);
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
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if(user.isEmpty()){
            email.setError("Email can not be empty");
        }
        if(pass.isEmpty()){
            password.setError("Password can not be empty");
        }

        else if(pass.length() < 6){
            password.setError("Password must be at least 6 characters");
        }
        else{
            mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
            });
        }
    }
}