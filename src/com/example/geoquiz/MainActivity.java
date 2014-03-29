package com.example.geoquiz;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPrevButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	private Boolean mCheatResult;
	
	private enum Direction {
		NEXT(1),PREVIOUS(-1),HOME(0);
		private int value;
		
		Direction(int val){
			this.value = val;
		}
	};
	private enum ChildWindows {
		CHEAT(0),HELP(1);
		private int value;
		
		ChildWindows(int val){
			this.value = val;
		}
	}
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_bad_religion, false),
			new TrueFalse(R.string.question_karl, true),
			new TrueFalse(R.string.question_toto, true),
			new TrueFalse(R.string.question_umphreys, false)
	};
	private int mCurrentIndex = 0;
	
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar actionBar = getActionBar();
			actionBar.setSubtitle("DEVIOUS QUIZ");	
		}
		
		if(savedInstanceState != null && savedInstanceState.containsKey(KEY_INDEX)){
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
			if(savedInstanceState.containsKey(CheatActivity.EXTRA_ANSWER_SELECTED)){
				mCheatResult = savedInstanceState.getBoolean(CheatActivity.EXTRA_ANSWER_SELECTED);
			}
		}
		setContentView(R.layout.activity_main);
		
		
		
		mTrueButton = (Button)findViewById(R.id.true_button);
		mFalseButton = (Button)findViewById(R.id.false_button);
		mNextButton = (Button)findViewById(R.id.next_q);
		mPrevButton = (Button)findViewById(R.id.prev_q);
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

		changeQuestion(mQuestionTextView, Direction.NEXT);
		
		mTrueButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
				
			}
		});
		
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeQuestion(mQuestionTextView, Direction.NEXT);
			}
		});
		
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeQuestion(mQuestionTextView, Direction.PREVIOUS);
			}
		});
		
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CheatActivity.class);
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].isTrueQuestion());
				startActivityForResult(i, ChildWindows.CHEAT.value);
			}
		});
		
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeQuestion(mQuestionTextView, Direction.NEXT);
				
			}
		});
		
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i(TAG, "IN THE SAVE INSTANCE METHOD");
		outState.putInt(KEY_INDEX, mCurrentIndex);
		//save if they are a cheater
		if(mCheatResult != null){
			outState.putBoolean(CheatActivity.EXTRA_ANSWER_SELECTED, mCheatResult);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	



	private void changeQuestion(TextView mQuestionView, Direction direction){

		int num = (mCurrentIndex + direction.value);
		mCurrentIndex =  num % mQuestionBank.length;
//		Log.d(TAG, Integer.toString(mCurrentIndex), new Exception());
		
		mCurrentIndex = mCurrentIndex < 0 ? mQuestionBank.length - 1 : mCurrentIndex;
		try{
			mQuestionView.setText(mQuestionBank[mCurrentIndex].getQuestion());	
		} catch (ArrayIndexOutOfBoundsException e){
			Log.e(TAG, "Index was out of bounds for question", e);
		}

	}

	private void checkAnswer(boolean userAnswer){

		String retMessage;
		
		if(userAnswer == mQuestionBank[mCurrentIndex].isTrueQuestion()){
			retMessage = getResources().getString(R.string.correct_answer_text);
		} else {
			retMessage = getResources().getString(R.string.incorrect_answer_text);
		}
		
		if(mQuestionBank[mCurrentIndex].isCheater()){
			retMessage += ", " + getResources().getString(R.string.cheat_answer_text);
		}
		
		Toast.makeText(MainActivity.this, retMessage + "!", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(data == null){
			return;
		}
		
		mCheatResult = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SELECTED, false);
		mQuestionBank[mCurrentIndex].setCheater();
	}
	
	
}
