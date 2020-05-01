package com.example.theweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
     private static String forecastDaysNum = "3";
     String city = "Philadelphia, PA";

     private RadioButton unit;
     private Spinner verbosity;
     private EditText lowTemp;
     private EditText midTemp;
     private EditText highTemp;
     private Button save;
     private  ImageButton settings_b;

     public static final String SHARED_PREFS = "";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.forecast_activity);
          unit = (RadioButton) findViewById(R.id.unit);
          verbosity = (Spinner) findViewById(R.id.verbosity);
          lowTemp = (EditText) findViewById(R.id.lowTemp);
          midTemp = (EditText) findViewById(R.id.midTemp);
          highTemp = (EditText) findViewById(R.id.highTemp);
          save = (Button) findViewById(R.id.save);
          settings_b = (ImageButton) findViewById(R.id.Settings_button);

          settings_b.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Intent intent = new Intent (MainActivity.this, SettingsActivity.class);
               }
          });



     }
}