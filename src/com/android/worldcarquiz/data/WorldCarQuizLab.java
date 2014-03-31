package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.worldcarquiz.database.WorldQuizDatabaseHelper;

public class WorldCarQuizLab {
	public static final int QUESTIONS_TO_UNLOCK = 40;
	private static final int NUM_WORLDS = 10;
	
	private static WorldCarQuizLab sWorldCarQuizLab;
	private WorldQuizDatabaseHelper mWqdbh;
	
	private ArrayList<World> mWorlds;
	private Stadistics mStadistics;
	private Context mAppContext;
	
	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		mWorlds = new ArrayList<World>();
		
		mWqdbh = new WorldQuizDatabaseHelper(mAppContext);
        
        SQLiteDatabase db = mWqdbh.getWritableDatabase();
               
		mWorlds.add(new World(0, true));
		for (int i = 1; i < NUM_WORLDS; i++) {
			if (!mWqdbh.isWorldLocked(db, i)) {
				mWorlds.add(new World(i, true));
			} else {
				mWorlds.add(new World(i, false));
			}
			
		}
		
		db.close();
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
	
	public WorldQuizDatabaseHelper getWqdbh() {
		return mWqdbh;
	}

}
