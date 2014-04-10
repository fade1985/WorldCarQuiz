package com.android.worldcarquiz.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.worldcarquiz.R;

public class QuestionAnswerFragment extends Fragment {
	
	private TableLayout mKeyBoard;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_question_answer_write, null);
		mKeyBoard = (TableLayout) v.findViewById(R.id.table);
		TableRow tr = null;
		
		for (int i = 0; i < 4; i++) {
			switch (i) {
				case 0: tr = loadLettersRow(inflater, 10);
						break;
				case 1: tr = loadLettersRow(inflater, 9);
						break;
				case 2: tr = loadLettersRow(inflater, 7);
						break;
				case 3: tr = loadNumbersRow(inflater);
						break;				
			}
		tr.setGravity(Gravity.CENTER);
		mKeyBoard.addView(tr);
		}
		
		return v;
	}
	
	public TableRow loadLettersRow(LayoutInflater inflater, int numLetters) {
		TableRow tr = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		Resources res = getResources();
		String[] letters = null;
		
		switch (numLetters){
			case 10:letters = res.getStringArray(R.array.first_row);
					break;
			case 9:letters = res.getStringArray(R.array.second_row);
					break;
			case 7:letters = res.getStringArray(R.array.third_row);
					break;			
		}
		 
		for (int j = 0; j < numLetters; j++) {
			View keyView = inflater.inflate(R.layout.fragment_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			button.setText(letters[j]);
			tr.addView(keyView);
		}
		
		params.setMargins(0, 10, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	public TableRow loadNumbersRow(LayoutInflater inflater) {
		TableRow tr = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		
		for (int i = 1; i <= 10; i++) {
			View keyView = inflater.inflate(R.layout.fragment_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			
			if (i == 10) 
				button.setText(String.valueOf(0));
			else
				button.setText(String.valueOf(i));
			
			tr.addView(keyView);
		}
		
		params.setMargins(0, 20, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	public static Fragment newInstance(int numSubWorld) {
		Bundle arg = new Bundle();
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}