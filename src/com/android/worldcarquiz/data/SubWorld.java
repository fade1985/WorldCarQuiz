package com.android.worldcarquiz.data;

import java.util.ArrayList;

public class SubWorld {
	private int mUnlockedQuestions;
	private ArrayList<Question> mQuestions;
	
	public SubWorld() {
		
	}

	public int getUnlockedQuestions() {
		mUnlockedQuestions = 0;
		updateUnlockedQuestions();
		return mUnlockedQuestions;
	}
	
	public void updateUnlockedQuestions() {
		for (Question q : mQuestions) {
			if (q.isSolved()) {
				mUnlockedQuestions++;
			}
		}
	}
}
