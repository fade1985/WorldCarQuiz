package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;

public class WorldCarQuizLab {
	private static final int NUM_WORLDS = 10;
	private static WorldCarQuizLab sWorldCarQuizLab;
	
	private ArrayList<World> mWorlds;
	private Stadistics mStadistics;
	private Context mAppContext;
	
	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		mWorlds = new ArrayList<World>();
		for (int i = 0; i < NUM_WORLDS; i++) {
			mWorlds.add(new World(i));
		}
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
