package com.android.worldcarquiz.fragment;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.worldcarquiz.R;
import com.android.worldcarquiz.activity.QuestionActivitySolved;
import com.android.worldcarquiz.data.WorldCarQuizLab;

public class QuestionAnswerFragment extends Fragment {
	/**
	 * Constantes para pasar parámetros al fragment.
	 */
	private static final String EXTRA_WORLD = "extra_world";
	private static final String EXTRA_SUBWORLD = "extra_subworld";
	private static final String EXTRA_QUESTION = "extra_question";
	
	/**
	 * Tablas donde estan las letras(RelativeLayout) del teclado y las de la respuesta.
	 */
	private TableLayout mKeyBoard;
	private TableLayout mTableAnswerBrand;
	private TableLayout mTableAnswerModel;
	
	private Vibrator mVibrator;
	
	/**
	 * mCarBrand: String con la marca del coche para crear la primera fila en la tabla mTableAnswer.
	 * mCarModel: String con la marca del coche para crear la segunda fila en la tabla mTableAnswer.
	 * mArrayAnswer: Array de Strings donde se guarda las letras introducidas por el jugador y que que se utiliza para validar la respuesta.
	 * mLastPosition: Posición donde debemos introducir la nueva letra.
	 */
	private String mCarBrand;
	private String mCarModel;
	private String[] mArrayAnswer;
	private int mLastPosition;
	
	private int mNumWorld;
	private int mNumSubWorld;
	private int mNumQuestion;
	
	private Handler mHandler = new Handler();
	
	/**
	 * mQueueInfo: Cola que se utilizada para almacenar las letras a pintar y su posición en orden cuando pulsamos otra tecla antes de que acabe su animación
	 */
	private LinkedList<QueueInfo> mQueueInfo;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Obtenemos los parámetros básicos de la pregunta.
		mNumWorld = getArguments().getInt(EXTRA_WORLD);
		mNumSubWorld = getArguments().getInt(EXTRA_SUBWORLD);
		mNumQuestion = getArguments().getInt(EXTRA_QUESTION);
		
		//Obtenemos el string que contiene la respuesta de la pregunta y la divimos en 2, marca y modelo.
		mCarBrand = WorldCarQuizLab.get(getActivity()).getQuestionBrand(mNumWorld, mNumSubWorld, mNumQuestion).replace(" ", "");
		mCarModel = WorldCarQuizLab.get(getActivity()).getQuestionModel(mNumWorld, mNumSubWorld, mNumQuestion);
		
		//Inicializamos el array mArrayAnswer con la longitud de la respuesta y el resto por defecto.
		mArrayAnswer = new String[mCarBrand.length() + mCarModel.length()];
		mVibrator = ((Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE));
		mLastPosition = 0;
		mQueueInfo = new LinkedList<QueueInfo>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//Inflamos el layout del fragment que contiene la matricula y cargamos la tabla respuesta.
		View v = inflater.inflate(R.layout.fragment_question_answer, null);
		mTableAnswerBrand = (TableLayout) v.findViewById(R.id.tableAnswerBrand);
		mTableAnswerModel = (TableLayout) v.findViewById(R.id.tableAnswerModel);
		buildTableAnswer(inflater, mTableAnswerBrand, mTableAnswerModel);
		
