package com.android.worldcarquiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.cache.ImageLoader;
import com.android.worldcarquiz.data.WorldCarQuizLab;
import com.android.worldcarquiz.utils.LoaderImageView;

public class QuestionPhotoFragment extends Fragment {
	public static final String EXTRA_NUM_QUESTION = "extra_num_question";
	
	private int mNumWorld;
	private int mNumSubWorld;
	private int mNumQuestion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mNumWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_WORLD);
		mNumSubWorld = getArguments().getInt(SubWorldFragment.EXTRA_NUM_SUBWORLD);
		mNumQuestion = getArguments().getInt(EXTRA_NUM_QUESTION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_question_photo, null);
		LoaderImageView image = (LoaderImageView)v.findViewById(R.id.photo_question);
		
        // Image url
		String image_url = WorldCarQuizLab.get(getActivity()).getImage(mNumWorld, mNumSubWorld, mNumQuestion);

		// Loader image - will be shown before loading image
        int loader = R.drawable.ic_open_box_two;
        
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getActivity());
         
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(image_url, loader, image);
		
		return v;
	}
	
	public static Fragment newInstance(int numWorld, int numSubWorld, int numQuestion) {
		Bundle arg = new Bundle();
		arg.putInt(SubWorldFragment.EXTRA_NUM_WORLD, numWorld);
		arg.putInt(SubWorldFragment.EXTRA_NUM_SUBWORLD, numSubWorld);
		arg.putInt(EXTRA_NUM_QUESTION, numQuestion);
		QuestionPhotoFragment fragment = new QuestionPhotoFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}