package com.android.worldcarquiz.provider;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private int mNumWorld;
    private int mNumSubWorld;

    public QuestionAdapter(Context context, int numWorld, int numSubWorld)
    {
        mContext = context;
        mNumWorld = numWorld;
        mNumSubWorld = numSubWorld;
    }

    @Override
    public int getCount() {
        return SubWorld.NUM_QUESTIONS;
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


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        
    	if(WorldCarQuizLab.get(mContext).questionLocked(mNumWorld, mNumSubWorld, i)) 
    		convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.fragment_box_blocked, null);
    	else if (WorldCarQuizLab.get(mContext).questionUnlocked(mNumWorld, mNumSubWorld, i))
    		convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.fragment_box_open, null);	
    	else
    		convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.fragment_box_solved, null);
        
        return convertView;
    }    
    
}