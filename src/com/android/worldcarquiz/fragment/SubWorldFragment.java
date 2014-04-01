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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivity;
import com.android.worldcarquiz.provider.QuestionAdapter;

public class SubWorldFragment extends Fragment {
	public static final String EXTRA_NUM_SUBWORLD = "extra_num_subWorld";
	
	private ImageButton mButton;
	private int mPos;
	private Handler mHandler = new Handler();
	private Vibrator vibrator;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
		
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
		    	
		    	//falta una condici�n que si la pregunta est� bloqueada no entre
		    	
		    	
		    	//efecto de cambiar de imagen
		    	ViewSwitcher switcher = (ViewSwitcher) v.findViewById(R.id.switcher);
		    	// Vibrate for 500 milliseconds
		    	 vibrator.vibrate(100);

                if (switcher.getDisplayedChild() == 0) {
                    switcher.showNext();
                } else {
                    switcher.showPrevious();
                }
                
                //Retraso el cambio de activity
                mHandler.postDelayed(mUpdateTimeTask, 500);
		    	
		       
		    }
		});
		gridView.setAdapter(new QuestionAdapter(getActivity()));
		
		return v;
	}
	
	public static SubWorldFragment newInstance(int numSubWorld) {
		Bundle arg = new Bundle();
		arg.putInt("PRUEBA", numSubWorld);
		SubWorldFragment fragment = new SubWorldFragment();
		fragment.setArguments(arg);
		
		return fragment;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subworld, menu);
	}
	
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   //Despues del Delay lanzamos la actividad
			   Intent i = new Intent(getActivity(), QuestionActivity.class);
		       startActivity(i);
		   }
		};
}
