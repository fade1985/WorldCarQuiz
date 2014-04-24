package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.QuestionBannerFragment;
import com.android.worldcarquiz.fragment.QuestionSolvedFragment;

public class QuestionActivitySolved extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.container_question_solved);
		
		FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.Fragment fragmentSolved = new QuestionSolvedFragment();
		android.support.v4.app.Fragment fragmentBanner = new QuestionBannerFragment();
		
		fm.beginTransaction()
		.add(R.id.fragment_question_solved, fragmentSolved)
		.add(R.id.fragment_banner, fragmentBanner)
		.commit();
		
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}

}
