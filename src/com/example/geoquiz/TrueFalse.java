package com.example.geoquiz;

public class TrueFalse {
	private int mQuestion;
	
	private boolean mTrueQuestion;
	
	private boolean mCheated = false;
	
	public TrueFalse(int question, boolean trueQuestion){
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}

	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int question) {
		this.mQuestion = question;
	}
	
	public void setCheater(){
		mCheated = true;
	}
	
	public boolean isCheater(){
		return mCheated;
	}

	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	public void setTrueQuestion(boolean trueQuestion) {
		this.mTrueQuestion = trueQuestion;
	}
	
}
