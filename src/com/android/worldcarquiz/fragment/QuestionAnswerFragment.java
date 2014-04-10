package com.android.worldcarquiz.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.worldcarquiz.R;

public class QuestionAnswerFragment extends Fragment {
	private static final String EXTRA_ANSWER = "extra_answer";
	
	private String mAnswer;
	private String[] mArrayAnswer;
	private TableLayout mKeyBoard;
	private TableLayout mTableAnswer;
	private Vibrator mVibrator;
	private int mLastPosition;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAnswer = getArguments().getString(EXTRA_ANSWER);
		String[] answerSplit = mAnswer.split(" ");
		String carBrand = answerSplit[0];
		String carModel = answerSplit[1];
		mArrayAnswer = new String[carBrand.length() + carModel.length()];
		mVibrator = ((Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE));
		mLastPosition = 0;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//Inflamos el layout del fragment que contiene el editText y el teclado virtual.
		View v = inflater.inflate(R.layout.fragment_question_answer, null);
		mTableAnswer = (TableLayout) v.findViewById(R.id.tableAnswer);
		buildTableAnswer(inflater, mTableAnswer);
		
		mKeyBoard = (TableLayout) v.findViewById(R.id.tableKeyboard);
		TableRow tr = null;
		
		/*Creamos 4 filas y las añadimos al TableLayout del fragment. Cada fila tiene diferente numero de letras,
		 *por eso se le llama con distintos parámetros. La última fila es fija, ya que son los dígitos.*/
		for (int i = 0; i < 4; i++) {
			switch (i) {
				case 0: tr = loadLettersRow(inflater, 10);
						break;
				case 1: tr = loadLettersRow(inflater, 9);
						break;
				case 2: tr = loadLettersRow(inflater, 7);
						break;
				case 3: tr = loadNumbersRow(inflater);
						break;				
			}
		//Añadimos el gravity centrado e insertamos en el TableLayout.
		tr.setGravity(Gravity.CENTER);
		mKeyBoard.addView(tr);
		}
		
		return v;
	}
	
	/**
	 * Devuelve una fila de botones de tamaño numLetters.
	 */
	public TableRow loadLettersRow(LayoutInflater inflater, int numLetters) {
		//Se inicializa el TableRow y se le asigna sus parametros.
		TableRow tr = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		Resources res = getResources();
		String[] letters = null;
		
		//Recuperamos de los recursos el array de letras dependiendo del valor de entrada.
		switch (numLetters){
			case 10:letters = res.getStringArray(R.array.first_row);
					break;
			case 9:letters = res.getStringArray(R.array.second_row);
					break;
			case 7:letters = res.getStringArray(R.array.third_row);
					break;			
		}
		 
		for (int j = 0; j < numLetters; j++) {
			//Inflamos el botón y le asignamos de texto la letra que corresponda del array. También agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			button.setText(letters[j]);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					paintLetter(view);
				}
			});
			tr.addView(keyView);
		}
		
		//Añadimos los márgenes entre filas
		params.setMargins(0, 10, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Devuelve una fila de botones de tamaño numLetters.
	 */	
	public TableRow loadNumbersRow(LayoutInflater inflater) {
		//Se inicializa el TableRow y se le asigna sus parametros.
		TableRow tr = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		
		for (int i = 1; i <= 10; i++) {
			//Inflamos el botón y le asignamos de texto el dígito que corresponda. También agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			
			//El botón 0 irá al final de la fila.
			if (i == 10) 
				button.setText(String.valueOf(0));
			else
				button.setText(String.valueOf(i));
			
			button.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View view) {
					paintLetter(view);
				}
			});
			tr.addView(keyView);
		}
		
		//Añadimos los márgenes entre filas
		params.setMargins(0, 20, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Construye la tabla de botones que muestra la respuesta.
	 */
	public void buildTableAnswer(LayoutInflater inflater, TableLayout tableAnswer) {
		String[] answerSplit = mAnswer.split(" ");
		String carBrand = answerSplit[0];
		String carModel = answerSplit[1];
		
		//Creamos dos filas, una para la marca y otra para el modelo
		TableRow brandRow = new TableRow(getActivity());
		TableRow modelRow = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 0, 0);
		brandRow.setLayoutParams(params);
		brandRow.setGravity(Gravity.CENTER);
		modelRow.setLayoutParams(params);
		modelRow.setGravity(Gravity.CENTER);
		
		for (int i = 0; i < carBrand.length(); i++) {
			//Inflamos el botón y lo dejamos vacio.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_solution, null);
			Button aButton = (Button)keyView.findViewById(R.id.button_solution);
			aButton.setTag(i);
			aButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					mLastPosition = (Integer)view.getTag();
					deleteLetter(view);
				}
			});
			brandRow.addView(keyView);
		}
		mTableAnswer.addView(brandRow);
		
		for (int i = carBrand.length(); i < carBrand.length() + carModel.length(); i++) {
			//Inflamos el botón y lo dejamos vacio.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_solution, null);
			Button aButton = (Button)keyView.findViewById(R.id.button_solution);
			aButton.setTag(i);
			aButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					mLastPosition = (Integer)view.getTag();
					deleteLetter(view);
				}
			});
			modelRow.addView(keyView);
		}
		mTableAnswer.addView(modelRow);
	}
	
	public void paintLetter(View view) {
		mVibrator.vibrate(10);
		
		String[] answerSplit = mAnswer.split(" ");
		String carBrand = answerSplit[0];
		String carModel = answerSplit[1];
		
		String sKey = ((Button)view).getText().toString();
		if (mLastPosition < (carBrand.length() + carModel.length())) {
			String s = mArrayAnswer[mLastPosition];
			while ((s != null) && !s.equals("")) {
				mLastPosition++;
				s = mArrayAnswer[mLastPosition];
			}			
		}

		
		if (mLastPosition < carBrand.length()) {
			FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswer.getChildAt(0)).getChildAt(mLastPosition);
			Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
			aButton.setText(sKey);
			mArrayAnswer[mLastPosition] = sKey;
			mLastPosition++;
		} else if (mLastPosition < (carBrand.length() + carModel.length())){
			FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswer.getChildAt(1)).getChildAt(mLastPosition - carBrand.length());
			Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
			aButton.setText(sKey);
			mArrayAnswer[mLastPosition] = sKey;
			mLastPosition++;
		}
	}
	
	public void deleteLetter(View view) {
		Button aButton = (Button)view;
		aButton.setText("");
		mArrayAnswer[mLastPosition] = "";
		mLastPosition = 0;
	}
	
	/**
	 * Crea una nueva instancia del fragment.
	 */
	public static Fragment newInstance(String answer) {
		Bundle arg = new Bundle();
		arg.putString(EXTRA_ANSWER, answer);
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}