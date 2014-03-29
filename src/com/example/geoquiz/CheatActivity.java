package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	public static final String EXTRA_ANSWER_IS_TRUE = "com.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SELECTED = "com.android.geoquiz.answer_selected";
	private boolean mAnswerIsTrue;
	
	private TextView mCorrectAnswerTextView;
	private Button mShowAnswer;
	private TextView mVersion;
	
	private void setAnswerShownResult(boolean selected){
		Intent i = new Intent();
		i.putExtra(EXTRA_ANSWER_SELECTED, selected);
		setResult(RESULT_OK, i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, false);
		mCorrectAnswerTextView = (TextView)findViewById(R.id.correctAnswerTextView);
		mShowAnswer = (Button)findViewById(R.id.cheat_button);
		mVersion = (TextView)findViewById(R.id.APIReportTextView);
		
		mVersion.setText(android.os.Build.VERSION.SDK);
		
		
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAnswerIsTrue){
					mCorrectAnswerTextView.setText(R.string.true_button);
				} else {
					mCorrectAnswerTextView.setText(R.string.false_button);
				}
				
				setAnswerShownResult(true);
			}
		});
	}
	

}
