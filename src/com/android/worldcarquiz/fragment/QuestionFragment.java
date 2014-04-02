package com.android.worldcarquiz.fragment;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.WorldCarQuizLab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class QuestionFragment{
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	
	public static Fragment newInstance(int type) {
		Fragment fragment;
		
		if (type == 0) {
			fragment = new QuestionPhotoFragment();
		} else if (type == 1) {
			fragment = new QuestionResultFragment();
		} else {
			fragment = new QuestionBannerFragment();
		}
		return fragment;
	}
	
	public static class QuestionPhotoFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_photo_question2, null);
			//ImageView iv = (ImageView)v.findViewById(R.id.photo_question);
			//iv.setImageResource(WorldCarQuizLab.get(getActivity()).getImageId(numWorld, numSubWorld, question));
			return v;
		}
	}
	
	public static class QuestionResultFragment extends Fragment {
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.fragment_result_question2, null);
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
