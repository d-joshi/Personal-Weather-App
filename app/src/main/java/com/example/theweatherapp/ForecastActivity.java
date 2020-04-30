package com.example.theweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class ForecastActivity extends AppCompatActivity {
    private static String forecastDaysNum = "3";

    String API = "2e623cf734abdaf0dccd465fdbdd49c2";

    String LOC = "";

    String UNITS;

    TextView tempTxt;
    TextView highTxt;
    TextView lowTxt;
    TextView conditionTxt;
    TextView humidityTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        UNITS = "metric";

        setContentView(R.layout.forecast);

        tempTxt = findViewById(R.id.temp);
        highTxt = findViewById(R.id.highTemp);
        lowTxt = findViewById(R.id.lowTemp);
        conditionTxt = findViewById(R.id.condition);
        humidityTxt = findViewById(R.id.humidity);

        new WeatherTask().execute();
    }

    class WeatherTask extends AsyncTask<String, Void, String> {
        /*@Override
        protected void onPreExcecute() {
            super.onPreExecute();


        }*/

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?" + LOC + "&units=" + UNITS + "&appid=" + API);
            Log.d("response", response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject wind = jsonObject.getJSONObject("wind");

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
            }
            catch (JSONException e) {
                tempTxt.setText("err");
            }
        }
    }
}

//created with help from this tutorial: https://www.survivingwithandroid.com/android-openweathermap-app-weather-app/