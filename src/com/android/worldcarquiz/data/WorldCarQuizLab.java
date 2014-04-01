package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;

public class WorldCarQuizLab {
	public static final int QUESTIONS_TO_UNLOCK = 40;
	private static final int NUM_WORLDS = 10;

	private static WorldCarQuizLab sWorldCarQuizLab;

	private ArrayList<World> mWorlds;
	private Stadistics mStadistics;
	private Context mAppContext;

	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		mWorlds = new ArrayList<World>();

		mWorlds.add(new World(0, true, 0));
		for (int i = 1; i < NUM_WORLDS; i++) {
			mWorlds.add(new World(i, false, QUESTIONS_TO_UNLOCK * i));
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