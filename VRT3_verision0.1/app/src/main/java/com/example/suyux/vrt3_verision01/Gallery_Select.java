package com.example.suyux.vrt3_verision01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Gallery_Select extends AppCompatActivity {

    ListView listView;
    String[] listItemValue = new String[]{"Project 0"};
    ArrayList<String> projectList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery__select);

        listView = (ListView) findViewById(R.id.gallery_list_view);

        /*
        projectList.add(mainActivity.msa.getAbsolutePath());
         */
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listItemValue);
        listView.setAdapter(madapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Gallery_Select.this, Gallery_Screen.class);
                intent.putExtra("GalleryScreen", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
        Intent backactivityThatCalled = getIntent();
        String previousActivity = backactivityThatCalled.getExtras().getString("callingActivity");

    }

    public void onBackFromGallary(View view) {
        onBackPressed();
    }
}
