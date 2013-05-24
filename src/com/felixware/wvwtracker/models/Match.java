package com.felixware.wvwtracker.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {
	public String id;
	public long redWorldId;
	public long blueWorldId;
	public long greenWorldId;

	public Match() {
	}

	public Match(String id, long redWorldId, long blueWorldId, long greenWorldId) {
		this.id = id;
		this.redWorldId = redWorldId;
		this.blueWorldId = blueWorldId;
		this.greenWorldId = greenWorldId;
	}

	private Match(Parcel in) {
		id = in.readString();
		redWorldId = in.readLong();
		blueWorldId = in.readLong();
		greenWorldId = in.readLong();
	}

	public static Match getMatchFromJSON(JSONObject jsonObject) {
		Match match = null;
		try {
			match = new Match(jsonObject.getString("wvw_match_id"), jsonObject.getLong("red_world_id"), jsonObject.getLong("blue_world_id"), jsonObject.getLong("green_world_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return match;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeLong(redWorldId);
		out.writeLong(blueWorldId);
		out.writeLong(greenWorldId);
	}

	public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {

		@Override
		public Match createFromParcel(Parcel in) {
			return new Match(in);
		}

		@Override
		public Match[] newArray(int size) {
			return new Match[size];
		}

	};

}
