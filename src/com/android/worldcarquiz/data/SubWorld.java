package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class SubWorld {
	public static final int NUM_QUESTIONS = 30;
	public static final int QUESTIONS_TO_UNLOCK = 6;
	
	private int mUnlockedQuestions;
	private int mAnsweredQuestions;
	private ArrayList<Question> mQuestions;
	
	public SubWorld(int numWorld, int numSubWorld, WorldQuizDatabaseHelper database) {
		mQuestions = new ArrayList<Question>();
		
		SQLiteDatabase db = database.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT locked, trys, _id FROM questions WHERE world_id =" + numWorld
				+" and subWorld = " + numSubWorld, null);
		
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		          int locked = c.getInt(0);
		          int trys = c.getInt(1);
		          int id = c.getInt(2);
		          mQuestions.add(new Question(id, locked, trys, 0));
		     } while(c.moveToNext());
		} 		
		
		mUnlockedQuestions = database.questionsUnLocked(db, numWorld, numSubWorld);
		mAnsweredQuestions = database.answeredQuestions(db, numWorld, numSubWorld);
	}

	public int getUnlockedQuestions() {
		return mUnlockedQuestions;
	}
	
	public int getAnsweredQuestions() {
		return mAnsweredQuestions;
	}
	
}
