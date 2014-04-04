package com.android.worldcarquiz.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.WorldPagerActivity;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.provider.WorldAdapter;

public class WorldsListFragment extends ListFragment {
	//Array de mundos
	private ArrayList<World> mWorlds;
	//vibración
	private Vibrator vibrator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
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
		
		if (mWorlds.get(position).getSubWorlds() != null) {
			Intent i = new Intent(getActivity(), WorldPagerActivity.class);
			i.putExtra(SubWorldFragment.EXTRA_NUM_WORLD, position);
			vibrator.vibrate(20);
			startActivity(i);
		} else {
			Toast.makeText(getActivity(), (CharSequence)"Mundo bloqueado bitch!!", Toast.LENGTH_SHORT).show();
		}		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater) {
		super.onCreateOptionsMenu(menu1, menuinflater);
		menuinflater.inflate(R.menu.fragment_subworld, menu1);
	}
}
