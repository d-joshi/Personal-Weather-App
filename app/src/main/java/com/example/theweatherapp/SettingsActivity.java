package com.example.theweatherapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner verbosity = (Spinner) findViewById(R.id.verbosity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.verbosity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        verbosity.setAdapter(adapter);

    }
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}