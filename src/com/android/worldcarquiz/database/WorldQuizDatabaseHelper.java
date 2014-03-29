package com.android.worldcarquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorldQuizDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "worldQuiz.sqlite";
	private static final int VERSION = 1;
	
	private static final String TABLE_WORLDS = "worlds";
	private static final String TABLE_CARS = "cars";
    
	public WorldQuizDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		//Creamos la tabla de mundos
		sqlitedatabase.execSQL("create table " + TABLE_WORLDS + 
				"(_id integer primary key autoincrement, name varchar(50))");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table " + TABLE_CARS + 
				"(brand varchar(50), model varchar(50)," +
				" segment varchar(50), file_name varchar(50)," +
				" car_id integer references car(_id))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {

	}

}
