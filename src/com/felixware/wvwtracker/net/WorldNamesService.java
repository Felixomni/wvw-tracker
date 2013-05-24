package com.felixware.wvwtracker.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;

public class WorldNamesService extends WebService {
	private WvwTrackerApplication application;

	public WorldNamesService(Context context, WebServiceCallback callback) {
		this.context = context;
		this.callback = callback;
		application = (WvwTrackerApplication) ((Activity) context).getApplication();
	}

	@Override
	public void startService(Object object) {
		String URLString = Constants.getURL() + Constants.SERVICE_WORLD_NAMES;
		Log.i("WorldNamesService", URLString);
		new AsyncWebTask().execute(URLString);
	}

	@Override
	protected void handleSuccess(String response) {
		Log.i("WorldNamesService", response);
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject world = jsonArray.getJSONObject(i);
				Log.i("WorldNamesService", world.getString("name"));
				application.addWorldName(world.getLong("id"), world.getString("name"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		callback.onSuccess(null);

	}

	@Override
	protected void handleError(int responseCode, String response) {
		// TODO Auto-generated method stub

	}

}
