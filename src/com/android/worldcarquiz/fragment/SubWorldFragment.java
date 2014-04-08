package com.android.worldcarquiz.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ViewSwitcher;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.provider.QuestionAdapter;

public class SubWorldFragment extends Fragment {
	public static final String EXTRA_NUM_SUBWORLD = "extra_num_subWorld";
	public static final String EXTRA_NUM_WORLD = "extra_num_world";
	
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	private Handler mHandler = new Handler();
	private Vibrator vibrator;
	private ViewSwitcher switcher;
	private GridView mGridView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
		
		setHasOptionsMenu(true);
		mNumWorld = getArguments().getInt(EXTRA_NUM_WORLD);		
		mNumSubWorld = getArguments().getInt(EXTRA_NUM_SUBWORLD);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_subworld, null);
		
		mGridView = (GridView) v.findViewById(R.id.gridview);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		    {	
		    	mNumQuestion = position;
		    	if (!WorldCarQuizLab.get(getActivity()).questionAnswered(mNumWorld, mNumSubWorld, mNumQuestion)) {
			    	//efecto de cambiar de imagen
			    	switcher = (ViewSwitcher) v.findViewById(R.id.switcher);
			    	// Vibrate for 500 milliseconds
			    	 vibrator.vibrate(20);

	                if (switcher.getDisplayedChild() == 0) {
	                    switcher.showNext();
	                } else {
	                    switcher.showPrevious();
	                }
		    	}
           
				if (!WorldCarQuizLab.get(getActivity()).questionLocked(mNumWorld, mNumSubWorld, mNumQuestion)) {
					//Retraso el cambio de activity
				    mHandler.postDelayed(mUpdateTimeTask, 400);
				}    	      
		    }
		});
		mGridView.setAdapter(new QuestionAdapter(getActivity(), mNumWorld, mNumSubWorld));
		
		return v;
	}
	
	public static SubWorldFragment newInstance(int numWorld, int numSubWorld) {
		Bundle arg = new Bundle();
		arg.putInt(EXTRA_NUM_WORLD, numWorld);
		arg.putInt(EXTRA_NUM_SUBWORLD, numSubWorld);
		SubWorldFragment fragment = new SubWorldFragment();
		fragment.setArguments(arg);
		
		return fragment;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subworld, menu);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((BaseAdapter)mGridView.getAdapter()).notifyDataSetChanged();
	}
	
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   //Despues del Delay lanzamos la actividad
			   Intent i = new Intent(getActivity(), QuestionActivity.class);
			   i.putExtra(EXTRA_NUM_WORLD, mNumWorld);
			   i.putExtra(EXTRA_NUM_SUBWORLD, mNumSubWorld);
			   i.putExtra(QuestionFragment.EXTRA_NUM_QUESTION, mNumQuestion);
		       startActivity(i);
		   }
	};
}
