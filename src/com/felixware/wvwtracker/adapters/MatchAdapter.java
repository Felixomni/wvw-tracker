package com.felixware.wvwtracker.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.miscellaneous.WvwTrackerApplication;
import com.felixware.wvwtracker.models.Match;

public class MatchAdapter extends ArrayAdapter<Match> {
	private Context context;
	private List<Match> matchList;
	private WvwTrackerApplication application;

	public MatchAdapter(Context context, int id, List<Match> matchList) {
		super(context, 0, matchList);
		this.context = context;
		this.matchList = matchList;
		this.application = (WvwTrackerApplication) ((Activity) context).getApplication();
	}

	private static class ViewHolder {
		TextView redWorld;
		TextView blueWorld;
		TextView greenWorld;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(R.layout.match_list_row, null);

			holder.redWorld = (TextView) convertView.findViewById(R.id.red);
			holder.blueWorld = (TextView) convertView.findViewById(R.id.blue);
			holder.greenWorld = (TextView) convertView.findViewById(R.id.green);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.redWorld.setText(application.getWorldName(matchList.get(pos).redWorldId));
		holder.blueWorld.setText(application.getWorldName(matchList.get(pos).blueWorldId));
		holder.greenWorld.setText(application.getWorldName(matchList.get(pos).greenWorldId));

		return convertView;
	}

	public String getMatchId(int pos) {
		return matchList.get(pos).id;
	}
}
