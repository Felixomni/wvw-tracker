package com.felixware.wvwtracker.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.models.MatchDetails;

public class MatchDetailsService extends WebService {
	public MatchDetailsService(Context context, WebServiceCallback callback) {
		this.context = context;
		this.callback = callback;
	}

	@Override
	public void startService(Object object) {
		String URLString = Constants.getURL() + Constants.WVW_PATH + Constants.SERVICE_MATCH_DETAILS + "?match_id=" + (String) object;
		Log.i("MatchesService", URLString);
		new AsyncWebTask().execute(URLString);

	}

	@Override
	protected void handleSuccess(String response) {
		MatchDetails details = new MatchDetails();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response);
			details = MatchDetails.getMatchDetailsFromJson(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		callback.onSuccess(details);

	}

	@Override
	protected void handleError(int responseCode, String response) {
		// TODO Auto-generated method stub

	}

}
