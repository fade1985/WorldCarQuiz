package com.android.worldcarquiz.fragment;

import com.android.worldcarquiz.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuestionFragment{
	
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
			return inflater.inflate(R.layout.fragment_photo_question, null);
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
			return inflater.inflate(R.layout.fragment_banner, null);
		}
	}
}
