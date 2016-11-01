package com.example.suyux.takephoto_without_interact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Camera camera; // camera object
    private TextView TimeLeft; // time left field
    File mediaFile;
    private static final String IMAGE_DIRECTORY_NAME = "Captured_Images";
    int numberOfCameras;
    private TextView cameras;
    Random rand = new Random();
    Handler handler = new Handler();

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeLeft = (TextView) findViewById(R.id.TimeLeft); // make time left object
        camera = Camera.open();
        numberOfCameras = Camera.getNumberOfCameras();
        cameras = (TextView) findViewById(R.id.camera);


        if (numberOfCameras == 0)
            cameras.setText("Camera Not Supported in this Mobile");
        else if (numberOfCameras == 2)
            cameras.setText("No Of Cameras:" + numberOfCameras + "\n Both Front & Back Camera Available");
        else
            cameras.setText("No Of Cameras:" + numberOfCameras + "\n Back Camera only Available");
        SurfaceView view = new SurfaceView(this);


        try {
            camera.setPreviewDisplay(view.getHolder()); // feed dummy surface to surface
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        new CountDownTimer(10000,1000){
            @Override
            public void onFinish() {
// count finished
                TimeLeft.setText("Picture Taken");
                camera.takePicture(null, null, null, jpegCallBack);
            }
            @Override
            public void onTick(long millisUntilFinished) {
// every time 1 second passes
                TimeLeft.setText("Seconds Left: "+millisUntilFinished/1000);
            }
        }.start();
        camera.startPreview();
    }

    Camera.PictureCallback jpegCallBack = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                }
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");
            try {
                Bitmap userImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                // set file out stream
                FileOutputStream out = new FileOutputStream(mediaFile);
                // set compress format quality and stream
                userImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
}