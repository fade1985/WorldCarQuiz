package com.android.worldcarquiz.fragment;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.World;

public class WorldsListFragment extends ListFragment {
	private ArrayList<World> mWorlds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mWorlds = new ArrayList<World>();
		for (int i = 0; i<10; i++) {
			mWorlds.add(new World(i));
		}
		
		WorldAdapter adapter = new WorldAdapter(mWorlds);
		setListAdapter(adapter);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ListView lv = getListView();
		lv.setDividerHeight(20);
		super.onActivityCreated(savedInstanceState);
	}
	
	
	private class WorldAdapter extends ArrayAdapter<World>{
		
		private WorldAdapter(ArrayList<World> worlds){
			super(getActivity(), 0, worlds);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_worlds_fragment, null);
			}
			
			int c = Color.BLUE;
			c = c - (25 * position);	
			
			ImageView imageWorld = (ImageView)convertView.findViewById(R.id.image_list_worlds);
			imageWorld.setBackgroundColor(c);
			
			TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
			worldTitle.setText("World" + String.valueOf(position));
		
			return convertView;
			
		}
	}
}
