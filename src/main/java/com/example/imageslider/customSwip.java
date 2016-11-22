package com.example.imageslider;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by apple on 11/21/16.
 */

public class customSwip extends PagerAdapter {
    private int [] imageResources = {R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5};
    private Context cxt;
    private LayoutInflater layoutInflater;

    public customSwip(Context c) {
        cxt =c;
    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.activity_custom_swip,container,false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);
        TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        imageView.setImageResource(imageResources[position]);
        textView.setText("image counter "+position);
        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}

