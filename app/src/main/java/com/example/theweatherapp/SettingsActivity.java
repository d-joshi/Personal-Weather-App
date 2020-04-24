package com.example.theweatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ImageButton settings_b;
    Button back;
    Button currlocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currlocation = findViewById(R.id.currentlocation);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        currlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        back = (Button) findViewById(R.id.button2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        settings_b = (ImageButton) findViewById(R.id.Settings_button);

        settings_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsFragments.class);
                settings_b = (ImageButton) findViewById(R.id.Settings_button);
                startActivity(intent);
            }
        });
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(SettingsActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        textView1.setText(Html.fromHtml("<font color ='#6200EE'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude())); //get latitude
                        textView2.setText(Html.fromHtml("<font color ='#6200EE'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude())); //get longitude
                        textView3.setText(Html.fromHtml("<font color ='#6200EE'><b>Country Name :</b><br></font>" + addresses.get(0).getCountryName())); //get country name
                        textView4.setText(Html.fromHtml("<font color ='#6200EE'><b>Locality :</b><br></font>" + addresses.get(0).getLocality())); //get Locality
                        textView5.setText(Html.fromHtml("<font color ='#6200EE'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0))); //get Locality

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}