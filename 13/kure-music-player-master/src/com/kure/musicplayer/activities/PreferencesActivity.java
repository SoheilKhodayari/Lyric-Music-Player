package com.kure.musicplayer.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import java.util.List;

import com.kure.musicplayer.R;

/**
 * The preferences activity in which one can change application preferences.
 */
public class PreferencesActivity extends PreferenceActivity {
	/**
	 * Initialize the activity, loading the preference specifications.
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); {
			addPreferencesFromResource(R.xml.pre2);
		}
	}

	@TargetApi(11)
	@Override
	public void onBuildHeaders(List<Header> target)
	{
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}


	public static class AboutActivity extends Activity {
		@Override
		public void onCreate(Bundle state)
		{
			super.onCreate(state);
			WebView view = new WebView(this);
			view.getSettings().setJavaScriptEnabled(true);
			view.loadUrl("file:///android_asset/about.html");
			view.setBackgroundColor(Color.TRANSPARENT);
			setContentView(view);
		}
	}

	@TargetApi(11)
	public static class AboutFragment extends WebViewFragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			WebView view = (WebView)super.onCreateView(inflater, container, savedInstanceState);
			view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			view.getSettings().setJavaScriptEnabled(true);
			view.loadUrl("file:///android_asset/about.html");
			view.setBackgroundColor(Color.TRANSPARENT);
			return view;
		}
	}
}
