package com.example.accelerometer;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

public class MainActivity1 extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    TextView tv1,tv2,tv3,tv4;
    int j=0;
    int k=0;
    int l=0;
    int m=0;
    float x,y,z;
    float x1,y1;
    float x2,y2;
    float x3,y3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        senSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int i;
            i = 0;


            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            x1=x;
            y1=y;

            x2 = x1-x;
            y2 = y1-y;

            x3 += x2/1000*1000*2;
            y3 += y3/1000*1000*2;


            tv1 = (TextView) findViewById(R.id.textView1);
            tv2 = (TextView) findViewById(R.id.textView2);
            tv3 = (TextView) findViewById(R.id.textView3);
            tv4 = (TextView) findViewById(R.id.textView4);

            tv1.setText("x=" + x);
            tv2.setText("y=" + y);
            tv3.setText("xpos=" + x3);
            tv4.setText("ypos=" + y3);

        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
