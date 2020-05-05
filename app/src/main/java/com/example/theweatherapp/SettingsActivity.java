package com.example.theweatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SettingsActivity", "hello there");

        setContentView(R.layout.activity_settings);

        if(findViewById(R.id.frag_settings) != null){
            if(savedInstanceState != null){
                return;
            }

            getSupportFragmentManager().beginTransaction().add(R.id.frag_settings, new SettingsFragments()).commit();
        }
    }

}