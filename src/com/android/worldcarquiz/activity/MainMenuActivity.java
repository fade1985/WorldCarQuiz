package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.MainMenuFragment;

public class MainMenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container_main_menu);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.container_main_menu);
		
		if (fragment == null) {
			fragment = new MainMenuFragment();
			fm.beginTransaction().add(R.id.container_main_menu, fragment).commit();
		}
	}
}
