package com.android.worldcarquiz.data;

public class Question {
	private int mId;
	private int mLocked;
	private int mPoints;
	private int mTrys;
	private int mImage_id;
	
	public Question(int id, int locked, int trys, int image_id) {
		mId = id;
		mLocked = locked;
		mTrys = trys;
		mImage_id = image_id;
	}

	public int getPoints() {
		return mPoints;
	}

	public void setPoints(int points) {
		mPoints = points;
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
	
	public boolean isAnswered() {
		return mLocked == 2;
	}

	public boolean isUnLocked() {
		return mLocked == 0;
	}

	public void answered() {
		mLocked = 2;
	}
	
	public int getId() {
		return mId;
	}
	
	public int getImageId() {
		return mImage_id;
	}
}
