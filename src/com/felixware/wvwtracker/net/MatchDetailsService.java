package com.felixware.wvwtracker.net;

import android.content.Context;
import android.util.Log;

import com.felixware.wvwtracker.miscellaneous.Constants;

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
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleError(int responseCode, String response) {
		// TODO Auto-generated method stub

	}

}
