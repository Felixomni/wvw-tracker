package com.felixware.wvwtracker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.fragments.MatchDetailsFragment;
import com.felixware.wvwtracker.fragments.MatchListFragment;

public class HomeActivity extends SherlockFragmentActivity {
	private static final String TAG = "HomeActivity";

	private MatchDetailsFragment matchDetailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		addOrSwapFragment(new MatchListFragment());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onMatchSelected(String matchId) {
		matchDetailsFragment = MatchDetailsFragment.newInstance(matchId);
		addOrSwapFragment(matchDetailsFragment);
	}

	public void addOrSwapFragment(Fragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
	}

}
