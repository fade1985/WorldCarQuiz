package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class WorldCarQuizLab {
	public static final int QUESTIONS_TO_UNLOCK = 40;
	private static final int NUM_WORLDS = 10;

	private static WorldCarQuizLab sWorldCarQuizLab;

	private WorldQuizDatabaseHelper mDatabase;
	private ArrayList<World> mWorlds;
	private Stadistics mStadistics;
	private Context mAppContext;

	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		
		//Creamos el array de mundos
		mWorlds = new ArrayList<World>();
		
		//Abrimos la base de datos
		WorldQuizDatabaseHelper mDatabase = new WorldQuizDatabaseHelper(appContext);
		SQLiteDatabase db = mDatabase.getWritableDatabase();

		//El primer mundo siempre se crea
		mWorlds.add(new World(1, mDatabase));
		for (int i = 2; i <= NUM_WORLDS; i++) {
			//Recorremos la lista de mundos preguntando si están bloqueados o no
			if (!mDatabase.isWorldLocked(db, i)) {
				mWorlds.add(new World(i, mDatabase));
			} else {
				mWorlds.add(new World(i));
			}
		}
		
		mDatabase.close();
	}

	public static WorldCarQuizLab get(Context c) {
		if (sWorldCarQuizLab == null) {
			sWorldCarQuizLab = new WorldCarQuizLab(c.getApplicationContext());
		}
		return sWorldCarQuizLab;
	}

	public World getWorld(int numWorld) {
		return mWorlds.get(numWorld);
	}

	public ArrayList<World> getWorlds() {
		return mWorlds;
	}

}