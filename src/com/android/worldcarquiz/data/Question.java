package com.android.worldcarquiz.data;

/**
 * Clase correspondiente a cada pregunta del Quiz.
 *
 */
public class Question {
	/**
	 * Atributos privados de la clase: 
	 * -mId: id de la pregunta.
	 * -mStatus: estado de la pregunta. 0 = desbloqueada, 1 = bloqueada, 2 = respondida.
	 * -mValor: puntuación final de la pregunta.
	 * -mTrys: intentos de acierto sobre la pregunta.
	 * -mImageId: id del recurso del drawable de la imagen.
	 */
	//private int mId;
	private int mStatus;
	private int mScore;
	private int mTrys;
	private String mImage;
	private String mAnswer;

	
	/**
	 * CONSTRUCTOR
	 */
	public Question(int status, int trys, int score, String image, String answer) {
		//mId = id;
		mStatus = status;
		mScore = score;
		mTrys = trys;
		mImage = image;
		mAnswer = answer;
	}

	
	/**
	 * GETTERS Y SETTERS
	 */
	
	public String getAnswer() {
		return mAnswer;
	}
	
	public int getScore() {
		return mScore;
	}

	/**
	 * Método que marca la respuesta acertada y le asigna su puntuacion
	 */
	public void setAnswered(int score) {
		mScore = score;
		mStatus = 2;
		
		//LLamar al Lab pa modificar la base de datos
	}

	public int getTrys() {
		return mTrys;
	}

	public void setTrys(int trys) {
		mTrys = trys;
	}
	
	/*public int getId() {
		return mId;
	}
	*/
	public String getImage() {
		return mImage;
	}

	/**
	 * Devuelve true si la pregunta esta desbloqueda.
	 * @return
	 */
	public boolean isUnLocked() {
		return mStatus == 0;
	}
	
	/**
	 * Devuelve true si la pregunta esta bloqueada.
	 * @return
	 */
	public boolean isLocked() {
		return mStatus == 1;
	}
	
	/**
	 * Devuelve true si la pregunta se ha acertado.
	 * @return
	 */
	public boolean isAnswered() {
		return mStatus == 2;
	}

}
