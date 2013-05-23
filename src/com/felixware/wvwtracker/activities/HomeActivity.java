package com.felixware.wvwtracker.activities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.adapters.MatchAdapter;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.net.MatchesService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;

public class HomeActivity extends SherlockFragmentActivity {
	private static final String TAG = "HomeActivity";
	private ListView list;
	private MatchAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		list = (ListView) findViewById(R.id.list);
		MatchesService service = new MatchesService(this, mCallback);
		service.startService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private WebServiceCallback mCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {
			List<Match> matchList = (List<Match>) response;
			Collections.sort(matchList, new Comparator<Match>() {

				@Override
				public int compare(Match lhs, Match rhs) {
					return rhs.id.compareTo(lhs.id) * -1;
				}

			});
			for (int i = 0; i < matchList.size(); i++) {
				Log.i(TAG, matchList.get(i).id);
			}
			adapter = new MatchAdapter(HomeActivity.this, 0, matchList);
			list.setAdapter(adapter);

		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

}
