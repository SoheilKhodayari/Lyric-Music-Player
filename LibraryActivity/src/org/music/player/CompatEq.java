package org.music.player;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;

/**
 * Gingerbread equalizer compatibility.
 */
@TargetApi(9)
public class CompatEq {
	private final Equalizer mEq;

	/**
	 * Create the equalizer and attach it to the given MediaPlayer's audio
	 * session.
	 */
	public CompatEq(MediaPlayer player)
	{
		Equalizer eq = new Equalizer(0, player.getAudioSessionId());
		eq.setEnabled(true);
		mEq = eq;
	}

	/**
	 * Call {@link Equalizer#getNumberOfBands()}
	 */
	public short getNumberOfBands()
	{
		return mEq.getNumberOfBands();
	}

	/**
	 * Call {@link Equalizer#setBandLevel(short, short)}.
	 */
	public void setBandLevel(short band, short level)
	{
		mEq.setBandLevel(band, level);
	}
}
