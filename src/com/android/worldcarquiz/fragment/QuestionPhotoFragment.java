package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionPhotoFragment extends Fragment {
	public static final String EXTRA_NUM_QUESTION = "extra_num_question";
	
	private int mNumWorld;
	private int mNumSubWorld;
	private int mNumQuestion;
	
	private ImageButton mHelp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_WORLD);
		mNumSubWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_SUBWORLD);
		mNumQuestion = getArguments().getInt(EXTRA_NUM_QUESTION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_question_photo, null);
		ImageView iv = (ImageView)v.findViewById(R.id.photo_question);
		int id = WorldCarQuizLab.get(getActivity())
					.getImageId(mNumWorld, mNumSubWorld, mNumQuestion);
		iv.setImageResource(id);
		
		mHelp = (ImageButton) v.findViewById(R.id.help_button);
		mHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				WorldCarQuizLab.get(getActivity())
					.setQuestionAnswered(mNumWorld, mNumSubWorld, mNumQuestion, mNumQuestion + 1);
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
				Fragment fragment = new QuestionSolvedFragment();
				fm.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
					.replace(R.id.fragment_answer, fragment).commit();
			}
		});
		
		return v;
	}
	
	public static Fragment newInstance(int numWorld, int numSubWorld, int numQuestion) {
		Bundle arg = new Bundle();
		arg.putInt(SubWorldFragment.EXTRA_NUM_WORLD, numWorld);
		arg.putInt(SubWorldFragment.EXTRA_NUM_SUBWORLD, numSubWorld);
		arg.putInt(EXTRA_NUM_QUESTION, numQuestion);
		QuestionPhotoFragment fragment = new QuestionPhotoFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}