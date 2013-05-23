package com.felixware.wvwtracker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Objective {
	public long id;
	public String owner;
	public String ownerGuild;
	public ObjectiveType type;

	public Objective() {
	}

	public Objective(long id, String owner, String ownerGuild) {
		this.id = id;
		this.owner = owner;
		this.ownerGuild = ownerGuild;
		// TODO get type from id
	}

	public Objective(long id, String owner, String ownerGuild, ObjectiveType type) {
		this.id = id;
		this.owner = owner;
		this.ownerGuild = ownerGuild;
		this.type = type;
	}

	public static Objective getObjectiveFromJson(JSONObject jsonObject) {
		Objective objective = new Objective();

		try {
			objective.id = jsonObject.getLong("id");
			objective.owner = jsonObject.getString("owner");
			if (jsonObject.has("owner_guild")) {
				objective.ownerGuild = jsonObject.getString("owner_guild");
			}

			// TODO get type from id
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return objective;
	}

	public static enum ObjectiveType {
		CAMP("camp", 5), TOWER("tower", 10), KEEP("keep", 25), CASTLE("castle", 35);

		private String name;
		private int value;

		ObjectiveType(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

	}

}
