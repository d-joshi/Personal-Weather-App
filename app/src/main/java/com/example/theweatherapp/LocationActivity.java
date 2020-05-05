package com.example.theweatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LocationActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";
    String city = "Philadelphia, PA";


    Button back;
    Button currlocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static final String LAT_KEY = "latitude";
    public static final String LON_KEY = "longitude";
    public static final String COUNTRY_KEY = "country";
    public static final String LOCALITY_KET = "locality";
    public static final String CITY_KEY = "city";

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
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);

        // Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                    }
//                });
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

                            Map<String, String> locationData = new HashMap<String, String>();

                            locationData.put(LAT_KEY, Double.toString(addresses.get(0).getLatitude()));
                            locationData.put(LON_KEY, Double.toString(addresses.get(0).getLongitude()));
                            locationData.put(COUNTRY_KEY, addresses.get(0).getCountryName());
                            locationData.put(LOCALITY_KET, addresses.get(0).getLocality());
                            locationData.put(CITY_KEY, addresses.get(0).getAddressLine(0));

                            //db.collection("users/" + user.getUid()).add(locationData);

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
