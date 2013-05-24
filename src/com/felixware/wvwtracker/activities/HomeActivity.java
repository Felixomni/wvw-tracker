package com.felixware.wvwtracker.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.fragments.MatchDetailsFragment;
import com.felixware.wvwtracker.fragments.MatchFragment;
import com.felixware.wvwtracker.fragments.MatchListFragment;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.models.ObjectiveInfo;

public class HomeActivity extends SherlockFragmentActivity {
	private static final String TAG = "HomeActivity";

	private MatchDetailsFragment matchDetailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		initializeObjectiveInfo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			((MatchFragment) getSupportFragmentManager().findFragmentByTag("fragment")).refresh();
			return true;
		default:
			return false;
		}
	}

	public void initializeObjectiveInfo() {
		objectiveInfoTask.execute("objectiveInfo.json");
	}

	private AsyncTask<String, Void, Void> objectiveInfoTask = new AsyncTask<String, Void, Void>() {

		@Override
		protected Void doInBackground(String... params) {
			StringBuilder response = new StringBuilder();

			BufferedReader r = new BufferedReader(new InputStreamReader(openAsset(params[0])));
			String line;
			try {
				while ((line = r.readLine()) != null) {
					response.append(line);
				}
				r.close();

				JSONArray jsonArray = new JSONArray(response.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					ObjectiveInfo info = ObjectiveInfo.getObjectiveInfoFromJson(jsonArray.getJSONObject(i));
					((WvwTrackerApplication) HomeActivity.this.getApplication()).addObjectiveInfo(info.id, info);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			addOrSwapFragment(new MatchListFragment(), false);
		}

	};

	public InputStream openAsset(String asset) {
		AssetManager manager = this.getAssets();
		try {
			return manager.open(asset);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void onMatchSelected(Match match) {
		matchDetailsFragment = MatchDetailsFragment.newInstance(match);
		addOrSwapFragment(matchDetailsFragment, true);
	}

	public void addOrSwapFragment(Fragment fragment, boolean backable) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragmentContainer, fragment, "fragment");
		if (backable) {
			ft.addToBackStack(null);
		}
		ft.commit();
	}
}
