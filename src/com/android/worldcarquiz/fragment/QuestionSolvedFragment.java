package com.android.worldcarquiz.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;
import com.android.worldcarquiz.activity.WorldsListActivity;
import com.android.worldcarquiz.data.Question;
import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class QuestionSolvedFragment extends Fragment {
	
	private String mCarBrand;
	private String mCarModel;
	
	private TextView mTextViewCar;
	private TextView mTextViewScore;
	
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	private int score;
	
	private Button mNextCar;
	private Button mPrevCar;
	private Button mGarage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_WORLD);
		mNumSubWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_SUBWORLD);
		mNumQuestion = getArguments().getInt(QuestionPhotoFragment.EXTRA_NUM_QUESTION);
		
		//Obtenemos el string que contiene la respuesta de la pregunta y la divimos en 2, marca y modelo.
		mCarBrand = WorldCarQuizLab.get(getActivity()).getQuestionBrand(mNumWorld, mNumSubWorld, mNumQuestion);
		mCarModel = WorldCarQuizLab.get(getActivity()).getQuestionModel(mNumWorld, mNumSubWorld, mNumQuestion);
		score =  WorldCarQuizLab.get(getActivity()).score(mNumWorld, mNumSubWorld, mNumQuestion);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Inflamos el layout del fragment con la respuesta correcta y los puntos.
		View v = inflater.inflate(R.layout.fragment_question_solved, null);
		mTextViewCar = (TextView) v.findViewById(R.id.car_title);
		mTextViewCar.setText(mCarBrand.toUpperCase() + "\n" + mCarModel.toUpperCase());
		mTextViewScore = (TextView) v.findViewById(R.id.points);
		mTextViewScore.setText("+" + score);
		
		//Listener de los botones
		//Listener del boton next car
		mNextCar = (Button)v.findViewById(R.id.solution_next_button);
		mNextCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	if(mNumQuestion <= SubWorld.NUM_QUESTIONS*mNumWorld*mNumSubWorld){
					Intent i = new Intent(getActivity(), QuestionActivity.class);
				   i.putExtra(SubWorldFragment.EXTRA_NUM_WORLD, mNumWorld);
				   i.putExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, mNumSubWorld);
				   i.putExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, mNumQuestion + 1);
			       startActivity(i);
				}
			//}
		});
		
		//Listener del boton prev car
		mPrevCar = (Button)v.findViewById(R.id.solution_prev_button);
		mPrevCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(mNumQuestion >= ){
					Intent i = new Intent(getActivity(), QuestionActivity.class);
				    i.putExtra(SubWorldFragment.EXTRA_NUM_WORLD, mNumWorld);
				    i.putExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, mNumSubWorld);
				    i.putExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, mNumQuestion -1 );
			        startActivity(i);
				}
			//}
		});
		
		
				
		return v;
	}
	
	//creamos metodo estatico que pasaremos como parametro
		public static Fragment newInstance(int numWorld, int numSubWorld, int numQuestion) {
			Bundle arg = new Bundle();
			arg.putInt(SubWorldFragment.EXTRA_NUM_WORLD, numWorld);
			arg.putInt(SubWorldFragment.EXTRA_NUM_SUBWORLD, numSubWorld);
			arg.putInt(QuestionPhotoFragment.EXTRA_NUM_QUESTION, numQuestion);
			QuestionSolvedFragment fragment = new QuestionSolvedFragment();
			fragment.setArguments(arg);
			
			return fragment;
		}
}