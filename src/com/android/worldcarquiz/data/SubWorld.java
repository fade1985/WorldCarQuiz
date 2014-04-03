package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class SubWorld {
	public static final int NUM_QUESTIONS = 30;
	public static final int QUESTIONS_TO_UNLOCK = 6;
	
	private ArrayList<Question> mQuestions;
	
	private Context mAppContext;
	
	public SubWorld(int numWorld, int numSubWorld, WorldQuizDatabaseHelper database, Context appContext) {
		mQuestions = new ArrayList<Question>();
		mAppContext = appContext;
		
		SQLiteDatabase db = database.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT locked, trys, questions._id, file_name FROM questions, cars" +
				" WHERE questions.world_id = " + numWorld +" and questions.subWorld = " + numSubWorld + 
				" and questions.subWorld = cars.complexity and questions._id = cars._id", null);
	
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		          int locked = c.getInt(0);
		          int trys = c.getInt(1);
		          int id = c.getInt(2);
		          int resImage = mAppContext.getResources().getIdentifier(c.getString(3), "drawable", mAppContext.getPackageName());
		          mQuestions.add(new Question(id, locked, trys, resImage));
		     } while(c.moveToNext());
		} 		
	}

	public int getUnlockedQuestions() {
		int unlockedQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isUnLocked()) {
				unlockedQuestions++;
			}
		}
		return unlockedQuestions;
	}
	
	public int getAnsweredQuestions() {
		int answeredQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isAnswered()) {
				answeredQuestions++;
			}
		}
		return answeredQuestions;
	}
	
	public int getLockedQuestions() {
		int lockedQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isLocked()) {
				lockedQuestions++;
			}
		}
		return lockedQuestions;
	}
	
	public ArrayList<Question> getQuestions() {
		return mQuestions;
	}
	
	/*public boolean questionLocked(int question) {
		return mQuestions.get(question).isLocked();
	}
	
	public boolean questionAnswered(int question) {
		return mQuestions.get(question).isAnswered();
	}
	
	public boolean questionUnlocked(int question) {
		return mQuestions.get(question).isUnLocked();
	}
	
	public int getImageId(int question) {
		return mQuestions.get(question).getImageId();
	}*/
}
