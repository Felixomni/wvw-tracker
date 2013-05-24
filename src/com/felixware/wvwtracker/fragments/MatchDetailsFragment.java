package com.felixware.wvwtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.net.MatchDetailsService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;

public class MatchDetailsFragment extends MatchFragment {
	private String matchId;

	public static MatchDetailsFragment newInstance(String matchId) {
		MatchDetailsFragment fragment = new MatchDetailsFragment();

		Bundle args = new Bundle();
		args.putString(Constants.KEY_MATCH_ID, matchId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ((matchId = getArguments().getString(Constants.KEY_MATCH_ID)) == null) {
			throw new NullPointerException("matchId must be provided!");
		}
		View view = inflater.inflate(R.layout.match_details_fragment, container, false);

		new MatchDetailsService(getActivity(), detailsCallback).startService(matchId);

		return view;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	private WebServiceCallback detailsCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {

			// TODO
		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

}
