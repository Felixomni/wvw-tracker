package com.felixware.wvwtracker.miscellaneous;

public class Constants {
	public static final String BASE_URL = "https://api.guildwars2.com/";
	public static final String API_VERSION = "v1/";
	public static final String WVW_PATH = "wvw/";
	public static final String SERVICE_MATCHES = "matches.json";
	public static final String SERVICE_MATCH_DETAILS = "match_details.json";
	public static final String SERVICE_WORLD_NAMES = "world_names.json";

	public static String getURL() {
		return BASE_URL + API_VERSION;
	}

}
