package com.example.imageslider;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    customSwip customSwip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        customSwip = new customSwip(this);
        viewPager.setAdapter(customSwip);

    }


}
//// TODO: 11/21/16 fade in fade out effect; slide bar; read image from sd card; other effects and layout  