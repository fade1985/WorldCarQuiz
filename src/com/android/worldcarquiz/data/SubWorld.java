package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

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
	public static final int QUESTIONS_UNLOCKED = 9;
	
	private ArrayList<Question> mQuestions;
	
	/**
	 * CONSTRUCTOR
	 */
	public SubWorld(Cursor cursor, Context appContext) {
		mQuestions = new ArrayList<Question>();
		
	     //Recorremos el cursor hasta que no haya más registros
	     for (int  i = 0; i <SubWorld.NUM_QUESTIONS; i++) {
	          int locked = cursor.getInt(0);
	          int trys = cursor.getInt(1);
	          int score  = cursor.getInt(2);
	          String image = cursor.getString(3);
	          String brand = cursor.getString(4);
	          String model = cursor.getString(5);
	          mQuestions.add(new Question(locked, trys, score, image, brand, model));
	          cursor.moveToNext();
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
