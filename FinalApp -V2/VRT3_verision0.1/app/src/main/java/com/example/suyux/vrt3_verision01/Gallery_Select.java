package com.example.suyux.vrt3_verision01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Gallery_Select extends AppCompatActivity {

    ListView listView;
    String[] listItemValue = new String[]{"Project 0"};
    int i;
    ArrayList<String> ps = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery__select);
        File md = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp"  );
        Log.d("MyCameraGallery",String.valueOf(md.listFiles()));
        File[] files = md.listFiles();
        try {
            String[] projectList = new String[files.length];

            int j = files.length;

            for (i = 0; i < j; i++) {
                projectList[i] = String.valueOf(md) + "/" + String.valueOf(i);
                Log.d("MyCameraGallery", projectList[i]);
                Log.d("MyCameraGalleryfiles", String.valueOf(files[i]));
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    android.R.layout.activity_list_item, android.R.id.text1, projectList);

            listView = (ListView) findViewById(R.id.gallery_list_view);
            listView.setAdapter(adapter);
//        listView  = (ListView) findViewById(R.id.gallery_list_view);

            //      ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, ps);
            //    listView.setAdapter(madapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Gallery_Select.this, Gallery_Screen.class);
                    intent.putExtra("GalleryScreen", listView.getItemAtPosition(position).toString());
                    startActivity(intent);
                }

            });
        }
        catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(),"No Files. Please make new",Toast.LENGTH_LONG).show();
        }

        Intent backactivityThatCalled = getIntent();
        String previousActivity = backactivityThatCalled.getExtras().getString("callingActivity");

    }

    public void onBackFromGallary(View view) {
        onBackPressed();
    }
}
