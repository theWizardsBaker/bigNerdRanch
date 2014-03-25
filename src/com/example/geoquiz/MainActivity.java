package com.example.geoquiz;

import android.os.Bundle;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null && savedInstanceState.containsKey(KEY_INDEX)){
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
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
		if(userAnswer == mQuestionBank[mCurrentIndex].isTrueQuestion()){
			Toast.makeText(MainActivity.this,"Nice one!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MainActivity.this,"No way!", Toast.LENGTH_SHORT).show();

		}
	}
}
