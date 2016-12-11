package com.example.suyux.vrt3_verision01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main_menu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_);

    }

    public void onTakeNewModel(View view) {
        Intent getNameScreenIntent1 = new Intent(this, mainActivity.class);

        final int result = 1;
        getNameScreenIntent1.putExtra("calling3dActivity","Main_menu_Activity");

        startActivityForResult(getNameScreenIntent1,result);

    }

    public void onViewPicture(View view) {
        Intent getNameScreenIntent = new Intent(this, Gallery_Select.class);

        final int result = 1;
        getNameScreenIntent.putExtra("callingActivity","Main_menu_Activity");
        startActivityForResult(getNameScreenIntent,result);
    }

    public void onView3dModel(View view) {
    }
}
