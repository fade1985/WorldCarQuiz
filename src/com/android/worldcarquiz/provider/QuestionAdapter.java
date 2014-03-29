package com.android.worldcarquiz.provider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.worldcarquiz.R;

public class QuestionAdapter extends BaseAdapter {
    private Context mContext;

    public QuestionAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @TargetApi(13)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            
            int Measuredwidth = 0;
            int Measuredheight = 0;
            Point size = new Point();
            WindowManager w = ((Activity)mContext).getWindowManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                w.getDefaultDisplay().getSize(size);

                Measuredwidth = size.x;
                Measuredheight = size.y;
            }
            else
            {
                Display d = w.getDefaultDisplay();
                Measuredwidth = d.getWidth();
                Measuredheight = d.getHeight();
            }
            
            String s = ((Activity)mContext).getResources().getString(R.string.div) ;
            int div = Integer.valueOf(s);
            
            imageView.setLayoutParams(new GridView.LayoutParams(Measuredwidth/div, Measuredwidth/div));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(50,50,50,50);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[i%4]);
        return imageView;
    }
    
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_open_box_one, R.drawable.ic_open_box_nine,
            R.drawable.ic_close_box, R.drawable.ic_half_closed_box
    };
    
}