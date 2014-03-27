package com.android.worldcarquiz.data;

public class Question {
	private boolean mSolved;
	private int mValor;
	private int mFinalValor;
	private int mTrys;
	
	public Question(int valor) {
		mSolved = false;
		mValor = valor;
		mFinalValor = 0;
		mTrys = 0;
	}

	public int getFinalValor() {
		return mFinalValor;
	}

	public void setFinalValor(int mFinalValor) {
		this.mFinalValor = mFinalValor;
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

	public int getTrys() {
		return mTrys;
	}

	public void setTrys(int trys) {
		mTrys = trys;
	}

}
