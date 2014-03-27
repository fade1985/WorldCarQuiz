package com.android.worldcarquiz.data;

import java.util.ArrayList;


public class World {
	private static final int NUM_SUBWORLDS = 3;

	private ArrayList<SubWorld> mSubWorlds;
	private int mUnlockedQuestions;
	private int mQuestionsToUnlock;
	private int mNumWorld;
	
	public World(int i, boolean create, int qtu) {
		mUnlockedQuestions = 0;
		mNumWorld = i;
		mSubWorlds = null;
		mQuestionsToUnlock = qtu;
		
		if (create) { 
			mSubWorlds = new ArrayList<SubWorld>();
			
			for (int j = 1; j <= NUM_SUBWORLDS; j++) {
				mSubWorlds.add(new SubWorld(j));
			}
			
		}
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
	
	public void correctAnswer() {
		mQuestionsToUnlock--;
	}
	
	public int getQuestionsToUnlock() {
		return mQuestionsToUnlock;
	}
}
