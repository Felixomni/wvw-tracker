package com.felixware.wvwtracker.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.models.Match;

public class MatchesService extends WebService {
	public MatchesService(Context context, WebServiceCallback callback) {
		this.context = context;
		this.callback = callback;
	}

	@Override
	public void startService(Object object) {
		String URLString = Constants.getURL() + Constants.WVW_PATH + Constants.SERVICE_MATCHES;
		Log.i("MatchesService", URLString);
		new AsyncWebTask().execute(URLString);
	}

	@Override
	protected void handleSuccess(String response) {
		List<Match> matchList = new ArrayList<Match>();
		Match match;
		JSONObject responseObject = null;
		try {
			responseObject = new JSONObject(response);
			JSONArray responseArray = responseObject.getJSONArray("wvw_matches");
			for (int i = 0; i < responseArray.length(); i++) {
				if ((match = Match.getMatchFromJSON(responseArray.getJSONObject(i))) != null) {
					matchList.add(match);
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		callback.onSuccess(matchList);

	}

	@Override
	protected void handleError(int responseCode, String response) {
		// TODO Auto-generated method stub

	}

}
