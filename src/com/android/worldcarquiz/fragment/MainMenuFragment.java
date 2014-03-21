package com.android.worldcarquiz.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.WorldsListActivity;

public class MainMenuFragment extends Fragment {

	private Button mPlayButton;
	private Button mWorldsButton;
	private Button mTutorialButton;
	//Tipografia
	private Typeface RobotoThin;
	private Typeface RobotoRegular;
	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_menu, null);
		
		TextView mWorldQuizText = (TextView) v.findViewById(R.id.world_quiz);
		TextView mCarsText = (TextView) v.findViewById(R.id.cars);
		RobotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		RobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
		mWorldQuizText.setTypeface(RobotoThin); 
		mCarsText.setTypeface(RobotoRegular);
		
		mPlayButton = (Button)v.findViewById(R.id.play_main_button);
		mWorldsButton = (Button)v.findViewById(R.id.worlds_main_button);
		mWorldsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), WorldsListActivity.class);
				startActivity(i);
			}
		});
		
		mTutorialButton = (Button)v.findViewById(R.id.tutorial_main_button);
		
		mPlayButton.setTypeface(RobotoRegular);
		mWorldsButton.setTypeface(RobotoRegular);
		mTutorialButton.setTypeface(RobotoRegular);
		
		return v;
	}
	
}
