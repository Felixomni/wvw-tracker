package com.felixware.wvwtracker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felixware.wvwtracker.R;

public class TotalScoreView extends RelativeLayout {
	private TextView nameText, scoreText;
	private View scoreBar;

	public TotalScoreView(Context context) {
		this(context, null);
	}

	public TotalScoreView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TotalScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.total_score_view, this);

		nameText = (TextView) findViewById(R.id.scoreName);
		scoreText = (TextView) findViewById(R.id.score);
		scoreBar = (View) findViewById(R.id.scoreBar);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TotalScoreView);
		scoreBar.setBackgroundColor(a.getColor(R.styleable.TotalScoreView_scoreBackground, R.color.transparent));
		a.recycle();
	}

	public void setName(String name) {
		nameText.setText(name);
	}

	public String getName() {
		return nameText.getText().toString();
	}

	public void setScore(int score) {
		scoreText.setText(Integer.toString(score));
	}

	public void setScoreBarHeight(int height) {
		LayoutParams params = (LayoutParams) scoreBar.getLayoutParams();
		params.height = height;
		scoreBar.setLayoutParams(params);
	}

	public int getScore() {
		return Integer.parseInt(scoreText.getText().toString());
	}
}
