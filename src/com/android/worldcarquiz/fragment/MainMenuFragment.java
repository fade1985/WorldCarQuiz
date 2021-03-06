package com.android.worldcarquiz.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.GarageActivity;
import com.android.worldcarquiz.activity.WorldsListActivity;
import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class MainMenuFragment extends Fragment {
	
	//Declaracion de botones
	private Button mPlayButton;
	private Button mWorldsButton;
	private Button mTutorialButton;
	private ImageButton mGarageButton;
	//Tipografia
	private Typeface RobotoThin;
	private Typeface RobotoRegular;
	//vibración
	private Vibrator vibrator;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		//WorldCarQuizLab worldCarQuizLab = WorldCarQuizLab.get(getActivity());
		vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_menu, null);
		
		//Cargamos la fuente de los textos
		TextView mWorldQuizText = (TextView) v.findViewById(R.id.world_quiz);
		TextView mCarsText = (TextView) v.findViewById(R.id.cars);
		RobotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		RobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
		mWorldQuizText.setTypeface(RobotoThin); 
		mCarsText.setTypeface(RobotoRegular);
		
		//Listener del boton Jugar
		mPlayButton = (Button)v.findViewById(R.id.play_main_button);
		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/***TEST: BORRAR BBDD***/
				getActivity().deleteDatabase(WorldQuizDatabaseHelper.DB_NAME);
			}
		});
		
		//Listener del boton Mundos
		mWorldsButton = (Button)v.findViewById(R.id.worlds_main_button);
		mWorldsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(getActivity(), WorldsListActivity.class);
				startActivity(i);
				vibrator.vibrate(20);
			}
		});
		
		//Listener del boton Garage
				mGarageButton = (ImageButton)v.findViewById(R.id.stamps_main_circle_button);
				mGarageButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						Intent i = new Intent(getActivity(), GarageActivity.class);
						startActivity(i);
						vibrator.vibrate(20);
					}
				});
		
		mTutorialButton = (Button)v.findViewById(R.id.tutorial_main_button);
		
		//Asignacion de la fuente a los bototnes
		mPlayButton.setTypeface(RobotoRegular);
		mWorldsButton.setTypeface(RobotoRegular);
		mTutorialButton.setTypeface(RobotoRegular);
		
		return v;
	}	
}
