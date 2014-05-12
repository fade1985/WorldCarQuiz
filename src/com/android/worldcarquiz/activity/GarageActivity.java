package com.android.worldcarquiz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;
import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.GarageInfoFragment;
import com.android.worldcarquiz.fragment.GarageMenuFragment;
import com.android.worldcarquiz.fragment.GarageRandomFragment;
import com.android.worldcarquiz.fragment.QuestionBannerFragment;

public class GarageActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.container_garage);
		
		FragmentManager fm = getSupportFragmentManager();
		
		Fragment fragmentGarageMenu = new GarageMenuFragment();
		Fragment fragmentGarageInfo = new GarageInfoFragment();
		Fragment fragmentGarageRandom = new GarageRandomFragment();
		Fragment fragmentBanner = new QuestionBannerFragment();
		
		fm.beginTransaction()
		.add(R.id.fragment_garage_menu, fragmentGarageMenu)
		.add(R.id.fragment_garage_info, fragmentGarageInfo)
		.add(R.id.fragment_garage_random, fragmentGarageRandom)
		.add(R.id.fragment_banner, fragmentBanner)
		.commit();
		
	}
	
}
