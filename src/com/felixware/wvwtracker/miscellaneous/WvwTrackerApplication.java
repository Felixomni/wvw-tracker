package com.felixware.wvwtracker.miscellaneous;

import java.util.HashMap;

import android.app.Application;
import android.util.Log;

import com.felixware.wvwtracker.models.ObjectiveInfo;

public class WvwTrackerApplication extends Application {
	private HashMap<Long, String> worldNamesMap = new HashMap<Long, String>();
	private HashMap<Long, ObjectiveInfo> objectiveInfoMap = new HashMap<Long, ObjectiveInfo>();

	public HashMap<Long, String> getWorldNamesMap() {
		return worldNamesMap;
	}

	public void addWorldName(Long id, String name) {
		worldNamesMap.put(id, name);
	}

	public void setWorldNamesMap(HashMap<Long, String> worldNamesMap) {
		this.worldNamesMap = worldNamesMap;
	}

	public String getWorldName(Long id) {
		if (worldNamesMap.containsKey(id)) {
			return worldNamesMap.get(id);
		} else {
			// TODO make sure world names are up-to-date
			Log.i("Application", "key not found");
			return null;
		}
	}

	public HashMap<Long, ObjectiveInfo> getObjectiveInfoMap() {
		return objectiveInfoMap;
	}

	public void addObjectiveInfo(Long id, ObjectiveInfo info) {
		objectiveInfoMap.put(id, info);
	}

	public void setObjectiveInfoMap(HashMap<Long, ObjectiveInfo> objectiveInfoMap) {
		this.objectiveInfoMap = objectiveInfoMap;
	}

	public ObjectiveInfo getObjectiveInfo(Long id) {
		if (objectiveInfoMap.containsKey(id)) {
			return objectiveInfoMap.get(id);
		} else {
			// TODO make sure objective info is up-to-date
			Log.i("Application", "key not found");
			return null;
		}
	}

}
