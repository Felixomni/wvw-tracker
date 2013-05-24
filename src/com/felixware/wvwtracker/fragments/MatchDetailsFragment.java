package com.felixware.wvwtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.models.MatchDetails;
import com.felixware.wvwtracker.net.MatchDetailsService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;

public class MatchDetailsFragment extends MatchFragment {
	private Match match;
	private MatchDetails details;
	private TextView redScoreName, redScore, blueScoreName, blueScore, greenScoreName, greenScore;
	private WvwTrackerApplication application;

	public static MatchDetailsFragment newInstance(Match match) {
		MatchDetailsFragment fragment = new MatchDetailsFragment();

		Bundle args = new Bundle();
		args.putParcelable(Constants.KEY_MATCH, match);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ((match = getArguments().getParcelable(Constants.KEY_MATCH)) == null) {
			throw new NullPointerException("match must be provided!");
		}
		View view = inflater.inflate(R.layout.match_details_fragment, container, false);

		application = (WvwTrackerApplication) getActivity().getApplication();

		bindViews(view);

		new MatchDetailsService(getActivity(), detailsCallback).startService(match.id);

		return view;
	}

	private void bindViews(View view) {
		redScoreName = (TextView) view.findViewById(R.id.redScoreName);
		redScore = (TextView) view.findViewById(R.id.redScore);

		blueScoreName = (TextView) view.findViewById(R.id.blueScoreName);
		blueScore = (TextView) view.findViewById(R.id.blueScore);

		greenScoreName = (TextView) view.findViewById(R.id.greenScoreName);
		greenScore = (TextView) view.findViewById(R.id.greenScore);

	}

	@Override
	public void refresh() {
		new MatchDetailsService(getActivity(), detailsCallback).startService(match.id);
	}

	private WebServiceCallback detailsCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {
			details = (MatchDetails) response;

			greenScoreName.setText(application.getWorldName(match.greenWorldId));
			blueScoreName.setText(application.getWorldName(match.blueWorldId));
			redScoreName.setText(application.getWorldName(match.redWorldId));
			greenScore.setText(Integer.toString(details.totalScore.greenScore));
			blueScore.setText(Integer.toString(details.totalScore.blueScore));
			redScore.setText(Integer.toString(details.totalScore.redScore));

		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

}
