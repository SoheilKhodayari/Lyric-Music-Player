
package org.music.player;

import org.music.player.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * SeekBar preference to set the shake force threshold.
 */
public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {
	/**
	 * The current value.
	 */
	private int mValue;
	/**
	 * TextView to display current threshold.
	 */
	private TextView mValueText;

	public SeekBarPreference(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public CharSequence getSummary()
	{
		return getSummary(mValue);
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index)
	{
		return a.getInt(index, 100);
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue)
	{
		mValue = restoreValue ? getPersistedInt(mValue) : (Integer)defaultValue;
    }

	/**
	 * Create the summary for the given value.
	 *
	 * @param value The force threshold.
	 * @return A string representation of the threshold.
	 */
	private String getSummary(int value)
	{
		if ("shake_threshold".equals(getKey())) {
			return String.valueOf(value / 10.0f);
		} else {
			return String.format("%d%% (%+.1fdB)", value, 20 * Math.log10(Math.pow(value / 100.0, 3)));
		}
	}

	@Override
	protected View onCreateDialogView()
	{
		View view = super.onCreateDialogView();

		mValueText = (TextView)view.findViewById(R.id.value);
		mValueText.setText(getSummary(mValue));

		SeekBar seekBar = (SeekBar)view.findViewById(R.id.seek_bar);
		seekBar.setMax(150);
		seekBar.setProgress(mValue);
		seekBar.setOnSeekBarChangeListener(this);

		return view;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult)
	{
		notifyChanged();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		if (fromUser) {
			mValue = progress;
			mValueText.setText(getSummary(progress));
			persistInt(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
	}
}
