package com.android.worldcarquiz.data;


public class World {
	private static final int sWorldsToUnlock = 40;

	public static int getmUnlockedQuestions() {
		return mUnlockedQuestions;
	}

	private SubWorld[] mSubWorlds;
	private static int mUnlockedQuestions;
	
	public World(int i) {
		mUnlockedQuestions = 0;
	}

	public static int getUnlockedQuestions() {
		return mUnlockedQuestions;
	}

	public void updateUnlockedQuestions() {
		mUnlockedQuestions = 0;	
		for (SubWorld s : mSubWorlds) {
			mUnlockedQuestions += s.getUnlockedQuestions();
		}
	}
	
	public static int getWorldstoUnlock() {
		return sWorldsToUnlock;
	}
		
}
