package com.felixware.wvwtracker.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Map {
	public String type;
	public Score score;
	public List<Objective> objectives;

	public Map() {
	}

	public Map(String type, Score score, List<Objective> objectives) {
		this.type = type;
		this.score = score;
		this.objectives = objectives;
	}

	public static Map getMapFromJson(JSONObject jsonObject) {
		Map map = new Map();
		try {
			map.type = jsonObject.getString("type");
			map.score = Score.getScoreFromJSON(jsonObject.getJSONArray("scores"));
			JSONArray objectivesArray = jsonObject.getJSONArray("objectives");
			map.objectives = new ArrayList<Objective>();
			for (int i = 0; i < objectivesArray.length(); i++) {
				map.objectives.add(Objective.getObjectiveFromJson(objectivesArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return map;
	}

}
