package com.felixware.wvwtracker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MatchDetails {
	public String id;
	public Score totalScore;
	public Map redHome;
	public Map blueHome;
	public Map greenHome;
	public Map center;

	public MatchDetails() {
	}

	public MatchDetails(String id, Score totalScore, Map redHome, Map blueHome, Map greenHome, Map center) {
		this.id = id;
		this.totalScore = totalScore;
		this.redHome = redHome;
		this.blueHome = blueHome;
		this.greenHome = greenHome;
		this.center = center;
	}

	public static MatchDetails getMatchDetailsFromJson(JSONObject jsonObject) {
		MatchDetails matchDetails = new MatchDetails();
		try {
			matchDetails.id = jsonObject.getString("match_id");
			matchDetails.totalScore = Score.getScoreFromJSON(jsonObject.getJSONArray("scores"));
			// TODO maps
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return matchDetails;
	}

}
