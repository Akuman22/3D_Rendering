package com.example.suyux.vrt3_verision01;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

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
        File md = new File(newlocation );
        final File[] files = md.listFiles();
        final ImageView imageView = (ImageView) findViewById(R.id.swip_image_view);
        SeekBar s = (SeekBar) findViewById(R.id.seekBar);
        s.setMax(0);
        s.setMax(files.length-1);
        imageView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(files[0])));


        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                setProgress(progressChanged);
                imageView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(files[progressChanged])));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }});





        /*
        ms = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        File[] files = ms.listFiles();
        */

        /*Intent backactivityThatCalled = getIntent();
        String previousActivity = backactivityThatCalled.getExtras().getString("callingActivity");
        */
    }

    public void onBackToGallarySelect(View view) {
        onBackPressed();
    }
}
