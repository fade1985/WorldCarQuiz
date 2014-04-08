package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;

public class QuestionAnswerFragment extends Fragment {
	public static final String EXTRA_NUM_SUBWORLD = "extra_num_subworld";
	
	private int mNumSubWorld;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumSubWorld = getArguments().getInt(EXTRA_NUM_SUBWORLD);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int idLayout;
		
		if (mNumSubWorld > 0) {
			idLayout = R.layout.fragment_question_answer_write;
		} else {
			idLayout = R.layout.fragment_question_answer_options;
		}
		
		return inflater.inflate(idLayout, null);
		
	}
	
	public static Fragment newInstance(int numSubWorld) {
		Bundle arg = new Bundle();
		arg.putInt(EXTRA_NUM_SUBWORLD, numSubWorld);
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}