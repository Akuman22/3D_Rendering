package com.example.suyux.vrt3_verision01;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by suyux on 2016/11/27.
 */

public class Gallery_Screen extends Activity{
    ViewPager viewPager;
    customSwip customSwip;
    public static File ms;
    public static String newlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        Bundle extras = getIntent().getExtras();
        newlocation = extras.getString("GalleryScreen");
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        /*
        ms = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File[] files = ms.listFiles();
        */
        customSwip=new customSwip(this);
        viewPager.setAdapter(customSwip);
        /*Intent backactivityThatCalled = getIntent();
        String previousActivity = backactivityThatCalled.getExtras().getString("callingActivity");
        */
    }

    public void onBackToGallarySelect(View view) {
        onBackPressed();
    }
}
