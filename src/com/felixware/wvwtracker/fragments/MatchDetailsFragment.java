package com.felixware.wvwtracker.fragments;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.miscellaneous.Constants;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.models.MatchDetails;
import com.felixware.wvwtracker.models.Objective;
import com.felixware.wvwtracker.models.ObjectiveInfo;
import com.felixware.wvwtracker.net.MatchDetailsService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;
import com.felixware.wvwtracker.views.ObjectivesView;
import com.felixware.wvwtracker.views.TotalScoreView;

public class MatchDetailsFragment extends MatchFragment {
	private static final String TAG = "MatchDetailsFragment";

	private static final int GREEN = 0;
	private static final int BLUE = 1;
	private static final int RED = 2;

	private static final int CAMP = 0;
	private static final int TOWER = 1;
	private static final int KEEP = 2;
	private static final int CASTLE = 3;

	private static final int MAX_SCORE_BAR_HEIGHT = 300;

	private Match match;
	private MatchDetails details;
	private TotalScoreView redTotalView, blueTotalView, greenTotalView;
	private ObjectivesView greenObjectivesView, redObjectivesView, blueObjectivesView;
	private WvwTrackerApplication application;
	private int redIncome, blueIncome, greenIncome;
	private int ownedObjects[][] = new int[3][4];
	private FrameLayout chartContainer;
	private GraphicalView chartView;
	private CategorySeries mSeries = new CategorySeries("");
	private DefaultRenderer mRenderer = new DefaultRenderer();

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

		configureRenderer();

		return view;
	}

	private void configureRenderer() {
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setDisplayValues(false);
		mRenderer.setShowLegend(false);
		mRenderer.setZoomEnabled(false);
		mRenderer.setInScroll(true);
		mRenderer.setAntialiasing(true);
		mRenderer.setShowLabels(false);
		mRenderer.setPanEnabled(false);
	}

	private void bindViews(View view) {
		redTotalView = (TotalScoreView) view.findViewById(R.id.redTotalView);
		greenTotalView = (TotalScoreView) view.findViewById(R.id.greenTotalView);
		blueTotalView = (TotalScoreView) view.findViewById(R.id.blueTotalView);

		greenObjectivesView = (ObjectivesView) view.findViewById(R.id.greenObjectives);
		blueObjectivesView = (ObjectivesView) view.findViewById(R.id.blueObjectives);
		redObjectivesView = (ObjectivesView) view.findViewById(R.id.redObjectives);

		chartContainer = (FrameLayout) view.findViewById(R.id.chartContainer);

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

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}
	};

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

		greenObjectivesView.setText(ownedObjects[GREEN][CAMP], ownedObjects[GREEN][TOWER], ownedObjects[GREEN][KEEP], ownedObjects[GREEN][CASTLE], greenIncome);
		blueObjectivesView.setText(ownedObjects[BLUE][CAMP], ownedObjects[BLUE][TOWER], ownedObjects[BLUE][KEEP], ownedObjects[BLUE][CASTLE], blueIncome);
		redObjectivesView.setText(ownedObjects[RED][CAMP], ownedObjects[RED][TOWER], ownedObjects[RED][KEEP], ownedObjects[RED][CASTLE], redIncome);

		double values[] = { greenIncome, blueIncome, redIncome };
		addToSeries(values);

		mRenderer.addSeriesRenderer(getRenderer(R.color.green_team));
		mRenderer.addSeriesRenderer(getRenderer(R.color.blue_team));
		mRenderer.addSeriesRenderer(getRenderer(R.color.red_team));

		chartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
		chartContainer.addView(chartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

	}

	private SimpleSeriesRenderer getRenderer(int color) {
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		renderer.setColor(getResources().getColor(color));
		return renderer;
	}

	private void addToSeries(double values[]) {
		for (double value : values) {
			mSeries.add(value);
		}
	}

	public void addScoreToIncome(Objective objective) {
		ObjectiveInfo info = application.getObjectiveInfo(objective.id);
		if (objective.owner.equals("Blue")) {
			blueIncome += info.score;
			addObjectiveToTotal(BLUE, info.type);
		} else if (objective.owner.equals("Red")) {
			redIncome += info.score;
			addObjectiveToTotal(RED, info.type);
		} else if (objective.owner.equals("Green")) {
			greenIncome += info.score;
			addObjectiveToTotal(GREEN, info.type);
		}
	}

	public void addObjectiveToTotal(int team, String type) {
		Log.i(TAG, "team is " + Integer.toString(team));
		Log.i(TAG, "type is " + type);
		if (type.equals("camp")) {
			ownedObjects[team][CAMP] += 1;
		} else if (type.equals("tower")) {
			ownedObjects[team][TOWER] += 1;
		} else if (type.equals("keep")) {
			ownedObjects[team][KEEP] += 1;
		} else if (type.equals("castle")) {
			ownedObjects[team][CASTLE] += 1;
		}
	}

	public int getHeight(int highestScore, int score) {
		double divisor = (double) score / highestScore;
		return (int) (divisor * MAX_SCORE_BAR_HEIGHT);
	}
}
