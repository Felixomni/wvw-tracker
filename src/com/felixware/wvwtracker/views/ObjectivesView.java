package com.felixware.wvwtracker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felixware.wvwtracker.R;

public class ObjectivesView extends RelativeLayout {
	private TextView campsText, towersText, keepsText, castlesText, incomeText;
	private Context context;

	public ObjectivesView(Context context) {
		this(context, null);
	}

	public ObjectivesView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ObjectivesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.objectives_view, this);

		campsText = (TextView) findViewById(R.id.campsTotal);
		towersText = (TextView) findViewById(R.id.towersTotal);
		keepsText = (TextView) findViewById(R.id.keepsTotal);
		castlesText = (TextView) findViewById(R.id.castlesTotal);
		incomeText = (TextView) findViewById(R.id.totalIncome);
	}

	public void setText(int camps, int towers, int keeps, int castles, int income) {
		campsText.setText(Integer.toString(camps));
		towersText.setText(Integer.toString(towers));
		keepsText.setText(Integer.toString(keeps));
		castlesText.setText(Integer.toString(castles));
		incomeText.setText("+" + Integer.toString(income));
	}

}
