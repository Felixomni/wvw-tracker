package com.felixware.wvwtracker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ObjectiveInfo {
	public long id;
	public int score;
	public String map;
	public String type;
	public String name;

	public ObjectiveInfo() {
	}

	public ObjectiveInfo(long id, int score, String map, String type, String name) {
		this.id = id;
		this.score = score;
		this.map = map;
		this.type = type;
		this.name = name;
	}

	public static ObjectiveInfo getObjectiveInfoFromJson(JSONObject jsonObject) {
		ObjectiveInfo info = new ObjectiveInfo();
		try {
			info.id = jsonObject.getLong("objective_id");
			info.score = jsonObject.getInt("score");
			info.map = jsonObject.getString("map");
			info.type = jsonObject.getString("type");
			info.name = jsonObject.getString("name_en");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return info;
	}

}
