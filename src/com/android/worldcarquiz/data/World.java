package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;


public class World {
	private static final int NUM_SUBWORLDS = 2;

	private ArrayList<SubWorld> mSubWorlds;
	
	/**
	 * CONSTRUCTOR 1
	 * Inicializa los submundos recuperando la información de la base de datos.
	 */
	public World(int numWorld, WorldQuizDatabaseHelper database, Context appContext) {
		mSubWorlds = new ArrayList<SubWorld>();
		
		for (int j = 1; j <= NUM_SUBWORLDS; j++) {
			mSubWorlds.add(new SubWorld(numWorld, j, database, appContext));
		}
	}
	
	/**
	 * CONSTRUCTOR 2
	 * Crea el mundo con los submundos vacíos.
	 */
	public World(int i, Context appContext) {
		mSubWorlds = null;
	}
	
	/**
	 * Método que devuelve el número de preguntas desbloqueadas del mundo.
	 */
	public int getUnlockedQuestions() {
		int unlockedQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			unlockedQuestions += mSubWorlds.get(i).getUnlockedQuestions();
		}
		
		return unlockedQuestions;
	}
	
	/**
	 * Método que devuelve el número de preguntas respondidas del mundo.
	 */
	public int getAnsweredQuestions() {
		int answeredQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			answeredQuestions += mSubWorlds.get(i).getAnsweredQuestions();
		}
		
		return answeredQuestions;
	}
	
	/**
	 * Método que devuelve el número de preguntas bloqueadas del mundo
	 */
	public int getLockedQuestions() {
		int lockedQuestions = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			lockedQuestions += mSubWorlds.get(i).getLockedQuestions();
		}
		
		return lockedQuestions;
	}

	public int getWorldScore() {
		int score = 0;
		
		for (int i = 0; i < NUM_SUBWORLDS; i++) {
			score += mSubWorlds.get(i).getSubWorldScore();
		}
		
		return score;
	}

	public ArrayList<SubWorld> getSubWorlds(){
		return mSubWorlds;
	}

}