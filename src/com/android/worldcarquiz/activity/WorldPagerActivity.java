package com.android.worldcarquiz.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.fragment.SubWorldFragment;

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
				return SubWorldFragment.newInstance(arg0);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				getActionBar().setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});	
		
		ActionBar actionBar = getActionBar();
	    // Se especifica que se van a mostrar barras en la barra de accion
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());	
			}
			
			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				
			}
		};
		
	    // Se añaden los titulos de las tablas
	   actionBar.addTab(actionBar.newTab()
			   .setText("Rookie").setTabListener(tabListener));
	   actionBar.addTab(actionBar.newTab()
			   .setText("Expert").setTabListener(tabListener));	    
	   actionBar.addTab(actionBar.newTab()
			   .setText("Pro").setTabListener(tabListener));
	}
}
