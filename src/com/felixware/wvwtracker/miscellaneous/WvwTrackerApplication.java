package com.felixware.wvwtracker.miscellaneous;

import java.util.HashMap;

import android.app.Application;
import android.util.Log;

public class WvwTrackerApplication extends Application {
	private HashMap<Long, String> worldNames = new HashMap<Long, String>();

	public HashMap<Long, String> getWorldNames() {
		return this.worldNames;
	}

	public void addWorldName(Long id, String name) {
		worldNames.put(id, name);
	}

	public void setWorldNames(HashMap<Long, String> worldNames) {
		this.worldNames = worldNames;
	}

	public String getWorldName(Long id) {
		if (worldNames.containsKey(id)) {
			return worldNames.get(id);
		} else {
			// TODO make sure world names are up-to-date
			Log.i("Application", "key not found");
			return null;
		}
	}

}
