package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.WorldsListFragment;

public class WorldsListActivity extends FragmentActivity {
        
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.container_list_worlds);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.container_list_worlds);
		
		if (fragment == null) {
			fragment = new WorldsListFragment();
			fm.beginTransaction().add(R.id.container_list_worlds, fragment).commit();
		}
	}
}
