package com.example.mehta.accelerometerin;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;


    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters params = mCamera.getParameters();
            params.setPreviewSize(1280,720);
            params.setPictureSize(1280,720);
            mCamera.setParameters(params);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
        
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Camera mCamera;
    private CameraPreview mPreview;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    int i = 1;
    int l = 10;
    private float xg, yg, zg, xa, ya, za;
    private float xang, yang, zang;
    private float zacc;
    private TransferUtility transferUtility;
    private ArrayList<HashMap<String, Object>> transferRecordMaps;
    private SimpleAdapter simpleAdapter;

    private static File msa;
    private float yacc;
    private int greset;
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {

        }
        return c;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transferUtility = Util.getTransferUtility(this);

        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        greset = 0;
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        senSensorManager.registerListener(this,senAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this,senGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp"  );
        File[] files = mediaStorageDir.listFiles();
        try {
            msa = new File(String.valueOf(files[1]));
        }
        catch (NullPointerException e)
        {
            msa = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyCameraApp/" + String.valueOf(0));
        }
        if (! msa.exists()) {
            if (!msa.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory111");

            }
        }


    }

    private void initData() {
        transferRecordMaps.clear();
        // Uses TransferUtility to get all previous download records.
        observers = transferUtility.getTransfersWithType(TransferType.DOWNLOAD);
        TransferListener listener = new UploadListener();
        for (TransferObserver observer : observers) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Util.fillMap(map, observer, false);
            transferRecordMaps.add(map);

            // Sets listeners to in progress transfers
            if (TransferState.WAITING.equals(observer.getState())
                    || TransferState.WAITING_FOR_NETWORK.equals(observer.getState())
                    || TransferState.IN_PROGRESS.equals(observer.getState())) {
                observer.setTransferListener(listener);
            }
        }
        simpleAdapter.notifyDataSetChanged();
    }
    public void beginUpload(String filePath) {
        if (filePath == null) {
            Log.e("", "Could not find the filepath of the selected file aws");
            return;
        }
        Log.e("", "found the filepath of the selected file aws");
        Log.e("", filePath+"aws");
        File file = new File(filePath);
            TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, file.getName(),
                    file);
        observer.setTransferListener (new UploadListener());



    }

        /*
         * Note that usually we set the transfer listener after initializing the
         * transfer. However it isn't required in this sample app. The flow is
         * click upload button -> start an activity for image selection
         * startActivityForResult -> onActivityResult -> beginUpload -> onResume
         * -> set listeners to in progress transfers.
         */
        // observer.setTransferListener(new UploadListener());

    private List<TransferObserver> observers;


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), "MyCameraApp"  );
    File[] files = mediaStorageDir.listFiles();
    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp"  );
        File[] files = mediaStorageDir.listFiles();
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory111");
                return null;
            }
        }
        Log.d("MyCam","MyCameraApp/"+String.valueOf(files.length));

      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(msa.getPath() + File.separator +
                    "img_"+ timeStamp + ".jpg");
            Log.e("",msa.getPath() + File.separator +
                    "img_"+ timeStamp + ".jpg" + " aws");

        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.startPreview();
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: " );
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                beginUpload(msa + File.separator +
                        "img_"+ timeStamp + ".jpg");
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };



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
                Camera.Parameters params = mCamera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                params.setJpegQuality(100);
                params.setRotation(90);
                mCamera.setParameters(params);
                mCamera.takePicture(null,null,mPicture);
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


}

 class UploadListener implements TransferListener {

    // Simply updates the UI list when notified.
    @Override
    public void onError(int id, Exception e) {
        Log.e(TAG, "Error during upload: " + id, e);

    }

    @Override
    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
        Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                id, bytesTotal, bytesCurrent));

    }

    @Override
    public void onStateChanged(int id, TransferState newState) {
        Log.d(TAG, "onStateChanged: " + id + ", " + newState);

    }
}
