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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignup;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;

    public static final String LAT_KEY = "latitude";
    public static final String LON_KEY = "longitude";
    public static final String COUNTRY_KEY = "country";
    public static final String LOCALITY_KET = "locality";
    public static final String CITY_KEY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Map<String, String> locationData = new HashMap<String, String>();

        locationData.put(LAT_KEY, "");
        locationData.put(LON_KEY, "");
        locationData.put(COUNTRY_KEY,"");
        locationData.put(LOCALITY_KET, "");
        locationData.put(CITY_KEY, "");


        emailId = findViewById(R.id.enter_email);
        password = findViewById(R.id.enter_password);
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnSignup = findViewById(R.id.registerbutton);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();

                } else if (TextUtils.isEmpty(pwd)) {
                    password.setError("Please enter your password");
                    return;
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                String uid = mFirebaseAuth.getCurrentUser().getUid();
                                db.collection("users").document(uid).set(locationData);
                                startActivity(new Intent(RegisterActivity.this, ForecastActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}



