package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.worldcarquiz.R;

public class QuestionAnswerFragment extends Fragment {
	public static final String EXTRA_NUM_SUBWORLD = "extra_num_subworld";
	
	private int mNumSubWorld;
	private TableLayout mKeyBoard;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumSubWorld = getArguments().getInt(EXTRA_NUM_SUBWORLD);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_question_answer_write, null);
		
		mKeyBoard = (TableLayout) v.findViewById(R.id.table);
		for (int i = 0; i < 4; i++) {
			TableRow tr = new TableRow(getActivity());
			TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

			int limite = 10;
			if (i == 3) 
				params.setMargins(0, 20, 0, 0);
			else 
				params.setMargins(0, 10, 0, 0);
			tr.setLayoutParams(params);
			
			if (i == 1)
				limite = 9;
			else if (i == 2) 
				limite = 7;
			
			for (int j = 0; j < limite; j++) {
				View keyView = inflater.inflate(R.layout.fragment_key, null);
			    
				tr.addView(keyView);
			}

			tr.setLayoutParams(params);
			tr.setGravity(Gravity.CENTER);
			mKeyBoard.addView(tr);
		}
		
		return v;
	}
	
	public static Fragment newInstance(int numSubWorld) {
		Bundle arg = new Bundle();
		arg.putInt(EXTRA_NUM_SUBWORLD, numSubWorld);
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}