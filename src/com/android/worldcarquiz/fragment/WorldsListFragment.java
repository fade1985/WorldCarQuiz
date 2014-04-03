package com.android.worldcarquiz.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.WorldPagerActivity;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.provider.WorldAdapter;

public class WorldsListFragment extends ListFragment {
	//Array de mundos
	private ArrayList<World> mWorlds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mWorlds = WorldCarQuizLab.get(getActivity()).getWorlds();

		WorldAdapter adapter = new WorldAdapter(getActivity(), mWorlds);
		setListAdapter(adapter);		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ListView listView = getListView();
		listView.setDividerHeight(0);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(getActivity(), WorldPagerActivity.class);
		i.putExtra(SubWorldFragment.EXTRA_NUM_WORLD, position);
		startActivity(i);		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater) {
		super.onCreateOptionsMenu(menu1, menuinflater);
		menuinflater.inflate(R.menu.fragment_subworld, menu1);
	}
}
