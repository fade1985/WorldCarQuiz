package com.android.worldcarquiz.data;

import java.util.ArrayList;

public class SubWorld {
	private static final int NUM_QUESTIONS = 30;
	private static final int QUESTIONS_TO_UNLOCK = 15;
	
	private int mUnlockedQuestions;
	private ArrayList<Question> mQuestions;
	
	public SubWorld(int valor) {
		mUnlockedQuestions = 0;
		mQuestions = new ArrayList<Question>();
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			mQuestions.add(new Question(valor));
		}
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
