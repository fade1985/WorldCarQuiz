package com.android.worldcarquiz.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.Question;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionSolvedFragment extends Fragment {
	
	private String mCarBrand;
	private String mCarModel;
	private ArrayList<World> mWorlds;
	
	private TextView mTextViewCar;
	private TextView mTextViewScore;
	
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	private int score;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_WORLD);
		mNumSubWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_SUBWORLD);
		mNumQuestion = getArguments().getInt(QuestionPhotoFragment.EXTRA_NUM_QUESTION);
		
		//Obtenemos el string que contiene la respuesta de la pregunta y la divimos en 2, marca y modelo.
		String answer = WorldCarQuizLab.get(getActivity()).getQuestionAnswer(mNumWorld, mNumSubWorld, mNumQuestion);
		String[] answerSplit = answer.split(" ");
		mCarBrand = answerSplit[0];
		mCarModel = answerSplit[1];	
		score = mWorlds.get(mNumWorld).getSubWorlds().get(mNumSubWorld)
				.getQuestions().get(mNumQuestion).getScore();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Inflamos el layout del fragment con la respuesta correcta y los puntos.
		View v = inflater.inflate(R.layout.fragment_question_solved, null);
		mTextViewCar = (TextView) v.findViewById(R.id.car_title);
		mTextViewCar.setText(mCarBrand.toUpperCase() + "\n" + mCarModel.toUpperCase());
		mTextViewScore = (TextView) v.findViewById(R.id.points_solved);
		mTextViewScore.setText("+" + score);
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