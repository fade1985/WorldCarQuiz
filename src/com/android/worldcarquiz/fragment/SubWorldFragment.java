package com.android.worldcarquiz.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;

public class SubWorldFragment extends Fragment {
	private ImageButton mButton;
	private int mPos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPos = getArguments().getInt("PRUEBA");		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_subworld, null);
		
		GridView gridView = (GridView) v.findViewById(R.id.gridview);
		gridView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		    {
		        // this 'mActivity' parameter is Activity object, you can send the current activity.
		        Intent i = new Intent(getActivity(), QuestionActivity.class);
		        startActivity(i);
		    }
		});
		gridView.setAdapter(new BoxAdapter(getActivity()));
		
		return v;
	}
	
	public static SubWorldFragment newInstance(int pos) {
		Bundle arg = new Bundle();
		arg.putInt("PRUEBA", pos);
		SubWorldFragment fragment = new SubWorldFragment();
		fragment.setArguments(arg);
		
		return fragment;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subworld, menu);
	}
	
	
	
	private class BoxAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;
        private Context mContext;

        public BoxAdapter(Context context)
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
                WindowManager w = getActivity().getWindowManager();

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
                
                String s = getActivity().getResources().getString(R.string.div) ;
                int div = Integer.valueOf(s);
                
                imageView.setLayoutParams(new GridView.LayoutParams(Measuredwidth/div, Measuredwidth/div));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(30, 30, 30, 30);
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
}
