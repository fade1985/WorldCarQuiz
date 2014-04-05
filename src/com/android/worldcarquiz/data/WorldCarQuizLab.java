package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
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

	private WorldQuizDatabaseHelper mDatabase;
	private ArrayList<World> mWorlds;
	private Stadistics mStadistics;
	
	private Context mAppContext;

	/**
	 * Constructor del singleton.
	 */
	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		
		//Creamos el array de mundos
		mWorlds = new ArrayList<World>();
		
		//Abrimos la base de datos
		WorldQuizDatabaseHelper mDatabase = new WorldQuizDatabaseHelper(mAppContext);
		SQLiteDatabase db = mDatabase.getWritableDatabase();

		//El primer mundo siempre se crea
		mWorlds.add(new World(1, mDatabase, mAppContext));
		for (int i = 2; i <= NUM_WORLDS; i++) {
			//Recorremos la lista de mundos preguntando si están bloqueados o no
			if (!mDatabase.isWorldLocked(db, i)) {
				mWorlds.add(new World(i, mDatabase, mAppContext));
			} else {
				mWorlds.add(new World(i, mAppContext));
			}
		}
		
		mDatabase.close();
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
	 * Devuelve el mundo 'numWorld'.
	 */
	public World getWorld(int numWorld) {
		return mWorlds.get(numWorld);
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
	 * Modifica la pregunta como acertada y le asigna su puntuacion.
	 */
	public void setQuestionAnswered(int numWorld, int numSubWorld, int numQuestion, int points) {
		mWorlds.get(numWorld).getSubWorlds().get(numSubWorld).getQuestions()
			.get(numQuestion).setAnswered(points);
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