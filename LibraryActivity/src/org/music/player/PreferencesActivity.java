package org.music.player;

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

import org.music.player.R;

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
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.preferences);
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

	public static class AudioActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_audio);
		}
	}

	@TargetApi(11)
	public static class AudioFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_audio);
		}
	}

	public static class PlaybackActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_playback);
		}
	}

	@TargetApi(11)
	public static class PlaybackFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_playback);
		}
	}

	public static class LibraryActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_library);
		}
	}

	@TargetApi(11)
	public static class LibraryFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_library);
			PreferenceGroup group = getPreferenceScreen();
			group.removePreference(group.findPreference("controls_in_selector"));
		}
	}

	public static class NotificationsActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_notifications);
		}
	}

	@TargetApi(11)
	public static class NotificationsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_notifications);
		}
	}

	public static class ShakeActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_shake);
		}
	}

	@TargetApi(11)
	public static class ShakeFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_shake);
		}
	}

	public static class MiscActivity extends PreferenceActivity {
		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_misc);
		}
	}

	@TargetApi(11)
	public static class MiscFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_misc);
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
	public static class LyricActivity extends Activity {
		@Override
		public void onCreate(Bundle state)
		{
			super.onCreate(state);

			WebView view = new WebView(this);
			view.getSettings().setJavaScriptEnabled(true);
			view.loadUrl("file:///android_asset/lyrics.html");
			view.setBackgroundColor(Color.TRANSPARENT);
			setContentView(view);
		}
	}
	@TargetApi(11)
	public static class LyricFragment extends WebViewFragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			WebView view = (WebView)super.onCreateView(inflater, container, savedInstanceState);
			view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			view.getSettings().setJavaScriptEnabled(true);
			view.loadUrl("file:///android_asset/lyrics.html");
			view.setBackgroundColor(Color.TRANSPARENT);
			return view;
		}
	}
}
