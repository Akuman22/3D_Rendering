package com.example.mehta.accelerometerin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static java.lang.Math.sqrt;







class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;




    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }




    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.


        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Camera mCamera;
    private CameraPreview mPreview;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    TextView tv1, tv2, tv3, tv4, tv5;
    int j = 0;
    int i = 1;
    int l = 10;
    int m = 0;
    private float xg, yg, zg, xa, ya, za;
    private float x1, y1;
    float x2, y2;
    private float xang, yang, zang;
    private float zacc;
    private float xacc;
    private float yacc;
    private int greset;
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        greset = 0;
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        senSensorManager.registerListener(this,senAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this,senGyroscope,SensorManager.SENSOR_DELAY_NORMAL);


    }





    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);

    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this,senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this,senGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;


        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            greset ++;
            zg += sensorEvent.values[1];
            xg +=sensorEvent.values[0];
            yg +=sensorEvent.values[2];
            if(zg>30)
            {
                zg=0;
            }
            if(xg>30)
            {
                xg=0;
            }
            if(yg>30)
            {
                yg=0;
            }
            xang = xg*12;
            yang = yg*12;
            zang = zg*12;
            if(zang>=l) {
                Toast.makeText(getApplicationContext(), "click="+i, Toast.LENGTH_LONG).show();
                l += 10;
                i++;}




            if (greset >10)
            {
                xg +=0.1;

                greset = 0;
            }

        }

        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            xa = sensorEvent.values[0];
            za = sensorEvent.values[1];
            ya = sensorEvent.values[2];



            zacc = (float)(za - (((90 -  xang)/ 90) * 9.8));


        }



        if (l > 360) {
            l = 10;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public int returnvalue(){
        return(i);
    }

}



