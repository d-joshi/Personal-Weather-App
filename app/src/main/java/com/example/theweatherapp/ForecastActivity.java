package com.example.theweatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.androdocs.httprequest.HttpRequest;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;


public class ForecastActivity extends AppCompatActivity {
    //private static String forecastDaysNum = "3";

    //SharedPrefences
    private SharedPreferences sharedPreferences;

    //Firebase
    FirebaseFirestore db;
    FirebaseUser user;
    String uid;
    DocumentReference userDoc;

    //assets
    String API = "2e623cf734abdaf0dccd465fdbdd49c2";
    String latitude = "";
    String longitude = "";
    String city = "mountain view";
    String state = "";
    String country = "";

    String LOC = "";
    String UNITS;
    String MESSAGE_LENGTH;

    //Views
    TextView tempTxt;
    TextView highTxt;
    TextView lowTxt;
    TextView conditionTxt;
    TextView humidityTxt;
    TextView windTxt;
    TextView personalMessageTxt;

    //ArrayList<ArrayList<TextView>> hourlyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        city = sharedPreferences.getString("city", "mountain view");

        if(!city.isEmpty()){
            LOC += "q=" + city;
            if(!state.isEmpty()){
                LOC += "," + state;
                if(!country.isEmpty()){
                    LOC += "," + country;
                }
            }
        }else if(!latitude.isEmpty() && !longitude.isEmpty()) {
            LOC = "lat=" + latitude + "&lon=" + longitude;
        }else{
            Log.d("no location", "no location");
        }


        UNITS = sharedPreferences.getString("units", "metric");
        MESSAGE_LENGTH = sharedPreferences.getString("messageLength", "medium");

        setContentView(R.layout.activity_forecast);

        tempTxt = findViewById(R.id.temp);
        highTxt = findViewById(R.id.highTemp);
        lowTxt = findViewById(R.id.lowTemp);
        conditionTxt = findViewById(R.id.condition);
        humidityTxt = findViewById(R.id.humidity);
        windTxt = findViewById(R.id.wind);
        personalMessageTxt = findViewById(R.id.personalMessage);

        new WeatherTask().execute();
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    class WeatherTask extends AsyncTask<String, Void, String> {
        /*@Override
        protected void onPreExcecute() {
            super.onPreExecute();


        }*/

        @Override
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?" + LOC + "&units=" + UNITS + "&appid=" + API);
            Log.d("response", response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONObject now = jsonObject.getJSONArray("list").getJSONObject(0);
                JSONObject weather = now.getJSONArray("weather").getJSONObject(0);
                JSONObject main = now.getJSONObject("main");
                JSONObject wind = now.getJSONObject("wind");

                //get unit strings
                String[] units = new String[2];
                if(UNITS.equals("imperial")){
                    units[0] = "°F";
                    units[1] = "mph";
                }else{
                    units[0] = "°C";
                    units[1] = "kph";
                }

                //get temperature
                String temp = Float.toString(Math.round(Float.parseFloat(main.getString("temp")))).substring(0,2);
                String high = Float.toString(Math.round(Float.parseFloat(main.getString("temp_max")))).substring(0,2);
                String low = Float.toString(Math.round(Float.parseFloat(main.getString("temp_min")))).substring(0,2);

                //get weather condition
                String condition = weather.getString("description");

                //get humidity
                String humidity = main.getString("humidity");

                String windSpeed = wind.getString("speed");
                float deg = Float.parseFloat(wind.getString("deg"));
                int degInt = (int) Math.floor((deg + 11.75)/23.5);
                String direction = new String();
                switch(degInt){
                    case 0: direction = "N";
                        break;
                    case 1: direction = "NNE";
                        break;
                    case 2: direction = "NE";
                        break;
                    case 3: direction = "ENE";
                        break;
                    case 4: direction = "E";
                        break;
                    case 5: direction = "ESE";
                        break;
                    case 6: direction = "SE";
                        break;
                    case 7: direction = "SSE";
                        break;
                    case 8: direction = "S";
                        break;
                    case 9: direction = "SSW";
                        break;
                    case 10: direction = "SW";
                        break;
                    case 11: direction = "WSW";
                        break;
                    case 12: direction = "W";
                        break;
                    case 13: direction = "WNW";
                        break;
                    case 14: direction = "NW";
                        break;
                    case 15: direction = "NNW";
                        break;

                }

                tempTxt.setText(temp + units[0]);
                highTxt.setText("high: " + high + units[0]);
                lowTxt.setText("low: " + low + units[0]);
                conditionTxt.setText(condition);
                humidityTxt.setText(humidity + "% humidity");
                windTxt.setText("wind: " + windSpeed + " "  + units[1] + " " + direction);

                int length = 1;

                switch (sharedPreferences.getString("messageLength", "medium")){
                    case "short" : length = 0;
                        break;
                    case "medium" : length = 1;
                        break;
                    case "long" : length = 2;
                }

                double testTemp = Math.round(Float.parseFloat(main.getString("temp")));

                if(units[0].equals("°F")){
                    testTemp = (testTemp - 32) * 5 / 9;
                }

                String feelsLike = Float.toString(Math.round(Float.parseFloat(main.getString("feels_like")))).substring(0,2);

                String clothing;

                if(testTemp<5){
                    clothing = "put on a coat";
                }else if(testTemp<25){
                    clothing = "dress moderately";
                }else{
                    clothing = "try to keep cool";
                }

                String conditionText;
                String conditionReccomendation;

                switch (condition){
                    case "Thunderstorm" :
                        conditionText = "stormy";
                        conditionReccomendation = "carry an umbrella and be careful";
                        break;
                    case "Drizzle" :
                    case "Rain" :
                        conditionText = "rainy";
                        conditionReccomendation = "carry an umbrella";
                        break;
                    case "Snow" :
                        conditionText = "snowy";
                        conditionReccomendation = "watch your step";
                        break;
                    case "Extreme" :
                        conditionText = "extreme";
                        conditionReccomendation = "be careful out there";
                        break;
                    default:
                        conditionText = "normal";
                        conditionReccomendation = "have a nice day";
                        break;
                }

                String message = String.format(getResources().getStringArray(R.array.personal_message)[length], temp, units[0], feelsLike, clothing, conditionText, conditionReccomendation);

                personalMessageTxt.setText(message);

            }
            catch (JSONException e) {
                tempTxt.setText("err");
            }
        }

    }
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if( id == R.id.Action_settings) {
            Log.d("Action_settings", "action settings");
            Intent intent = new Intent(ForecastActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
            }
        if(id == R.id.logout) {
                Intent intent = new Intent(ForecastActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(ForecastActivity.this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
            }
        if(id == R.id.enter_location) {
            Intent intent = new Intent (ForecastActivity.this, LocationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

//created with help from this tutorial: https://www.survivingwithandroid.com/android-openweathermap-app-weather-app/