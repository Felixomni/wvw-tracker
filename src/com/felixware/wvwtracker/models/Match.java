package com.felixware.wvwtracker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Match {
	public String id;
	public long redWorldId;
	public long blueWorldId;
	public long greenWorldId;

	public Match() {
	}

	public Match(String id, long redWorldId, long blueWorldId, long greenWorldId) {
		this.id = id;
		this.redWorldId = redWorldId;
		this.blueWorldId = blueWorldId;
		this.greenWorldId = greenWorldId;
	}

	public static Match getMatchFromJSON(JSONObject jsonObject) {
		Match match = null;
		try {
			match = new Match(jsonObject.getString("wvw_match_id"), jsonObject.getLong("red_world_id"), jsonObject.getLong("blue_world_id"), jsonObject.getLong("green_world_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return match;
	}

}
