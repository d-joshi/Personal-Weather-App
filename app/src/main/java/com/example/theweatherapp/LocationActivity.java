package com.example.theweatherapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LocationActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";
    String city = "Philadelphia, PA";

    SharedPreferences sharedPreferences;

    Button back;
    Button currlocation;
    Button save;
    TextView textView1, textView2, textView3, textView4, textView5;
    EditText enterLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static final String LAT_KEY = "latitude";
    public static final String LON_KEY = "longitude";
    public static final String COUNTRY_KEY = "country";
    public static final String LOCALITY_KET = "locality";
    public static final String CITY_KEY = "city";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        currlocation = findViewById(R.id.currentlocation);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        enterLocation = findViewById(R.id.enterLocation);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        currlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterLocation.getText().toString().isEmpty()){
                    Toast.makeText(LocationActivity.this, "Enter a location", Toast.LENGTH_SHORT).show();
                }else{
                    getLocationFromText(enterLocation.getText().toString());
                    Intent intent = new Intent(LocationActivity.this, ForecastActivity.class);
                    startActivity(intent);
                    Toast.makeText(LocationActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, ForecastActivity.class);
                startActivity(intent);
                Toast.makeText(LocationActivity.this, "Back to Forecast", Toast.LENGTH_SHORT).show();

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
                            Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            String latitude = Double.toString(addresses.get(0).getLatitude());
                            String longitude = Double.toString(addresses.get(0).getLongitude());
                            String country = addresses.get(0).getCountryCode().toLowerCase();
                            String locality = addresses.get(0).getAdminArea().toLowerCase();
                            String city = addresses.get(0).getLocality().toLowerCase();

                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LocationActivity.this);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("city", city.toLowerCase());
                            editor.commit();

                            Map<String, String> locationData = new HashMap<String, String>();
                            locationData.put(LAT_KEY, latitude);
                            locationData.put(LON_KEY, longitude);
                            locationData.put(COUNTRY_KEY, country);
                            locationData.put(LOCALITY_KET, locality);
                            locationData.put(CITY_KEY, city);

                            String uid = user.getUid();
                            Log.d("uid", uid);
                            db.collection("users").document(uid).set(locationData);

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

    private void getLocationFromText(String location){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LocationActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("city", location.toLowerCase());
        editor.commit();

//        try{
//            Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocationName(location, 1);
//
//            String latitude = Double.toString(addresses.get(0).getLatitude());
//            String longitude = Double.toString(addresses.get(0).getLongitude());
//            String country = addresses.get(0).getCountryCode().toLowerCase();
//            String locality = addresses.get(0).getAdminArea().toLowerCase();
//            String city = addresses.get(0).getLocality().toLowerCase();
//
//            Map<String, String> locationData = new HashMap<String, String>();
//            locationData.put(LAT_KEY, latitude);
//            locationData.put(LON_KEY, longitude);
//            locationData.put(COUNTRY_KEY, country);
//            locationData.put(LOCALITY_KET, locality);
//            locationData.put(CITY_KEY, city);
//
//            String uid = user.getUid();
//            Log.d("uid", uid);
//            db.collection("users").document(uid).set(locationData);
//
//            textView1.setText(Html.fromHtml("<font color ='#6200EE'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude())); //get latitude
//            textView2.setText(Html.fromHtml("<font color ='#6200EE'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude())); //get longitude
//            textView3.setText(Html.fromHtml("<font color ='#6200EE'><b>Country Name :</b><br></font>" + addresses.get(0).getCountryName())); //get country name
//            textView4.setText(Html.fromHtml("<font color ='#6200EE'><b>Locality :</b><br></font>" + addresses.get(0).getLocality())); //get Locality
//            textView5.setText(Html.fromHtml("<font color ='#6200EE'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0))); //get Locality
//
//        }catch(Exception e){
//            Toast.makeText(LocationActivity.this, "Enter a valid location", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
    }
}
