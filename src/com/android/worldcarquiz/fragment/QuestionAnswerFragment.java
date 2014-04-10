package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
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
		
		if (mNumSubWorld > 0) {
			View v = inflater.inflate(R.layout.fragment_question_answer_write, null);
			
			mKeyBoard = (TableLayout) v.findViewById(R.id.table);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			for (int i = 0; i < 4; i++) {
				TableRow tr = new TableRow(getActivity());
				int limite = 10;
				if (i == 3) 
					params.setMargins(0, 50, 0, 50);
				else 
					params.setMargins(0, 20, 0, 20);
				
				if (i == 1)
					limite = 9;
				else if (i == 2) 
					limite = 7;
				
				for (int j = 0; j < limite; j++) {
					View key = inflater.inflate(R.layout.fragment_key, null);

					tr.addView(key);
				}

				tr.setLayoutParams(params);
				tr.setGravity(Gravity.CENTER);
				mKeyBoard.addView(tr);
			}
			
			return v;
		} else {
			return inflater.inflate(R.layout.fragment_question_answer_options, null);
		}
	}
	
	public static Fragment newInstance(int numSubWorld) {
		Bundle arg = new Bundle();
		arg.putInt(EXTRA_NUM_SUBWORLD, numSubWorld);
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}