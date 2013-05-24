package com.felixware.wvwtracker.models;

import org.json.JSONArray;
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
			JSONArray mapsArray = jsonObject.getJSONArray("maps");
			for (int i = 0; i < mapsArray.length(); i++) {
				JSONObject map = mapsArray.getJSONObject(i);
				if (map.getString("type").equals("RedHome")) {
					matchDetails.redHome = Map.getMapFromJson(map);
				} else if (map.getString("type").equals("BlueHome")) {
					matchDetails.blueHome = Map.getMapFromJson(map);
				} else if (map.getString("type").equals("GreenHome")) {
					matchDetails.greenHome = Map.getMapFromJson(map);
				} else if (map.getString("type").equals("Center")) {
					matchDetails.center = Map.getMapFromJson(map);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return matchDetails;
	}

}
