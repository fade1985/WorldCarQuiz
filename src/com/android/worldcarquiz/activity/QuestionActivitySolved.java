package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.QuestionBannerFragment;
import com.android.worldcarquiz.fragment.QuestionPhotoFragment;
import com.android.worldcarquiz.fragment.QuestionSolvedFragment;
import com.android.worldcarquiz.fragment.SubWorldFragment;

public class QuestionActivitySolved extends FragmentActivity{
	
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.container_question_solved);
		
		mNumWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_WORLD, 0);
		mNumSubWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, 0);
		mNumQuestion = getIntent().getIntExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, 0);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragmentSolved = QuestionSolvedFragment.newInstance(mNumWorld, mNumSubWorld, mNumQuestion);
		android.support.v4.app.Fragment fragmentBanner = new QuestionBannerFragment();
		
		fm.beginTransaction()
		.add(R.id.fragment_question_solved, fragmentSolved)
		.add(R.id.fragment_banner, fragmentBanner)
		.commit();
		
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
	

}
