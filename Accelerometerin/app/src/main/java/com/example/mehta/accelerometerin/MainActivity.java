package com.example.mehta.accelerometerin;

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
import android.widget.Toast;
import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    TextView tv1,tv2,tv3,tv4;
    int j=0;
    int i=1;
    int l=10;
    int m=0;
    private float xg,yg,zg,xa,ya,za;
    private float x1,y1;
    float x2,y2;
    private float xang,yang,zang;
    private float zacc;
    private float xacc;
    private float yacc;
    private  int greset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greset = 0;
        senSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
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
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);

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
                Toast.makeText(getApplicationContext(), "click" + i, Toast.LENGTH_LONG).show();
                l += 10;
            i++;}


            tv2.setText("x angle = " + xang);
            tv1.setText("y angle = " + yang);

                tv3.setText("z angle=" + zang);
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

            tv4.setText("zacc = " + zacc);

        }



            if (l > 360) {
                l = 10;
            }
        }





    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
