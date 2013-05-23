package com.felixware.wvwtracker.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.felixware.wvwtracker.R;
import com.felixware.wvwtracker.models.Match;

public class MatchAdapter extends ArrayAdapter<Match> {
	private Context context;
	private List<Match> matchList;

	public MatchAdapter(Context context, int id, List<Match> matchList) {
		super(context, 0, matchList);
		this.context = context;
		this.matchList = matchList;
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

		holder.redWorld.setText(Long.toString(matchList.get(pos).redWorldId));
		holder.blueWorld.setText(Long.toString(matchList.get(pos).blueWorldId));
		holder.greenWorld.setText(Long.toString(matchList.get(pos).greenWorldId));

		return convertView;
	}
}
