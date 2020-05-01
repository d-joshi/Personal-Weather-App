package com.example.theweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    EditText Name, Email, Password;
    Button register;
    TextView bt_login;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name =  findViewById(R.id.enter_name);
        Email = findViewById(R.id.enter_email);
        Password = findViewById(R.id.enter_password);
        register = findViewById(R.id.bt_register);
        bt_login = findViewById(R.id.tv_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ForecastActivity.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Password.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    Password.setError("Password must be > 6 characters");
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User is created", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error has Occured" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
}
