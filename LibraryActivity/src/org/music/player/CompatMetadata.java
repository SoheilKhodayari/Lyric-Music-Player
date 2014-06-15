package org.music.player;

import android.annotation.TargetApi;
import android.media.MediaMetadataRetriever;

/**
 * Wrapper around MediaMetadataRetriever to allow compatibility with older
 * versions of Android.
 */
@TargetApi(10)
public class CompatMetadata {
	/**
	 * The wrapped MediaMetadataRetriever instance.
	 */
	private final MediaMetadataRetriever mData;

	/**
	 * Create an MediaMetadataRetriever attached to the file at the given path.
	 */
	public CompatMetadata(String path)
	{
		MediaMetadataRetriever data = new MediaMetadataRetriever();
		data.setDataSource(path);
		mData = data;
	}

	/**
	 * Call {@link MediaMetadataRetriever#extractMetadata(int)}.
	 */
	public String extractMetadata(int keyCode)
	{
		return mData.extractMetadata(keyCode);
	}

	/**
	 * Call {@link MediaMetadataRetriever#release()}.
	 */
	public void release()
	{
		mData.release();
	}
}
