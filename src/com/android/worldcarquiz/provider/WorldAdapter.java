package com.android.worldcarquiz.provider;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class WorldAdapter extends ArrayAdapter<World>{
    private ArrayList<World> mWorlds;
    
	public WorldAdapter(Context context, ArrayList<World> worlds){
		super(context, 0, worlds);
		mWorlds = worlds;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(mWorlds.get(position).getSubWorlds() != null){
			//convertView = getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE ).inflate(R.layout.fragment_list_worlds, null);
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.fragment_list_worlds, null);
			
			//Titulo de los puntos en el mundo
			TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
			circlePoints.setText("Falta" + " Points won!");
			circlePoints.setTextSize(14);
			circlePoints.setPadding(0, 10, 0, 0);
			
			//Numero de acertados dentro del mundo
			TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
			worldTitle.setText(mWorlds.get(position).getUnlockedQuestions() + " Out of " + WorldCarQuizLab.QUESTIONS_TO_UNLOCK + " Cars");
			
			//Porcentaje del mundo
			TextView percentWorld = (TextView)convertView.findViewById(R.id.percent_list_worlds);
			percentWorld.setText((mWorlds.get(position).getUnlockedQuestions()*100)/WorldCarQuizLab.QUESTIONS_TO_UNLOCK + "%");
		}
		else {
			
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.fragment_list_worlds_blocked, null);
			
			//Titulo de los puntos en el mundo
			TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
			//int carsLeft = mWorlds.get(position).getQuestionsToUnlock();
			//circlePoints.setText(carsLeft + " cars left\nto unlock");
			circlePoints.setTextSize(14);
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
