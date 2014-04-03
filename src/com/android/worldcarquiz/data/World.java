package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;


public class World {
	private static final int NUM_SUBWORLDS = 2;

	private ArrayList<SubWorld> mSubWorlds;
	private int mNumWorld;
	
	private Context mAppContext;

	public World(int i, WorldQuizDatabaseHelper database, Context appContext) {
		mNumWorld = i;
		mSubWorlds = new ArrayList<SubWorld>();
		mAppContext = appContext;
		
		for (int j = 1; j <= NUM_SUBWORLDS; j++) {
			mSubWorlds.add(new SubWorld(mNumWorld, j, database, appContext));
		}
	}
	
	public World(int i, Context appContext) {
		mNumWorld = i;
		mAppContext = appContext;
		mSubWorlds = null;
	}
	
	public int getUnlockedQuestions() {
		int unlockedQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			unlockedQuestions += mSubWorlds.get(i).getUnlockedQuestions();
		}
		
		return unlockedQuestions;
	}
	
	public int getAnsweredQuestions() {
		int answeredQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			answeredQuestions += mSubWorlds.get(i).getAnsweredQuestions();
		}
		
		return answeredQuestions;
	}
	
	public int getLockedQuestions() {
		int lockedQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			lockedQuestions += mSubWorlds.get(i).getLockedQuestions();
		}
		
		return lockedQuestions;
	}

	public int getNumWorld() {
		return mNumWorld;
	}

	public ArrayList<SubWorld> getSubWorlds(){
		return mSubWorlds;
	}

}