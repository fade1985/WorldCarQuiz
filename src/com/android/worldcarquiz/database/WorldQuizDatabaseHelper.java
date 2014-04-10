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

public class WorldQuizDatabaseHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "worldCarQuiz.sqlite";
	private static final int VERSION = 3;
	
	private static final String TABLE_QUESTIONS = "questions";
	private static final String TABLE_CARS = "cars";
	
	Context mContext;
	   
	public WorldQuizDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		mContext = context.getApplicationContext();
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {	
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
		if (i < j) {
			mContext.deleteDatabase(DB_NAME);
			onCreate(sqlitedatabase);
		}
	}
	
	/**
	 * Se inserta un nuevo mundo. En realidad sólo se insertan nuevas preguntas a la tabla.
	 * El control de los mundos se hace en función de la cantidad de preguntas que haya.
	 */
	public void insertNewWorld(SQLiteDatabase sqlitedatabase) {
	    //Inicializamos sus preguntas
	    insertQuestions(sqlitedatabase);
		insertCars(sqlitedatabase);
	}
	
	/**
	 * Método que introduce nuevas filas a la tabla de coches a través de un archivo sql.
	 */
	public void insertCars(SQLiteDatabase db) {
	    InputStream is = null;
	    try {
	    	db.beginTransaction();
	    	for (int i = 1; i <= World.NUM_SUBWORLDS; i++) {
	    		is = mContext.getAssets().open("dbWorlds/world1" + i +".sql");
	    		if (is != null) {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		            String line = reader.readLine();
		            while (!TextUtils.isEmpty(line)) {
		            	db.execSQL(line);
		                line = reader.readLine();
		            }
		         }
	    	}
	    	db.setTransactionSuccessful();
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
	
	/**
	 * Introduce un nuevo conjunto de preguntas a la tabla.
	 */
	void insertQuestions(SQLiteDatabase sqlitedatabase) {
		//Insertamos todas las preguntas del nuevo mundo
	    for(int i = 0; i < SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS; i++)
	    {
	    	//Las SubWorld.QUESTIONS_TO_UNLOCK primeras preguntas se introducen como desbloqueadas (locked = 0).
	    	int locked = (i < SubWorld.QUESTIONS_TO_UNLOCK) ? 0 : 1;
	        //Insertamos los datos en la tabla de preguntas.
	        sqlitedatabase.execSQL("INSERT INTO questions (locked) VALUES (" + locked + ")");
	    }
	}
		
	/**
	 * Realiza una consulta en base de datos y devuelve un cursor con la preguntas 
	 * del mundo que se le pasa por parámetro.
	 */
	public Cursor getWorldQuestions(SQLiteDatabase sqlitedatabase, int world) {
		/*
		 * Se calcula la primera y última pregunta que queremos recuperar en función de las
		 *constantes.
		 */
		int firstQuestion = (SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * (world - 1);
		int lastQuestion = (SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * world;
		
		//Se realiza la consulta en base de datos.
		Cursor c = sqlitedatabase.rawQuery("SELECT locked, trys, score, file_name, brand, model FROM questions, cars" +
						" WHERE questions._id = cars._id and questions._id <= " + lastQuestion +
						" and questions._id > " + firstQuestion, null);
		
		return c;
	}

	/**
	 * Marca una pregunta concreta como acertada y le asigna su puntuación.
	 */
	public void setQuestionAnswered(SQLiteDatabase db, int numWorld, int subWorld, int question, int score) {

		//Calculamos el id de la pregunta en base a las constantes declaradas y los parámetros recibidos.
		int numQuestion = 1 + ((SubWorld.NUM_QUESTIONS * World.NUM_SUBWORLDS) * numWorld) +
				(SubWorld.NUM_QUESTIONS * subWorld) + question;
		
		//Establecemos los campos-valores a actualizar
		ContentValues values = new ContentValues();
		values.put("locked","2");
		values.put("score",score);
		
		//Hacemos el update en la tabla.
		db.update(TABLE_QUESTIONS, values, "questions._id = " + numQuestion, null);
	}
}