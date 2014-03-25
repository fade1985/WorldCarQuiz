package com.android.worldcarquiz.data;


public class World {
	private static final int sWorldsToUnlock = 40;
	
	private SubWorld[] mSubWorlds;
	private int mUnlockedQuestions;
	
	public World(int i) {
		mUnlockedQuestions = 0;
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
		
}
