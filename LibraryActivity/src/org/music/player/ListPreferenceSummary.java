package org.music.player;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

/**
 * Overrides ListPreference to show the selected value as the summary.
 *
 * (ListPreference should supposedly be able to do this itself if %s is in the
 * summary, but as far as I can tell that behavior is broken.)
 */
public class ListPreferenceSummary extends ListPreference {
	public ListPreferenceSummary(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public CharSequence getSummary()
	{
		return getEntry();
	}

	@Override
	protected void onDialogClosed(boolean positiveResult)
	{
		super.onDialogClosed(positiveResult);
		notifyChanged();
	}
}
