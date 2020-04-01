package com.example.theweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";
    String city = "Philadelphia, PA";

    private RadioButton unit;
    private Spinner verbosity;
    private EditText lowTemp;
    private EditText midTemp;
    private EditText highTemp;
    private Button save;

    public static final String SHARED_PREFS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.forecast);

        unit = (RadioButton) findViewById(R.id.unit);
        verbosity = (Spinner) findViewById(R.id.verbosity);
        lowTemp = (EditText) findViewById(R.id.lowTemp);
        midTemp = (EditText) findViewById(R.id.midTemp);
        highTemp = (EditText) findViewById(R.id.highTemp);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData();
            }
        });
    }
    public void saveData(){

    }
}