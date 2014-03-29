package com.android.worldcarquiz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;
import com.android.worldcarquiz.provider.QuestionAdapter;

public class SubWorldFragment extends Fragment {
	private ImageButton mButton;
	private int mPos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPos = getArguments().getInt("PRUEBA");		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_subworld, null);
		
		GridView gridView = (GridView) v.findViewById(R.id.gridview);
		gridView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		    {
		        // this 'mActivity' parameter is Activity object, you can send the current activity.
		        Intent i = new Intent(getActivity(), QuestionActivity.class);
		        startActivity(i);
		    }
		});
		gridView.setAdapter(new QuestionAdapter(getActivity()));
		
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
