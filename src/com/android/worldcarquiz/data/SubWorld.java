package com.android.worldcarquiz.data;

import java.util.ArrayList;

public class SubWorld {
	private static final int NUM_QUESTIONS = 20;
	private static final int QUESTIONS_TO_UNLOCK = 10;
	
	private int mUnlockedQuestions;
	private ArrayList<Question> mQuestions;
	
	public SubWorld(int valor) {
		mUnlockedQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			mQuestions.add(new Question(valor, false, 0));
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
