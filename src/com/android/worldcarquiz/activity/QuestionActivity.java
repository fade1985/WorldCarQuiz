package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.QuestionFragment;
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
		mNumQuestion = getIntent().getIntExtra(QuestionFragment.EXTRA_NUM_QUESTION, 0);
		
		FragmentManager fm = getSupportFragmentManager();
		
		Fragment fragment = QuestionFragment.newInstance(0);
		Fragment fragment2 = QuestionFragment.newInstance(1);
		Fragment fragment3 = QuestionFragment.newInstance(2);
		fm.beginTransaction()
		.add(R.id.fragment_photo, fragment)
		.add(R.id.fragment_result, fragment2)
		.add(R.id.fragment_banner, fragment3)
		.commit();
		
	}
}
