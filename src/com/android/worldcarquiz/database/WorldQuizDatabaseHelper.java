package com.android.worldcarquiz.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.android.worldcarquiz.data.SubWorld;

public class WorldQuizDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "worldCarQuiz.sqlite";
	private static final int VERSION = 1;
	
	private static final String TABLE_WORLDS = "worlds";
	private static final String TABLE_QUESTIONS = "questions";
	private static final String TABLE_CARS = "cars";
	
	Context mContext;
	   
	public WorldQuizDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		mContext = context.getApplicationContext();
		context.deleteDatabase(DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		//Creamos la tabla de mundos
		sqlitedatabase.execSQL("create table " + TABLE_WORLDS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" name TEXT, locked INTEGER)");
		
		//Creamos la tabla de preguntas
		sqlitedatabase.execSQL("create table " + TABLE_QUESTIONS + " (world_id INTEGER references worlds(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, subWorld INTEGER," +
				" locked INTEGER, trys INTEGER)");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table " + TABLE_CARS + " (world_id INTEGER references worlds(_id)," +
				" _id INTEGER PRIMARY KEY AUTOINCREMENT, brand TEXT," +
				" model TEXT, segment TEXT, complexity INTEGER, year INTEGER," +
				" file_name TEXT)");
		
		//Inicializamos el primer mundo
		insertNewWorld(sqlitedatabase, 1);
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
		
	public void insertNewWorld(SQLiteDatabase sqlitedatabase, int numWorld) {
        //Insertamos los datos en la tabla Usuarios
        sqlitedatabase.execSQL("INSERT INTO worlds (name, locked) " +
                   "VALUES ('World " + numWorld + "', 0)");
        
        //Inicializamos sus preguntas
        initializeTableQuestion(sqlitedatabase, numWorld);
		insertCars(sqlitedatabase, numWorld);
	}
		
	public void insertCars(SQLiteDatabase db, int numWorld) {
	    InputStream is = null;
	    try {
	         is = mContext.getAssets().open("dbWorlds/world" + numWorld + ".sql");
	         if (is != null) {
	             db.beginTransaction();
	             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	             String line = reader.readLine();
	             while (!TextUtils.isEmpty(line)) {
	                 db.execSQL(line);
	                 line = reader.readLine();
	             }
	             db.setTransactionSuccessful();
	         }
	    } catch (Exception ex) {
	        Log.d("SQL_ERROR", ex.getMessage());      
	    } finally {
	        db.endTransaction();
	        if (is != null) {
	            try {
	                is.close();
	            } catch (IOException e) {
	                // Muestra log
	            }                
	        }
	    }
	}
	
	public void setQuestionAnswered(SQLiteDatabase sqlitedatabase, int numWorld, int subWorld, int question) {
		
	}
	
	/*public ArrayList getpossibleanswers(SQLiteDatabase db, String segment, int year)
	{
		//select para obtener coches del mismo segmento y año
		Cursor cursor = db.rawQuery("SELECT model, brand FROM cars WHERE segment=" + segment + " AND year=" + year , null);
		ArrayList<String> arrayAnswers = new ArrayList<String>();
		
		if(cursor.moveToFirst()){
			while(!cursor.isAfterLast())
			{
				String brand = cursor.getString(1);
				String model = cursor.getString(0);
				arrayAnswers.add( brand + " " + model);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		//recorremos el array hasta encontrar uno de su marca
		int i = 0;
		while (i < arrayAnswers.size())
		{
			ArrayList<String>aux = new ArrayList<String>();
			if(arrayAnswers.get(i) == )
				
		}
		
		
		
	}*/
	
}
