package com.example.theweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ForecastActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";

    SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    String userID = "qxGBkwWEGNEjTKM0fgp1";

    String API = "2e623cf734abdaf0dccd465fdbdd49c2";

    String LOC = "";

    String UNITS;

    TextView tempTxt;
    TextView highTxt;
    TextView lowTxt;
    TextView conditionTxt;
    TextView humidityTxt;
    TextView windTxt;

    ArrayList<ArrayList<TextView>> hourlyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //String latitude = users.document(userID)

        if(!getString(R.string.latitude).isEmpty() && !getString(R.string.longitude).isEmpty()){
            LOC = "lat=" + getString(R.string.latitude) + "&lon=" +getString(R.string.longitude);
        }else if(!getString(R.string.city).isEmpty()){
            LOC += "q=" + getString(R.string.city);
            if(!getString(R.string.state).isEmpty()){
                LOC += "," + getString(R.string.state);
                if(!getString(R.string.country).isEmpty()){
                    LOC += "," + getString(R.string.country);
                }
            }
        }else{
            LOC = getString(R.string.cityCode);
        }

        UNITS = sharedPreferences.getString("units", "metric");

        setContentView(R.layout.activity_forecast);

        tempTxt = findViewById(R.id.temp);
        highTxt = findViewById(R.id.highTemp);
        lowTxt = findViewById(R.id.lowTemp);
        conditionTxt = findViewById(R.id.condition);
        humidityTxt = findViewById(R.id.humidity);
        windTxt = findViewById(R.id.wind);



        new WeatherTask().execute();
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