package com.felixware.wvwtracker.models;

import org.json.JSONArray;
import org.json.JSONException;

public class Score {
	public int redScore;
	public int blueScore;
	public int greenScore;

	public Score() {
	}

	public Score(int redScore, int blueScore, int greenScore) {
		this.redScore = redScore;
		this.blueScore = blueScore;
		this.greenScore = greenScore;
	}

	public static Score getScoreFromJSON(JSONArray jsonArray) {
		Score score = new Score();
		try {
			if (jsonArray.length() == 3) {
				score.redScore = jsonArray.getInt(0);
				score.blueScore = jsonArray.getInt(1);
				score.greenScore = jsonArray.getInt(2);
			} else {
				throw new JSONException("JSONArray was incorrect length");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return score;
	}
}
