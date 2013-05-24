package com.felixware.wvwtracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.models.MatchDetails;
import com.felixware.wvwtracker.models.Objective;
import com.felixware.wvwtracker.models.ObjectiveInfo;
import com.felixware.wvwtracker.net.MatchDetailsService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;
import com.felixware.wvwtracker.views.TotalScoreView;

public class MatchDetailsFragment extends MatchFragment {
	private static final String TAG = "MatchDetailsFragment";

	private static final int GREEN = 0;
	private static final int BLUE = 1;
	private static final int RED = 2;

	private static final int MAX_SCORE_BAR_HEIGHT = 300;

	private Match match;
	private MatchDetails details;
	private TotalScoreView redTotalView, blueTotalView, greenTotalView;
	private WvwTrackerApplication application;
	private int redIncome, blueIncome, greenIncome;

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
		redTotalView = (TotalScoreView) view.findViewById(R.id.redTotalView);
		greenTotalView = (TotalScoreView) view.findViewById(R.id.greenTotalView);
		blueTotalView = (TotalScoreView) view.findViewById(R.id.blueTotalView);

	}

	@Override
	public void refresh() {
		new MatchDetailsService(getActivity(), detailsCallback).startService(match.id);

	}

	private WebServiceCallback detailsCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {
			details = (MatchDetails) response;

			populateFromDetails();
		}

		public void populateFromDetails() {
			greenTotalView.setName(application.getWorldName(match.greenWorldId));
			blueTotalView.setName(application.getWorldName(match.blueWorldId));
			redTotalView.setName(application.getWorldName(match.redWorldId));
			greenTotalView.setScore(details.totalScore.greenScore);
			blueTotalView.setScore(details.totalScore.blueScore);
			redTotalView.setScore(details.totalScore.redScore);

			int whichScore = GREEN;
			int highestScore = details.totalScore.greenScore;
			if (details.totalScore.blueScore > highestScore) {
				whichScore = BLUE;
				highestScore = details.totalScore.blueScore;
			}
			if (details.totalScore.redScore > highestScore) {
				whichScore = RED;
				highestScore = details.totalScore.redScore;
			}
			switch (whichScore) {
			case BLUE:
				blueTotalView.setScoreBarHeight(MAX_SCORE_BAR_HEIGHT);
				greenTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.greenScore));
				redTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.redScore));
				break;
			case GREEN:
				greenTotalView.setScoreBarHeight(MAX_SCORE_BAR_HEIGHT);
				blueTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.blueScore));
				redTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.redScore));
				break;
			case RED:
				redTotalView.setScoreBarHeight(MAX_SCORE_BAR_HEIGHT);
				blueTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.blueScore));
				greenTotalView.setScoreBarHeight(getHeight(highestScore, details.totalScore.greenScore));
				break;
			default:
				break;
			}

			redIncome = 0;
			blueIncome = 0;
			greenIncome = 0;

			for (Objective objective : details.blueHome.objectives) {
				addScoreToIncome(objective);
			}
			for (Objective objective : details.redHome.objectives) {
				addScoreToIncome(objective);
			}
			for (Objective objective : details.greenHome.objectives) {
				addScoreToIncome(objective);
			}
			for (Objective objective : details.center.objectives) {
				addScoreToIncome(objective);
			}

			Log.i(TAG, "red income is " + Integer.toString(redIncome));
			Log.i(TAG, "blue income is " + Integer.toString(blueIncome));
			Log.i(TAG, "green income is " + Integer.toString(greenIncome));
		}

		public void addScoreToIncome(Objective objective) {
			ObjectiveInfo info = application.getObjectiveInfo(objective.id);
			if (objective.owner.equals("Blue")) {
				blueIncome += info.score;
			} else if (objective.owner.equals("Red")) {
				redIncome += info.score;
			} else if (objective.owner.equals("Green")) {
				greenIncome += info.score;
			}
		}

		public int getHeight(int highestScore, int score) {
			double divisor = (double) score / highestScore;
			return (int) (divisor * MAX_SCORE_BAR_HEIGHT);
		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

}
