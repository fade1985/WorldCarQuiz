package com.android.worldcarquiz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;

public class WorldFragment extends Fragment {
	private Button mButton;
	private int mPos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mPos = getArguments().getInt("PRUEBA");
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_world, null);
		
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
	
	public static WorldFragment newInstance(int pos) {
		Bundle arg = new Bundle();
		arg.putInt("PRUEBA", pos);
		WorldFragment fragment = new WorldFragment();
		fragment.setArguments(arg);
		
		return fragment;
		
	}
}
