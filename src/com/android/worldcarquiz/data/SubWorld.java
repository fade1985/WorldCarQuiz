package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

/**
 * Clase correspondiente a los subniveles de cada mundo (Principiante y experto.)
 *
 */
public class SubWorld {
	/**
	 * Atributos de la clase:
	 * -NUM_QUESTIONS: número de preguntas por subnivel. Constante.
	 * -QUESTIONS_TO_UNLOCK: número de preguntas desbloqueadas inicialmente. Constante.
	 * -mQuestions: ArrayList de tamaño NUM_QUESTIONS, que contiene instancias de la clase Question.
	 */
	public static final int NUM_QUESTIONS = 30;
	public static final int QUESTIONS_TO_UNLOCK = 10;
	
	private ArrayList<Question> mQuestions;
	
	/**
	 * CONSTRUCTOR
	 */
	public SubWorld(int numWorld, int numSubWorld, WorldQuizDatabaseHelper database, Context appContext) {
		mQuestions = new ArrayList<Question>();
		
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
		          int resImage = appContext.getResources().getIdentifier(c.getString(3), "drawable", appContext.getPackageName());
		          mQuestions.add(new Question(id, locked, trys, resImage));
		     } while(c.moveToNext());
		} 		
	}

	/**
	 * Devuelve el número de preguntas desbloqueadas del submundo.
	 */
	public int getUnlockedQuestions() {
		int unlockedQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isUnLocked()) {
				unlockedQuestions++;
			}
		}
		return unlockedQuestions;
	}
	
	/**
	 * Devuelve el número de preguntas acertadas del submundo.
	 */
	public int getAnsweredQuestions() {
		int answeredQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isAnswered()) {
				answeredQuestions++;
			}
		}
		return answeredQuestions;
	}
	
	/**
	 * Devuelve el número de preguntas bloqueadas del submundo.
	 */
	public int getLockedQuestions() {
		int lockedQuestions = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isLocked()) {
				lockedQuestions++;
			}
		}
		return lockedQuestions;
	}
	
	/**
	 * Devuelve la puntuación total del submundo
	 */
	public int getSubWorldScore() {
		int score = 0;
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			if (mQuestions.get(i).isAnswered()) {
				score += mQuestions.get(i).getScore();
			}
		}
		
		return score;
	}
	
	/**
	 * Devuelve el ArrayList de preguntas.
	 */
	public ArrayList<Question> getQuestions() {
		return mQuestions;
	}
	
}
