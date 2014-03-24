package com.android.worldcarquiz.data;

import java.util.ArrayList;

public class World {
	private static final int sWorldsToUnlock = 30;
	
	private ArrayList<Question> mQuestions;
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
		for (Question q : mQuestions) {
			if (q.isSolved()) {
				mUnlockedQuestions++;
			}
		}
	}
		
}
