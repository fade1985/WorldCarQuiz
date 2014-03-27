package com.android.worldcarquiz.fragment;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class WorldsListFragment extends ListFragment {
	//Array de mundos
	private ArrayList<World> mWorlds;
	//Tipografia
	private Typeface RobotoThin;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mWorlds = WorldCarQuizLab.get(getActivity()).getWorlds();
		
		WorldAdapter adapter = new WorldAdapter(mWorlds);
		setListAdapter(adapter);		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ListView lv = getListView();
		lv.setDividerHeight(0);
		super.onActivityCreated(savedInstanceState);
	}
	
	
	private class WorldAdapter extends ArrayAdapter<World>{
		
		private WorldAdapter(ArrayList<World> worlds){
			super(getActivity(), 0, worlds);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(mWorlds.get(position).getSubWorlds() != null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_worlds, null);
				
				//Titulo de los puntos en el mundo
				TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
				circlePoints.setText("Falta" + " Points won!");
				circlePoints.setTextSize(10);
				circlePoints.setPadding(0, 10, 0, 0);
				
				//Numero de acertados dentro del mundo
				TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
				worldTitle.setText(mWorlds.get(position).getUnlockedQuestions() + " Out of " + WorldCarQuizLab.QUESTIONS_TO_UNLOCK + " Cars");
				
				//Porcentaje del mundo
				TextView percentWorld = (TextView)convertView.findViewById(R.id.percent_list_worlds);
				percentWorld.setText((mWorlds.get(position).getUnlockedQuestions()*100)/WorldCarQuizLab.QUESTIONS_TO_UNLOCK + "%");
			}
			else {
				
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_worlds_blocked, null);
				
				//Titulo de los puntos en el mundo
				TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
				int carsLeft = mWorlds.get(position).getQuestionsToUnlock();
				circlePoints.setText(carsLeft + " cars left\nto unlock");
				circlePoints.setTextSize(10);
				circlePoints.setPadding(0, 10, 0, 0);
				
				//Numero de acertados dentro del mundo
				TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
				worldTitle.setText("0 Out of 40 Cars");
				
				//Porcentaje del mundo
				TextView percentWorld = (TextView)convertView.findViewById(R.id.percent_list_worlds);
				percentWorld.setText("0%");
			}
			
			//Titulo del mundo
			TextView circleWorld = (TextView)convertView.findViewById(R.id.circle_list_worlds);
			circleWorld.setText("World " + String.valueOf(position+1));
			circleWorld.setTextSize(25);			
		
			return convertView;
			
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater) {
		super.onCreateOptionsMenu(menu1, menuinflater);
		menuinflater.inflate(R.menu.fragment_subworld, menu1);
	}
}
