package com.android.worldcarquiz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorldQuizDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "worldCarQuiz.sqlite";
	private static final int VERSION = 1;
	
	private static final String TABLE_WORLDS = "worlds";
	private static final String TABLE_CARS = "cars";
	private static final String TABLE_QUESTIONS = "questions";
    
	public WorldQuizDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		context.deleteDatabase(DB_NAME);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		//Creamos la tabla de mundos
		sqlitedatabase.execSQL("create table worlds (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" name TEXT, locked INTEGER)");
		initializeTableWorlds(sqlitedatabase);
		
		//Creamos la tabla de preguntas
		sqlitedatabase.execSQL("create table questions (world_id INTEGER references worlds(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, difficulty INTEGER," +
				" locked INTEGER, trys INTEGER)");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table cars (question_id INTEGER references questions(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, brand TEXT," +
				" model TEXT, segment TEXT, complexity INTEGER, year INTEGER," +
				" file_name TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {

	}

	public void initializeTableWorlds(SQLiteDatabase sqlitedatabase) {
		//Insertamos 5 usuarios de ejemplo
        for(int i = 1; i <= 5; i++)
        {
            //Insertamos los datos en la tabla Usuarios
            sqlitedatabase.execSQL("INSERT INTO worlds (name, locked) " +
                       "VALUES ('World " + i +"', 1)");
        }
	}
	
	public boolean isWorldLocked(SQLiteDatabase sqlitedatabase, int numWorld) {
		Cursor c = sqlitedatabase.rawQuery("SELECT locked FROM worlds WHERE _id=" + numWorld, null);
		boolean locked;
		
		c.moveToFirst();
		locked = c.getInt(0) == 1;
		return locked;
	}
	
	public int questionsLocked(SQLiteDatabase sqlitedatabase, int numWorld) {
		Cursor c = sqlitedatabase.rawQuery("SELECT count(*) FROM questions WHERE world_id=" + numWorld
				+" and locked = 1", null);
		int numLocked;
		
		c.moveToFirst();
		numLocked = c.getInt(0);
		return numLocked;
	}
	
	public int questionsUnLocked(SQLiteDatabase sqlitedatabase, int numWorld) {
		Cursor c = sqlitedatabase.rawQuery("SELECT count(*) FROM questions WHERE world_id=" + numWorld
				+" and locked = 0", null);
		int numLocked;
		
		c.moveToFirst();
		numLocked = c.getInt(0);
		return numLocked;
	}
}
