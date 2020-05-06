package com.example.theweatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ramotion.fluidslider.FluidSlider;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class fluidslider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fluidslider);

        final TextView textView = findViewById(R.id.textView);

        final int max = 100;
        final int min = 10;
        final int total = max - min;

        final FluidSlider slider = findViewById(R.id.fluidSlider);
        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                textView.setVisibility(View.INVISIBLE);
                return Unit.INSTANCE;
            }
        });

        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                textView.setVisibility(View.VISIBLE);
                return Unit.INSTANCE;
            }
        });



        slider.setPosition(0.3f);
        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));
    }
}