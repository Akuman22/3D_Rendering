package com.example.suyux.vrt3_verision01;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by suyux on 2016/11/27.
 */

public class customSwip extends PagerAdapter {

    File md = new File(Gallery_Screen.newlocation );
    private File[] files = md.listFiles();
    private Context ctx;
    private LayoutInflater layoutInflater;


    public customSwip(Context c) {
        ctx = c;
    }

    @Override
    public int getCount() {
        return (int) md.length();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.activity_custom_swip, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeFile(files[position].getAbsolutePath()));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }
            //imageView.setImageBitmap(imageNicer.decodeSampledBitmapFromResource(context));
        container.addView(itemView);

        return itemView;

    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == object);
    }


}
