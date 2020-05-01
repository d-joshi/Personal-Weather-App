package com.example.theweatherapp;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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
     private Button page2;
     FirebaseFirestore db = FirebaseFirestore.getInstance();


}