package com.example.theweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText Emailaddress;
    private EditText Password;
    private Button bt_login;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    /*    mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
          *//*  public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(mAuth != null) {

                }
            }*//*
        */
        Emailaddress = (EditText) findViewById(R.id.Email_Address);
        Password = (EditText) findViewById(R.id.Password);
        bt_login = (Button) findViewById(R.id.Login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Emailaddress.getText().toString();
                String password = Password.getText().toString();

                if (email.isEmpty()) {
                    Emailaddress.setError("please enter email");
                    Emailaddress.requestFocus();
                } else if (password.isEmpty()) {
                    Password.setError("please enter password");
                    Password.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, ForecastActivity.class));
                            }
                        }

                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error has occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}















