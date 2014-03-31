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
        return 30;
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
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.fragment_box, null);
        }
        
    	ImageView  imageView = (ImageView)convertView.findViewById(R.id.image_box);
        
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
        
        convertView.setLayoutParams(new GridView.LayoutParams(Measuredwidth/div, Measuredwidth/div));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setPadding(10, 5, 10, 5);
    
        imageView.setImageResource(mThumbIds[i%4]);
        return convertView;
    }
    
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_open_box_eight, R.drawable.ic_open_box_four,
            R.drawable.ic_open_box_seven, R.drawable.ic_close_box
    };
    
}