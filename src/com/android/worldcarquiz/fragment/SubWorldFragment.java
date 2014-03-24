package com.android.worldcarquiz.fragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;

public class SubWorldFragment extends Fragment {
	private Button mButton;
	private int mPos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPos = getArguments().getInt("PRUEBA");
			
		ActionBar actionBar = getActivity().getActionBar();
	    // Se especifica que se van a mostrar barras en la barra de accion
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				
			}
		};
		
	    // Se añaden los titulos de las tablas
	   actionBar.addTab(actionBar.newTab()
			   .setText("NIGGA").setTabListener(tabListener));
	   actionBar.addTab(actionBar.newTab()
			   .setText("SUPER-NIGGA").setTabListener(tabListener));	    
	   actionBar.addTab(actionBar.newTab()
			   .setText("NIGERRIMO").setTabListener(tabListener));
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_subworld, null);
		
		mButton = (Button)v.findViewById(R.id.boton_prueba);
		mButton.setText("Pagina: " + mPos);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), QuestionActivity.class);
				startActivity(i);
			}
		});
		
		return v;
	}
	
	public static SubWorldFragment newInstance(int pos) {
		Bundle arg = new Bundle();
		arg.putInt("PRUEBA", pos);
		SubWorldFragment fragment = new SubWorldFragment();
		fragment.setArguments(arg);
		
		return fragment;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subworld, menu);
	}
}
