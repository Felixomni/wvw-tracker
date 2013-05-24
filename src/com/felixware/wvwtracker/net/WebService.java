package com.felixware.wvwtracker.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class WebService {
	protected WebServiceCallback callback;
	protected Context context;
	private HttpsURLConnection connection;

	protected class AsyncWebTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			StringBuilder response = new StringBuilder();

			if (params != null) {
				URL url = null;
				try {
					url = new URL(params[0]);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				try {
					connection = (HttpsURLConnection) url.openConnection();
					int statusCode = connection.getResponseCode();
					if (statusCode == 200) {
						BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String line;
						while ((line = r.readLine()) != null) {
							response.append(line);
						}
						r.close();

					} else {
						Log.i("WebService", Integer.toString(statusCode));
						// TODO
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			return response.toString();
		}

		@Override
		protected void onPostExecute(String response) {
			handleSuccess(response);
		}

	};

	public abstract void startService(Object object);

	protected abstract void handleSuccess(String response);

	protected abstract void handleError(int responseCode, String response);

	public static interface WebServiceCallback {
		public void onSuccess(Object response);

		public void onError(Object response);
	}
}
