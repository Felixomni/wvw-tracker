package com.felixware.wvwtracker.fragments;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.activities.HomeActivity;
import com.felixware.wvwtracker.adapters.MatchAdapter;
import com.felixware.wvwtracker.models.Match;
import com.felixware.wvwtracker.net.MatchesService;
import com.felixware.wvwtracker.net.WebService.WebServiceCallback;
import com.felixware.wvwtracker.net.WorldNamesService;

public class MatchListFragment extends MatchFragment {
	private ListView list;
	private MatchAdapter adapter;
	private HomeActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.match_list_fragment, container, false);

		list = (ListView) view.findViewById(R.id.list);
		activity = (HomeActivity) getActivity();

		new WorldNamesService(getActivity(), namesCallback).startService(null);

		return view;
	}

	private WebServiceCallback namesCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {
			new MatchesService(getActivity(), matchesCallback).startService(null);

		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

	private WebServiceCallback matchesCallback = new WebServiceCallback() {

		@Override
		public void onSuccess(Object response) {
			@SuppressWarnings("unchecked")
			List<Match> matchList = (List<Match>) response;
			Collections.sort(matchList, new Comparator<Match>() {

				@Override
				public int compare(Match lhs, Match rhs) {
					return rhs.id.compareTo(lhs.id) * -1;
				}

			});
			adapter = new MatchAdapter(getActivity(), 0, matchList);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
					Match match = adapter.getMatch(pos);
					if (match != null) {
						activity.onMatchSelected(match);
					}

				}

			});

		}

		@Override
		public void onError(Object response) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void refresh() {
		new MatchesService(getActivity(), matchesCallback).startService(null);
	}

}
