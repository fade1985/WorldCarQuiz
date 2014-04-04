package com.android.worldcarquiz.provider;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;
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
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.fragment_list_worlds, null);
			
			//Puntos en el mundo
			TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
			int worldPoints = mWorlds.get(position).getWorldScore(); 
			circlePoints.setText(worldPoints + " Points won");
			circlePoints.setTextSize(12);
			
			//Numero de acertados dentro del mundo
			TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
			int answeredQuestions = mWorlds.get(position).getAnsweredQuestions();
			int totalQuestions = SubWorld.NUM_QUESTIONS * mWorlds.get(position).getSubWorlds().size();
			worldTitle.setText(answeredQuestions + " Out of " + totalQuestions + " Cars");
			
			
			//Porcentaje del mundo
			TextView percentWorld = (TextView)convertView.findViewById(R.id.percent_list_worlds);
			percentWorld.setText((mWorlds.get(position).getAnsweredQuestions()*100)/totalQuestions + "%");
			ProgressBar worldBar = (ProgressBar)convertView.findViewById(R.id.progress_bar_worlds);
			worldBar.setProgress(answeredQuestions);
		}
		else {
			
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.fragment_list_worlds_blocked, null);
			
			//Titulo de los puntos en el mundo
			TextView circlePoints = (TextView)convertView.findViewById(R.id.points_list_worlds);
			//int carsLeft = mWorlds.get(position).getQuestionsToUnlock();
			circlePoints.setText("12" + " cars left\nto unlock");
			circlePoints.setTextSize(14);
			
			//Numero de acertados dentro del mundo
			TextView worldTitle = (TextView)convertView.findViewById(R.id.text_list_worlds);
			worldTitle.setText("0 Out of 40 Cars");
			
			//Porcentaje del mundo
			TextView percentWorld = (TextView)convertView.findViewById(R.id.percent_list_worlds);
			percentWorld.setText("0%");
		}
		
		//Titulo del mundo
		TextView circleWorld = (TextView)convertView.findViewById(R.id.circle_list_worlds);
		circleWorld.setText("90's Cars Garage");
		circleWorld.setTextSize(22);
	
		return convertView;
		
	}
}
