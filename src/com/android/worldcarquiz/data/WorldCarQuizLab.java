package com.android.worldcarquiz.data;

import java.util.ArrayList;

import android.content.Context;

public class WorldCarQuizLab {
	private ArrayList<World> mWorlds;
	private static WorldCarQuizLab sWorldCarQuizLab;
	private Stadistics mStadistics;
	private Context mAppContext;
	
	private WorldCarQuizLab(Context appContext) { 
		mAppContext = appContext;
		mWorlds = new ArrayList<World>();
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
}
