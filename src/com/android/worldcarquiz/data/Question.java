package com.android.worldcarquiz.data;

public class Question {
	private int mLocked;
	private int mValor;
	private int mTrys;
	
	public Question(int locked, int trys) {
		mLocked = locked;
		mTrys = trys;
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

	public boolean isLocked() {
		return mLocked == 1;
	}

	public void setLocked() {
		mLocked = 0;
	}

}
