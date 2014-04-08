package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionFragment extends Fragment{
	public static final String EXTRA_NUM_QUESTION = "extra_num_question";
		
	public static Fragment newInstance(int type, int world, int subWorld, int question) {
		Fragment fragment;

		if (type == 0) {
			fragment = QuestionPhotoFragment.newInstance(world, subWorld, question);
		} else if (type == 1) {
			fragment = new QuestionResultFragment();
		} else {
			fragment = new QuestionBannerFragment();
		}
		return fragment;
	}
	
	public static class QuestionPhotoFragment extends Fragment {
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
			View v = inflater.inflate(R.layout.fragment_photo_question, null);
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
				}
			});
			
			return v;
		}
		
		public static Fragment newInstance(int world, int subWorld, int question) {
			Bundle arg = new Bundle();
			arg.putInt(SubWorldFragment.EXTRA_NUM_WORLD, world);
			arg.putInt(SubWorldFragment.EXTRA_NUM_SUBWORLD, subWorld);
			arg.putInt(EXTRA_NUM_QUESTION, question);
			QuestionPhotoFragment fragment = new QuestionPhotoFragment();
			fragment.setArguments(arg);
			
			return fragment;
		}
	}
	
	public static class QuestionResultFragment extends Fragment {
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.fragment_result_question, null);
		}
	}
		
	public static class QuestionBannerFragment extends Fragment {
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.fragment_banner_question, null);
		}
	}
}
