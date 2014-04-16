package com.android.worldcarquiz.activity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ShareActionProvider;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.fragment.QuestionAnswerFragment;
import com.android.worldcarquiz.fragment.QuestionBannerFragment;
import com.android.worldcarquiz.fragment.QuestionPhotoFragment;
import com.android.worldcarquiz.fragment.SubWorldFragment;

public class QuestionActivity extends FragmentActivity{
	private int mNumSubWorld;
	private int mNumWorld;
	private int mNumQuestion;
	
	private ShareActionProvider mShareActionProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.container_question);
		
		mNumWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_WORLD, 0);
		mNumSubWorld = getIntent().getIntExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, 0);
		mNumQuestion = getIntent().getIntExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, 0);
		
		FragmentManager fm = getSupportFragmentManager();
		
		Fragment fragmentPhoto = QuestionPhotoFragment.newInstance(mNumWorld, mNumSubWorld, mNumQuestion);
		Fragment fragmentAnswer = QuestionAnswerFragment.newInstance(mNumWorld, mNumSubWorld, mNumQuestion);		
		Fragment fragmentBanner = new QuestionBannerFragment();
		
		fm.beginTransaction()
		.add(R.id.fragment_photo, fragmentPhoto)
		.add(R.id.fragment_answer, fragmentAnswer)
		.add(R.id.fragment_banner, fragmentBanner)
		.commit();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.  
        getMenuInflater().inflate(R.menu.menu_question, menu);  
        // Locate MenuItem with ShareActionProvider  
        MenuItem item = menu.findItem(R.id.menu_item_share);  
        // Fetch and store ShareActionProvider  
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();  
        setShareIntent(createShareIntent());  
        // Return true to display menu  
        return true;  
	};
	
    // Call to update the share intent  
    private void setShareIntent(Intent shareIntent) {  
         if (mShareActionProvider != null) {  
              mShareActionProvider.setShareIntent(shareIntent);  
         }  
    }  
    private Intent createShareIntent() {  
         Intent shareIntent = new Intent(Intent.ACTION_SEND);  
         shareIntent.setType("text/*");
         shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.es");         
        /* Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/ford_focus_2014");
         try {
			InputStream stream = getContentResolver().openInputStream(uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         shareIntent.putExtra(Intent.EXTRA_STREAM, uri);*/
         
         
         return shareIntent;  
    } 
}
