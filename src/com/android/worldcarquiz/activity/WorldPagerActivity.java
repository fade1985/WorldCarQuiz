package com.android.worldcarquiz.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.fragment.WorldFragment;

public class WorldPagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<SubWorld> mSubWorld;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mViewPager.setCurrentItem(0);
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return 3;
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return WorldFragment.newInstance(arg0);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
}
