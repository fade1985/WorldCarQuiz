package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.QuestionFragment;

public class QuestionActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container_question);
		
		FragmentManager fm = getSupportFragmentManager();
/*		Fragment fragment = fm.findFragmentById(R.id.container_question);
		Fragment fragment2 = fm.findFragmentById(R.id.container_question);*/
		
		Fragment fragment = QuestionFragment.newInstance(0);
		Fragment fragment2 = QuestionFragment.newInstance(1);
		fm.beginTransaction()
		.add(R.id.fragment_photo, fragment)
		.add(R.id.fragment_result, fragment2)
		.commit();
		
	}
}
