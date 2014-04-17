package com.android.worldcarquiz.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionAnswerFragment extends Fragment {
	private static final String EXTRA_WORLD = "extra_world";
	private static final String EXTRA_SUBWORLD = "extra_subworld";
	private static final String EXTRA_QUESTION = "extra_question";
	
	private TableLayout mKeyBoard;
	private TableLayout mTableAnswer;
	
	private Vibrator mVibrator;
	
	private String mCarBrand;
	private String mCarModel;
	private String[] mArrayAnswer;
	private int mLastPosition;
	
	private int mNumWorld;
	private int mNumSubWorld;
	private int mNumQuestion;
	
	private View mView;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNumWorld = getArguments().getInt(EXTRA_WORLD);
		mNumSubWorld = getArguments().getInt(EXTRA_SUBWORLD);
		mNumQuestion = getArguments().getInt(EXTRA_QUESTION);
		String answer = WorldCarQuizLab.get(getActivity()).getQuestionAnswer(mNumWorld, mNumSubWorld, mNumQuestion);
		String[] answerSplit = answer.split(" ");
		mCarBrand = answerSplit[0];
		mCarModel = answerSplit[1];
		
		mArrayAnswer = new String[mCarBrand.length() + mCarModel.length()];
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
		
		/*Creamos 4 filas y las a�adimos al TableLayout del fragment. Cada fila tiene diferente numero de letras,
		 *por eso se le llama con distintos par�metros. La �ltima fila es fija, ya que son los d�gitos.*/
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
		//A�adimos el gravity centrado e insertamos en el TableLayout.
		tr.setGravity(Gravity.CENTER);
		mKeyBoard.addView(tr);
		}
		
		return v;
	}
	
	/**
	 * Devuelve una fila de botones de tama�o numLetters.
	 */
	public TableRow loadLettersRow(LayoutInflater inflater, int numLetters) {
		//Se inicializa el TableRow y se le asigna sus parametros.
		TableRow tr = new TableRow(getActivity());
		tr.setClipChildren(false);
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
			//Inflamos el bot�n y le asignamos de texto la letra que corresponda del array. Tambi�n agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			Button buttonCopy = (Button)keyView.findViewById(R.id.key_button_copy);
			
			button.setText(letters[j]);
			buttonCopy.setText(letters[j]);
			buttonCopy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					mView = view;
			        moveViewToScreenCenter(view);
					checkAnswer();
				}
			});
			tr.addView(keyView);
		}
		
		//A�adimos los m�rgenes entre filas
		params.setMargins(0, 5, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Devuelve una fila de botones de tama�o numLetters.
	 */	
	public TableRow loadNumbersRow(LayoutInflater inflater) {
		//Se inicializa el TableRow y se le asigna sus parametros.
		TableRow tr = new TableRow(getActivity());
		tr.setClipChildren(false);
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		
		for (int i = 1; i <= 10; i++) {
			//Inflamos el bot�n y le asignamos de texto el d�gito que corresponda. Tambi�n agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			Button buttonCopy = (Button)keyView.findViewById(R.id.key_button_copy);
			
			//El bot�n 0 ir� al final de la fila.
			if (i == 10) {
				button.setText(String.valueOf(0));
				buttonCopy.setText(String.valueOf(0));
			}		
			else {
				button.setText(String.valueOf(i));
				buttonCopy.setText(String.valueOf(i));	
			}	
			
			buttonCopy.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View view) {
					mView = view;
					moveViewToScreenCenter(view);
					checkAnswer();
				}
			});
			tr.addView(keyView);
		}
		
		//A�adimos los m�rgenes entre filas
		params.setMargins(0, 5, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Construye la tabla de botones que muestra la respuesta.
	 */
	public void buildTableAnswer(LayoutInflater inflater, TableLayout tableAnswer) {
		//Creamos dos filas, una para la marca y otra para el modelo
		TableRow brandRow = new TableRow(getActivity());
		TableRow modelRow = new TableRow(getActivity());
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0,1, 0, 0);
		brandRow.setLayoutParams(params);
		brandRow.setGravity(Gravity.CENTER);
		brandRow.setPadding(0, 28, 0, 0);
		modelRow.setLayoutParams(params);
		modelRow.setGravity(Gravity.CENTER);
		
		for (int i = 0; i < mCarBrand.length(); i++) {
			//Inflamos el bot�n y lo dejamos vacio.
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
		
		for (int i = mCarBrand.length(); i < mArrayAnswer.length; i++) {
			//Inflamos el bot�n y lo dejamos vacio.
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
		
		String sKey = ((Button)view).getText().toString();
		           
		if (mLastPosition < mArrayAnswer.length) {
			if (mLastPosition < mCarBrand.length()) {
				FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswer.getChildAt(0)).getChildAt(mLastPosition);
				Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
				aButton.setText(sKey);
				mArrayAnswer[mLastPosition] = sKey;
				setNewposition();
			} else if (mLastPosition < mArrayAnswer.length){
				FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswer.getChildAt(1)).getChildAt(mLastPosition - mCarBrand.length());
				Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
				aButton.setText(sKey);
				mArrayAnswer[mLastPosition] = sKey;
				setNewposition();
			}			
		}
	}
	
	private void moveViewToScreenCenter(View view)
	{
	    LinearLayout root = (LinearLayout) getActivity().findViewById( R.id.container_question );
	    DisplayMetrics dm = new DisplayMetrics();
	    getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	    int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

	    int originalPos[] = new int[2];
	    view.getLocationOnScreen( originalPos );

	    int xDest = dm.widthPixels/2;
	    xDest -= (view.getMeasuredWidth()/2);
	    int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

	    TranslateAnimation anim = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
	    anim.setDuration(1000);
	    anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				paintLetter(mView);				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
			}
		});
	    view.startAnimation(anim);
	}
	
	public void setNewposition() {	
		while ((mLastPosition < mArrayAnswer.length) && (mArrayAnswer[mLastPosition] != null) && !mArrayAnswer[mLastPosition].equals("")) {
			mLastPosition++;
		}
	}
	
	public void deleteLetter(View view) {
		Button aButton = (Button)view;
		aButton.setText("");
		mArrayAnswer[mLastPosition] = "";
		mLastPosition = 0;
		setNewposition();
	}
	
	public void checkAnswer() {
		String answer = mCarBrand + mCarModel;
		String actualAnswer = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < mArrayAnswer.length; i++) {
			buffer.append( mArrayAnswer[i] );
		}
		actualAnswer = buffer.toString().toLowerCase(getResources().getConfiguration().locale);
		
		if (answer.equals(actualAnswer)) {
			WorldCarQuizLab.get(getActivity())
			.setQuestionAnswered(mNumWorld, mNumSubWorld, mNumQuestion, mNumQuestion + 1);
		
			FragmentManager fm = getActivity().getSupportFragmentManager();
			Fragment fragment = new QuestionSolvedFragment();
			fm.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
				.replace(R.id.fragment_answer, fragment).commit();
		}
	}
	
	/**
	 * Crea una nueva instancia del fragment.
	 */
	public static Fragment newInstance(int numWorld, int numSubWorld, int numQuestion) {
		Bundle arg = new Bundle();
		arg.putInt(EXTRA_WORLD, numWorld);
		arg.putInt(EXTRA_SUBWORLD, numSubWorld);
		arg.putInt(EXTRA_QUESTION, numQuestion);
		QuestionAnswerFragment fragment = new QuestionAnswerFragment();
		fragment.setArguments(arg);
		
		return fragment;
	}
}