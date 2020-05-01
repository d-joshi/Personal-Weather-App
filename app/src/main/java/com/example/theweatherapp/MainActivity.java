package com.example.theweatherapp;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
=======
>>>>>>> 006b2680acc38f4b86cf0bf6d41b24d3df0393e3
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
=======
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> 006b2680acc38f4b86cf0bf6d41b24d3df0393e3

import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
     private static String forecastDaysNum = "3";
     String city = "Philadelphia, PA";

     private RadioButton unit;
     private Spinner verbosity;
     private EditText lowTemp;
     private EditText midTemp;
     private EditText highTemp;
     private Button save;
<<<<<<< HEAD
    private Button page2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        unit = (RadioButton) findViewById(R.id.unit);
        verbosity = (Spinner) findViewById(R.id.verbosity);
        lowTemp = (EditText) findViewById(R.id.lowTemp);
        midTemp = (EditText) findViewById(R.id.midTemp);
        highTemp = (EditText) findViewById(R.id.highTemp);
        save = (Button) findViewById(R.id.save);

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                });
    }
}
=======
     private Button page2;
     FirebaseFirestore db = FirebaseFirestore.getInstance();

>>>>>>> 006b2680acc38f4b86cf0bf6d41b24d3df0393e3

