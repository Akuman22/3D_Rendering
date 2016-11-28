package com.example.suyux.vrt3_verision01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by suyux on 2016/11/27.
 */

public class Gallery_Screen extends Activity{
    ViewPager viewPager;
    customSwip customSwip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        customSwip=new customSwip(this);
        viewPager.setAdapter(customSwip);
        Intent backactivityThatCalled = getIntent();
        String previousActivity = backactivityThatCalled.getExtras().getString("callingActivity");
    }

    public void onBackFromGallary(View view) {
        onBackPressed();
    }
}
