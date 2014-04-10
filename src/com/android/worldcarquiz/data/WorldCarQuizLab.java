package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

/**
 * Singleton que contiene toda la información de la aplicación (ArrayList de
 * mundos, submundos, preguntas), además de la base de datos y las estadísticas.
 */
public class WorldCarQuizLab {
	public static final int QUESTIONS_TO_UNLOCK_WORLD = 40;
	private static final int NUM_WORLDS = 10;

	private static WorldCarQuizLab sWorldCarQuizLab;
	WorldQuizDatabaseHelper mDatabase; 
	
	private ArrayList<World> mWorlds;
	//private Stadistics mStadistics;
	
	private Context mAppContext;

	/**
	 * Constructor del singleton.
	 */
	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		
		//Creamos el array de mundos
		mWorlds = new ArrayList<World>();
		
		//Abrimos la base de datos
		mDatabase = new WorldQuizDatabaseHelper(mAppContext);
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		Cursor cursor;
		
		for (int i = 1; i <= NUM_WORLDS; i++) {
			//Recorremos la lista de mundos
			cursor = mDatabase.getWorldQuestions(db, i);
			mWorlds.add(new World(cursor, mAppContext));
		}
		
		db.close();
	}

	/**
	 * Si no existe instancia de la clase, la creamos.
	 */
	public static WorldCarQuizLab get(Context c) {
		if (sWorldCarQuizLab == null) {
			sWorldCarQuizLab = new WorldCarQuizLab(c.getApplicationContext());
		}
		return sWorldCarQuizLab;
	}

	/**
	 * Devuelve el ArrayList de mundos.
	 */
	public ArrayList<World> getWorlds() {
		return mWorlds;
	}
	
	/**
	 * Devuelve true si la pregunta del mundo 'numWorld' y del submundo 'numSubWorld' esta bloqueada.
	 */
	public boolean questionLocked(int numWorld, int numSubWorld, int question) {
		return mWorlds.get(numWorld).getSubWorlds().get(numSubWorld)
				.getQuestions().get(question).isLocked();
	}

	/**
	 * Devuelve true si la pregunta del mundo 'numWorld' y del submundo 'numSubWorld' ha sido resuelta.
	 */
	public boolean questionAnswered(int numWorld, int numSubWorld, int question) {
		return mWorlds.get(numWorld).getSubWorlds().get(numSubWorld)
				.getQuestions().get(question).isAnswered();
	}
		
	/**
	 * Devuelve true si la pregunta del mundo 'numWorld' y del submundo 'numSubWorld' esta desbloqueada.
	 */
	public boolean questionUnlocked(int numWorld, int numSubWorld, int question) {
		return mWorlds.get(numWorld).getSubWorlds().get(numSubWorld)
				.getQuestions().get(question).isUnLocked();
	}
	
	/**
	 * Devuelve el id del recurso asociado al drawable de la pregunta.
	 */
	public int getImageId(int numWorld, int numSubWorld, int question) {
		return mWorlds.get(numWorld).getSubWorlds().get(numSubWorld)
				.getQuestions().get(question).getImageId();
	}
	
	/**
	 * Devuelve la respuesta de la pregunta.
	 */
	public String getQuestionAnswer(int numWorld, int numSubWorld, int question) {
		return mWorlds.get(numWorld).getSubWorlds().get(numSubWorld)
				.getQuestions().get(question).getAnswer();
	}
	
	/**
	 * Modifica la pregunta como acertada y le asigna su puntuacion.
	 */
	public void setQuestionAnswered(int numWorld, int numSubWorld, int numQuestion, int score) {
		mWorlds.get(numWorld).getSubWorlds().get(numSubWorld).getQuestions()
			.get(numQuestion).setAnswered(score);
		
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		//Modificamos en la base de datos
		mDatabase.setQuestionAnswered(db, numWorld, numSubWorld, numQuestion, score);
		db.close();
	}
	
	/**
	 * Método que recupera el total de preguntas acertadas hasta el momento
	 */
	public int getTotalAnsweredQuestion() {
		int total = 0;
		
		for (int i = 0; i < WorldCarQuizLab.NUM_WORLDS; i++) {
			//Preguntamos si existe el mundo
			if (getWorlds().get(i).getSubWorlds() != null) {
				total += getWorlds().get(i).getAnsweredQuestions();
			}
		}
		
		return total;
	}
}