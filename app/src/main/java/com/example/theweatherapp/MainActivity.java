package com.example.theweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";
    String city = "Philadelphia, PA";

    public static final String SHARED_PREFS = "";

    String CITY = "philadelphia,pa,us";
    String API = "2e623cf734abdaf0dccd465fdbdd49c2";
    String UNITS = "metric";

    TextView tempTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.forecast);

        Log.d("onCreate", "onCreate");

        tempTxt = findViewById(R.id.temp);
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        /*@Override
        protected void onPreExcecute() {
            super.onPreExecute();


        }*/

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=" + UNITS + "&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                Log.d("onPostExecute", "onPostExecute");
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");

                String temp = main.getString("temp") + "°C";

                tempTxt.setText(temp);
            }
            catch (JSONException e) {
                Log.d("exception", "¯\\_(ツ)_/¯");
            }
        }
    }
}

//created with help from this tutorial: https://www.survivingwithandroid.com/android-openweathermap-app-weather-app/