package com.kure.musicplayer;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class About {/**
	 * Current app's preferences.
	 * They're read and saved on `res/xml/pref2.xml`.
	 */
	private SharedPreferences pref2 = null;
	
	/**
	 * Initializes the internal settings
	 */
	public void load(Context c) {
        pref2 = PreferenceManager.getDefaultSharedPreferences(c);
	}
	
	/**
	 * Resets all settings to default.
	 */
	public void reset() {
		pref2.edit().clear().commit();
	}
	
	// QUERY METHODS
	
	public boolean get(String key, boolean defaultValue) {
		if (pref2 == null)
			return defaultValue;
		
		return pref2.getBoolean(key, defaultValue);
	}
	
	public String get(String key, String defaultValue) {
		if (pref2 == null)
			return defaultValue;
		
		return pref2.getString(key, defaultValue);
	}
	
	public int get(String key, int defaultValue) {
		if (pref2 == null)
			return defaultValue;
		
		return pref2.getInt(key, defaultValue);
	}

}
