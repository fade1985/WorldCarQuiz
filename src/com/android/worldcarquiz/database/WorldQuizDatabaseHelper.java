package com.android.worldcarquiz.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.android.worldcarquiz.data.SubWorld;
import com.android.worldcarquiz.data.World;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class WorldQuizDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "worldCarQuiz.sqlite";
	private static final int VERSION = 1;
	
	//private static final String TABLE_WORLDS = "worlds";
	private static final String TABLE_QUESTIONS = "questions";
	private static final String TABLE_CARS = "cars";
	
	Context mContext;
	   
	public WorldQuizDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		mContext = context.getApplicationContext();
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		//Creamos la tabla de mundos
		/*sqlitedatabase.execSQL("create table " + TABLE_WORLDS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" name TEXT, locked INTEGER)");
		
		//Creamos la tabla de preguntas
		sqlitedatabase.execSQL("create table " + TABLE_QUESTIONS + " (world_id INTEGER references worlds(_id)," +
				" subWorld INTEGER, num_question INTEGER, locked INTEGER INTEGER DEFAULT 0, " +
				"trys INTEGER INTEGER DEFAULT 0, points INTEGER INTEGER DEFAULT 0)");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table " + TABLE_CARS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" world_id INTEGER references worlds(_id), subworld INTEGER, brand TEXT, model TEXT," +
				" segment TEXT, year INTEGER, file_name TEXT)");
		
		//Inicializamos el primer mundo
		insertNewWorld(sqlitedatabase, 1);*/
		
		//Creamos la tabla de preguntas
		sqlitedatabase.execSQL("create table " + TABLE_QUESTIONS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" locked INTEGER, trys INTEGER INTEGER DEFAULT 0, score INTEGER INTEGER DEFAULT 0)");
		
		//Creamos la tabla de coches
		sqlitedatabase.execSQL("create table " + TABLE_CARS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" brand TEXT, model TEXT, segment TEXT, year INTEGER, file_name TEXT)");
		
		//Inicializamos el primer mundo
		insertNewWorld(sqlitedatabase);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
		
	}
	
	public void insertNewWorld(SQLiteDatabase sqlitedatabase) {
	    //Inicializamos sus preguntas
	    initializeTableQuestion(sqlitedatabase);
		insertCars(sqlitedatabase);
	}
	
	/*public void insertNewWorld(SQLiteDatabase sqlitedatabase, int numWorld) {
        //Insertamos los datos en la tabla Usuarios
        sqlitedatabase.execSQL("INSERT INTO worlds (name, locked) " +
                   "VALUES ('World " + numWorld + "', 0)");
        
        //Inicializamos sus preguntas
        initializeTableQuestion(sqlitedatabase, numWorld);
		insertCars(sqlitedatabase, numWorld);
	}*/
	
	public void initializeTableQuestion(SQLiteDatabase sqlitedatabase) {
		//Insertamos 5 preguntas desbloquedas
	    for(int i = 0; i < SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS; i++)
	    {
	    	int locked = (i < SubWorld.QUESTIONS_TO_UNLOCK) ? 0 : 1;
	        //Insertamos los datos en la tabla Usuarios
	        sqlitedatabase.execSQL("INSERT INTO questions (locked) VALUES (" + locked + ")");
	    }
	}
	
	/*public int getWorldsUnblocked(SQLiteDatabase sqlitedatabase) {	
		Cursor mCount= sqlitedatabase.rawQuery("SELECT COUNT(*) FROM questions", null);
		mCount.moveToFirst();
		return mCount.getInt(0)/(SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS);
	}*/
	
	public Cursor getWorldQuestions(SQLiteDatabase sqlitedatabase, int world) {
		int firstQuestion = (SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * (world - 1);
		int lastQuestion = (SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * world;
		
		Cursor c = sqlitedatabase.rawQuery("SELECT locked, trys, score, file_name FROM questions, cars" +
						" WHERE questions._id = cars._id and questions._id <= " + lastQuestion +
						" and questions._id > " + firstQuestion, null);
		
		return c;
	}
/*	public void initializeTableQuestion(SQLiteDatabase sqlitedatabase, int world) {
		
		//Insertamos 5 preguntas desbloquedas
        for(int i = 1; i <= SubWorld.QUESTIONS_TO_UNLOCK; i++)
        {
            //Insertamos los datos en la tabla Usuarios
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, num_question, subworld) " +
                       "VALUES (" + world + "," + i + ", 1)");
        }
        
		//Insertamos el resto de preguntas bloquedas
        for(int i = SubWorld.QUESTIONS_TO_UNLOCK + 1; i <= SubWorld.NUM_QUESTIONS; i++)
        {
            //Insertamos los datos en la tabla Usuarios
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, num_question, subworld, locked) " +
            		 "VALUES (" + world + "," + i + ", 1, 1)");
        }
        
        //Insertamos el resto de preguntas del segundo subMundo
        for(int i = 1; i <= SubWorld.NUM_QUESTIONS; i++)
        {
            sqlitedatabase.execSQL("INSERT INTO questions (world_id, num_question, subworld, locked) " +
            		"VALUES (" + world + "," + i + ", 2, 1)");
        }
	}*/
	
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
	
	public void insertCars(SQLiteDatabase db) {
	    InputStream is = null;
	    try {
	         is = mContext.getAssets().open("dbWorlds/world1.sql");
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
	/*public void insertCars(SQLiteDatabase db, int numWorld) {
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
	}*/
	
	public void setQuestionAnswered(SQLiteDatabase db, int numWorld, int subWorld, int question, int score) {
		//Establecemos los campos-valores a actualizar
		int numQuestion = 1 + ((SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * numWorld) +
				(SubWorld.NUM_QUESTIONS * subWorld) + question;
				
		ContentValues values = new ContentValues();
		values.put("locked","2");
		values.put("score",score);
		
		db.update(TABLE_QUESTIONS, values, "questions._id = " + numQuestion, null);
	}
	
	public void setQuestionUnlocked(SQLiteDatabase sqlitedatabase, int numWorld, int subWorld, int question) {
		//Establecemos los campos-valores a actualizar
		ContentValues updatedColumns = new ContentValues();
		updatedColumns.put("locked","1");
		
		sqlitedatabase.update(TABLE_QUESTIONS, updatedColumns, "world_id = " + numWorld + 
				"subWorld = " + subWorld + "", null);
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
	public void resetDatabase() { 
		mContext.deleteDatabase(DB_NAME);
	}
}
