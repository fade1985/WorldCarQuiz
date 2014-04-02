package com.android.worldcarquiz.database;

import com.android.worldcarquiz.data.SubWorld;

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
		//context.deleteDatabase(DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		//Creamos la tabla de mundos
		sqlitedatabase.execSQL("create table worlds (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" name TEXT, locked INTEGER)");
		
		//Creamos la tabla de preguntas
		sqlitedatabase.execSQL("create table questions (world_id INTEGER references worlds(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, subWorld INTEGER," +
				" locked INTEGER, trys INTEGER)");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table cars (question_id INTEGER references questions(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, brand TEXT," +
				" model TEXT, segment TEXT, complexity INTEGER, year INTEGER," +
				" file_name TEXT)");
		
		//Inicializamos el primer mundo
		insertNewWorld(sqlitedatabase, 0);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
		
	}
	
	public void initializeTableQuestion(SQLiteDatabase sqlitedatabase, int world) {
		
		//Insertamos 5 preguntas desbloquedas
        for(int i = 0; i < SubWorld.QUESTIONS_TO_UNLOCK; i++)
        {
            //Insertamos los datos en la tabla Usuarios
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, subworld, locked, trys) " +
                       "VALUES (" + world + ", 1, 0, 0)");
        }
        
		//Insertamos el resto de preguntas bloquedas
        for(int i = 0; i < SubWorld.NUM_QUESTIONS - SubWorld.QUESTIONS_TO_UNLOCK; i++)
        {
            //Insertamos los datos en la tabla Usuarios
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, subworld, locked, trys) " +
                       "VALUES (" + world + ", 1, 1, 0)");
        }
        
        //Insertamos el resto de preguntas del segundo subMundo
        for(int i = 0; i < SubWorld.NUM_QUESTIONS; i++)
        {
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, subworld, locked, trys) " +
                       "VALUES (" + world + ", 2, 1, 0)");
        }
	}
	
	public boolean isWorldLocked(SQLiteDatabase sqlitedatabase, int numWorld) {
		//Select que consulta si el mundo esta bloqueado
		Cursor c = sqlitedatabase.rawQuery("SELECT locked FROM worlds WHERE _id=" + numWorld, null);
		boolean locked;
		
		if (c.moveToFirst()) {
			locked = c.getInt(0) == 1;
			return locked;
		} else
			return true;
	}
	
	public int questionsLocked(SQLiteDatabase sqlitedatabase, int numWorld, int subWorld) {
		//Select que devuelve el numero de preguntas bloqueadas, si no existe el mundo devuelve el máximo
		Cursor c = sqlitedatabase.rawQuery("SELECT count(*) FROM questions WHERE world_id =" + numWorld
				+" and locked = 1 and subWorld = " + subWorld, null);
		int numLocked;
		
		if (c.moveToFirst()) {
			numLocked = c.getInt(0);
			return numLocked;
		} else 
			return SubWorld.NUM_QUESTIONS;
	}
	
	public int questionsUnLocked(SQLiteDatabase sqlitedatabase, int numWorld, int subWorld) {
		//Select que devuelve el numero de preguntas desbloqueadas, si no existe el mundo devuelve el mínimo
		Cursor c = sqlitedatabase.rawQuery("SELECT count(*) FROM questions WHERE world_id =" + numWorld
				+" and locked = 0 and subWorld = " + subWorld, null);
		int numLocked;
		
		if (c.moveToFirst()) {
			numLocked = c.getInt(0);
			return numLocked;
		} else 
			return 0;
	}
	
	public void insertNewWorld(SQLiteDatabase sqlitedatabase, int numWorld) {
        //Insertamos los datos en la tabla Usuarios
        sqlitedatabase.execSQL("INSERT INTO worlds (name, locked) " +
                   "VALUES ('World " + numWorld + "', 0)");
        
        //Inicializamos sus preguntas
        initializeTableQuestion(sqlitedatabase, numWorld);
	}
	
	public int answeredQuestions(SQLiteDatabase sqlitedatabase, int numWorld, int subWorld) {
		//Select que devuelve el numero de preguntas acertadas, si no existe el mundo devuelve 0
		Cursor c = sqlitedatabase.rawQuery("SELECT count(*) FROM questions WHERE world_id =" + numWorld
				+" and locked = 0 and trys > 0 and subWorld = " + subWorld, null);
		int answered;
		
		if (c.moveToFirst()) {
			answered = c.getInt(0);
			return answered;
		} else 
			return 0;
	}
}