		//Obtenemos la tabla del teclado para ir creando sus filas.
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
			//Inflamos el botón y le asignamos de texto la letra que corresponda del array. También agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			
			button.setText(letters[j]);
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					pressKeyButton((Button)view);
				}
			});
			tr.addView(keyView);
		}
		
		//Añadimos los márgenes entre filas
		params.setMargins(0, 5, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Devuelve una fila de botones de tamaño numLetters.
	 */	
	public TableRow loadNumbersRow(LayoutInflater inflater) {
		//Se inicializa el TableRow y se le asigna sus parametros.
		TableRow tr = new TableRow(getActivity());
		tr.setClipChildren(false);
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		
		for (int i = 1; i <= 10; i++) {
			//Inflamos el botón y le asignamos de texto el dígito que corresponda. También agregamos su listener.
			View keyView = inflater.inflate(R.layout.fragment_question_answer_key, null);
			Button button = (Button)keyView.findViewById(R.id.key_button);
			
			//El botón 0 irá al final de la fila.
			if (i == 10) {
				button.setText(String.valueOf(0));
			}		
			else {
				button.setText(String.valueOf(i));	
			}	
			
			button.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View view) {
					pressKeyButton((Button)view);
				}
			});
			tr.addView(keyView);
		}
		
		//Añadimos los márgenes entre filas
		params.setMargins(0, 5, 0, 0);
		tr.setLayoutParams(params);
		return tr;
	}
	
	/**
	 * Construye la tabla de botones que muestra la respuesta.
	 */
	public void buildTableAnswer(LayoutInflater inflater, TableLayout tableAnswerBrand, TableLayout tableAnswerModel) {
		//Creamos dos filas, una para la marca y otra para el modelo.
		TableRow brandRow = new TableRow(getActivity());
		TableRow modelRow = new TableRow(getActivity());
		
		//Le asignamos sus parametros iniciales.
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 1, 0, 0);
		brandRow.setLayoutParams(params);
		brandRow.setGravity(Gravity.CENTER);
		modelRow.setLayoutParams(params);
		modelRow.setGravity(Gravity.CENTER);
		
		//Fila de la marca del coche.
		String originalBrand = WorldCarQuizLab.get(getActivity()).getQuestionBrand(mNumWorld, mNumSubWorld, mNumQuestion);
		String originalModel = WorldCarQuizLab.get(getActivity()).getQuestionModel(mNumWorld, mNumSubWorld, mNumQuestion);
		boolean space = false;
		int cont = 0;
		for (int i = 0; i < originalBrand.length(); i++) {
			if (originalBrand.charAt(i) != ' '){
				View keyView;
				Button aButton;
				if (space) {
					//Inflamos el botón y lo dejamos vacio.
					keyView = inflater.inflate(R.layout.fragment_question_answer_solution_space, null);
					aButton = (Button)keyView.findViewById(R.id.button_solution_space);
					space = false;
					//El Tag del boton será la posición que ocupe y se usará para saber que boton hemos pulsado y borrar su contenido.
				} else {
					keyView = inflater.inflate(R.layout.fragment_question_answer_solution, null);
					aButton = (Button)keyView.findViewById(R.id.button_solution);
				}
				aButton.setTag(cont);
				aButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						//Al borrar la letra la nueva posición es la que se queda vacía.
						mLastPosition = (Integer)view.getTag();
						deleteLetter((Button)view);
					}
				});
				brandRow.addView(keyView);
				cont++;
			} else {
				space = true;
			}
		}
		mTableAnswerBrand.addView(brandRow);
		
		cont = mCarBrand.length();
		space = false;
		//Fila del modelo del coche.
		//for (int i = mCarBrand.length(); i < mArrayAnswer.length; i++) {
		for (int i = 0; i < originalModel.length(); i++) {
			if (originalModel.charAt(i) != ' '){
				View keyView;
				Button aButton;
				if (space) {
					//Inflamos el botón y lo dejamos vacio.
					keyView = inflater.inflate(R.layout.fragment_question_answer_solution_space, null);
					aButton = (Button)keyView.findViewById(R.id.button_solution_space);
					//El Tag del boton será la posición que ocupe y se usará para saber que boton hemos pulsado y borrar su contenido.
					space = false;
				} else {
					keyView = inflater.inflate(R.layout.fragment_question_answer_solution, null);
					aButton = (Button)keyView.findViewById(R.id.button_solution);
				}
				aButton.setTag(cont);
				aButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						//Al borrar la letra la nueva posición es la que se queda vacía.
						mLastPosition = (Integer)view.getTag();
						deleteLetter((Button)view);
					}
				});
				modelRow.addView(keyView);
				cont++;
			} else {
				space = true;
			}
		}
		mTableAnswerModel.addView(modelRow);
	}		
	
	/**
	 * Método que crea una copia del botón que se ha pulsado y lo traslada hacia su destino, le pasamos de parámetro el botón original.
	 */
	public void pressKeyButton(Button originalButton) {
		mVibrator.vibrate(10);
		//Calculamos primero la posición donde tiene que ir la animación. En caso de estar la respuesta completa no se hace nada.
		int newPosition = newPosition();
		if (newPosition != -1) {
			//Obtenemos el padre del botón original
			RelativeLayout parent = (RelativeLayout)originalButton.getParent();
			
			View keyView = getActivity().getLayoutInflater().inflate(R.layout.fragment_question_answer_solution, null);
			Button aButton = (Button)keyView.findViewById(R.id.button_solution);

			//Creamos un nuevo botón y le asignamos todos los atributos del original.
		    Button copyButton = new Button(getActivity());
		    copyButton.setText(originalButton.getText());
		    copyButton.setPadding(aButton.getPaddingLeft(), aButton.getPaddingTop(),
		    		aButton.getPaddingRight(), aButton.getPaddingBottom());
		    copyButton.setLayoutParams(aButton.getLayoutParams());
		    copyButton.setTextColor(Color.parseColor("#065a9d"));
		    copyButton.setBackgroundColor(Color.TRANSPARENT);
		    
		    //Añadimos el boton al Layout y lo dejamos en segundo plano.
		    parent.addView(copyButton);
		    originalButton.bringToFront();
		    originalButton.setFocusable(true);
		    
		    /*Rellenamos en la posicion newPosition en el array mArrayAnswer la letra que corresponda.
		     *Añadimos a la lista la información (posicion y letra) para que se pinten en orden despues de que la animación acabe.
		     *Llamamos al método que realiza la animación.*/
		    mArrayAnswer[newPosition] = originalButton.getText().toString();
		    mQueueInfo.addFirst(new QueueInfo(newPosition, originalButton.getText().toString()));
			moveView(copyButton, newPosition);
		}
	}
	
	/**
	 * Método que pinta la letra en la posición indicada. Dicha información (letra y posición) se encuentran en el parámetro info. 
	 */
	public void paintLetter(QueueInfo info) {
		//Si la posición actual es inferior al nombre de la marca hay que pintar en la primera fila, sino en la segunda.
		if (info.getPosition() < mArrayAnswer.length) {
			if (info.getPosition() < mCarBrand.length()) {
				//Obtenemos de la tabla el layout donde está el boton, en la fila 0, columna info.getPosition().
				FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswerBrand.getChildAt(0)).getChildAt(info.getPosition());
				Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
				if (aButton == null) {
					aButton = (Button)layoutButton.findViewById(R.id.button_solution_space);
				}
				aButton.setText(info.getLetter());
			} else if (info.getPosition() < mArrayAnswer.length){
				//Obtenemos de la tabla el layout donde está el boton, en la fila 1, columna info.getPosition() - longitud de la marca del coche.
				FrameLayout layoutButton = (FrameLayout)((TableRow)mTableAnswerModel.getChildAt(0)).getChildAt(info.getPosition() - mCarBrand.length());
				Button aButton = (Button)layoutButton.findViewById(R.id.button_solution);
				if (aButton == null) {
					aButton = (Button)layoutButton.findViewById(R.id.button_solution_space);
				}
				aButton.setText(info.getLetter());
			}			
		}
		
		//Comprobamos si la respuesta es correcta.
		checkAnswer();
	}
	
	/**
	 * Método que traslada la vista view a la posición newPosition de la tabla respuesta.
	 */
	private void moveView(View view, int newPosition) {
		//Calculamos la posición original del objeto.
	    int originalPos[] = new int[2];
	    view.getLocationOnScreen(originalPos);
	    
	    //Calculamos la posición destino del objeto.
	    int destinyPos[] = new int[2];
	    Button dButton;
	    FrameLayout layoutButton;
	    //Si la posición es inferior al nombre de la marca, deberá ir a la primera fila, sino a la segunda.
	    if (newPosition < mCarBrand.length()) {
	    	//Obtenemos de la tabla el botón al igual que en el método paintLetter.
			layoutButton = (FrameLayout)((TableRow)mTableAnswerBrand.getChildAt(0)).getChildAt(newPosition);
			dButton = (Button)layoutButton.findViewById(R.id.button_solution);
			if (dButton == null) {
				dButton = (Button)layoutButton.findViewById(R.id.button_solution_space);
			}
	    } else {
	    	//Obtenemos de la tabla el botón al igual que en el método paintLetter.	    	
			layoutButton = (FrameLayout)((TableRow)mTableAnswerModel.getChildAt(0)).getChildAt(newPosition - mCarBrand.length());
			dButton = (Button)layoutButton.findViewById(R.id.button_solution);
			if (dButton == null) {
				dButton = (Button)layoutButton.findViewById(R.id.button_solution_space);
			}
	    }
	    
	    //Con el botón destino recuperado obtenemos su posición.
	    dButton.getLocationOnScreen(destinyPos);

	    //Creamos la animación, su duración y hacemos que llame al método paintLetter en cuanto finalice.
	    TranslateAnimation anim = new TranslateAnimation( 0, destinyPos[0] - originalPos[0] , 0, destinyPos[1] - originalPos[1] );
	    anim.setDuration(1200);
	    anim.getFillBefore();
	    anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//Llamamos al método paintLetter obteniendo el primero de los objetos de la cola que hay que pintar
				paintLetter(mQueueInfo.removeLast());		
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
			}
		});
	    
	    //Empieza la animación.
	    view.startAnimation(anim);
	}
	
	/**
	 * Método que calcula la primera posición vacía del array mArrayAnswer y donde dberemos pintar la nueva letra.
	 */
	public int newPosition() {	
		int position = mLastPosition;
		//Mientras sea distinto de null y no nos salgamos de rango avanzamos.
		while ((mLastPosition < mArrayAnswer.length) && (mArrayAnswer[mLastPosition] != null)) {
			mLastPosition++;
		}
		
		position = mLastPosition;
		
		//Si no nos hemos salido de rango devolvemos la nueva posición.
		if (mLastPosition >= mArrayAnswer.length) 
			return -1;
		else 
			return position;
	}
	
	/**
	 * Método que borra el contenido de un boton de la respuesta.
	 */
	public void deleteLetter(Button button) {
		//Elimina el texto del botón, asigna null a su posición del array y reiniciamos el contador de última posicion.
		button.setText("");
		mArrayAnswer[mLastPosition] = null;
		mLastPosition = 0;
	}
	
	/**
	 * Método que comprueba si la respuesta introducida es la correcta.
	 */
	public void checkAnswer() {
		//Creamos el string con la respuesta.
		String answer = mCarBrand + mCarModel;
		
		//Creamos el string con la respuesta introducida.
		String actualAnswer = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < mArrayAnswer.length; i++) {
			buffer.append( mArrayAnswer[i] );
		}
		
		//Como en la respuesta están en mayúsculas hay que pasarlas a minúscula.
		actualAnswer = buffer.toString().toLowerCase(getResources().getConfiguration().locale);
		
		//Si son iguales marcamos la respuesta como correcta y lanzamos la transacción que muestra el fragment de acierto.
		if (answer.equals(actualAnswer)) {
			WorldCarQuizLab.get(getActivity())
			.setQuestionAnswered(mNumWorld, mNumSubWorld, mNumQuestion, mNumQuestion + 1);
			
			mHandler.postDelayed(new Runnable() {
	            public void run() {
	    			//si acertó lanzo la activity 
	    			Intent i = new Intent(getActivity(), QuestionActivitySolved.class);
				    i.putExtra(SubWorldFragment.EXTRA_NUM_WORLD, mNumWorld);
				    i.putExtra(SubWorldFragment.EXTRA_NUM_SUBWORLD, mNumSubWorld);
				    i.putExtra(QuestionPhotoFragment.EXTRA_NUM_QUESTION, mNumQuestion);
	    			startActivity(i);
	            }
	        }, 500);

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
	
	/**
	 * Clase privada que guarda la información donde se ha de pintar las letras (posición y texto de la letra). Se inserta en la cola mQueueInfo.
	 */
	private class QueueInfo {
		private int mPosition;
		private String mLetter;
		
		public QueueInfo(int position, String letter) {
			mPosition = position;
			mLetter = letter;
		}
		
		public int getPosition() {
			return mPosition;
		}
		
		public String getLetter() {
			return mLetter;
		}
		
	}
}