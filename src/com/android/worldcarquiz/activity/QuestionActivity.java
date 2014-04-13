package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.fragment.QuestionBannerFragment;
import com.android.worldcarquiz.fragment.QuestionPhotoFragment;
import com.android.worldcarquiz.fragment.QuestionAnswerFragment;
import com.android.worldcarquiz.fragment.SubWorldFragment;

public class QuestionActivity extends FragmentActivity{
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container_question);
		
		mNumWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_WORLD, 0);
		mNumSubWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, 0);
		mNumQuestion = getIntent().getIntExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, 0);
		
		FragmentManager fm = getSupportFragmentManager();
		
		Fragment fragmentPhoto = QuestionPhotoFragment.newInstance(mNumWorld, mNumSubWorld, mNumQuestion);
		Fragment fragmentAnswer = QuestionAnswerFragment.newInstance(mNumWorld, mNumSubWorld, mNumQuestion);		
		Fragment fragmentBanner = new QuestionBannerFragment();
		
		fm.beginTransaction()
		.add(R.id.fragment_photo, fragmentPhoto)
		.add(R.id.fragment_answer, fragmentAnswer)
		.add(R.id.fragment_banner, fragmentBanner)
		.commit();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fragment_subworld, menu);
		return true;
	};
}
