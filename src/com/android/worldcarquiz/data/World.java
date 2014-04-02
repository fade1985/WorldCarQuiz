package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;


public class World {
	private static final int NUM_SUBWORLDS = 2;

	private ArrayList<SubWorld> mSubWorlds;
	private int mUnlockedQuestions;
	private int mNumWorld;

	public World(int i, WorldQuizDatabaseHelper database) {
		mUnlockedQuestions = 0;
		mNumWorld = i;
		mSubWorlds = new ArrayList<SubWorld>();

		for (int j = 1; j <= NUM_SUBWORLDS; j++) {
			mSubWorlds.add(new SubWorld(mNumWorld, j, database));
		}
	}
	
	public World(int i) {
		mUnlockedQuestions = 0;
		mNumWorld = i;
		mSubWorlds = null;
	}
	
	public int getUnlockedQuestions() {
		updateUnlockedQuestions();
		return mUnlockedQuestions;
	}

	public void updateUnlockedQuestions() {
		mUnlockedQuestions = 0;	
		for (SubWorld s : mSubWorlds) {
			mUnlockedQuestions += s.getUnlockedQuestions();
		}
	}

	public int getNumWorld() {
		return mNumWorld;
	}

	public ArrayList<SubWorld> getSubWorlds(){
		return mSubWorlds;
	}

}