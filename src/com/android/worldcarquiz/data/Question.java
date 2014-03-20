package com.android.worldcarquiz.data;

public class Question {
	private boolean mSolved;
	private int mValor;
	
	public Question(int valor, boolean solved) {
		mSolved = solved;
		mValor = valor;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public int getValor() {
		return mValor;
	}

	public void setValor(int valor) {
		mValor = valor;
	}
}
