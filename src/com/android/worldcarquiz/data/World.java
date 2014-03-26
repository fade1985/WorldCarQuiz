package com.android.worldcarquiz.data;


public class World {
	private static final int QUESTIONS_TO_UNLOCK = 40;
	private static final int NUM_SUBWORLDS = 3;

	private SubWorld[] mSubWorlds;
	private int mUnlockedQuestions;
	private int mNumWorld;
	
	public World(int i, boolean create) {
		mUnlockedQuestions = 0;
		mNumWorld = i;
		
		if (create) { 
			for (int j = 0; j < NUM_SUBWORLDS; j++) {
				mSubWorlds[j] = new SubWorld(j);
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
	
	
}
