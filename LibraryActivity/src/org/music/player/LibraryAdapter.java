package org.music.player;

import android.content.Intent;
import android.view.View;
import android.widget.ListAdapter;

/**
 * Provides support for limiters and a few other methods LibraryActivity uses
 * for its adapters.
 */
public interface LibraryAdapter extends ListAdapter {
	/**
	 * Return the type of media represented by this adapter. One of
	 * MediaUtils.TYPE_*.
	 */
	public int getMediaType();

	/**
	 * Set the limiter for the adapter.
	 *
	 * A limiter is intended to restrict displayed media to only those that are
	 * children of a given parent media item.
	 *
	 * @param limiter The limiter, created by
	 * {@link LibraryAdapter#buildLimiter(long)}.
	 */
	public void setLimiter(Limiter limiter);

	/**
	 * Returns the limiter currently active on this adapter or null if none are
	 * active.
	 */
	public Limiter getLimiter();

	/**
	 * Builds a limiter based off of the media represented by the given row.
	 *
	 * @param id The id of the row.
	 * @see LibraryAdapter#getLimiter()
	 * @see LibraryAdapter#setLimiter(Limiter)
	 */
	public Limiter buildLimiter(long id);

	/**
	 * Set a new filter.
	 *
	 * The data should be requeried after calling this.
	 *
	 * @param filter The terms to filter on, separated by spaces. Only
	 * media that contain all of the terms (in any order) will be displayed
	 * after filtering is complete.
	 */
	public void setFilter(String filter);

	/**
	 * Retrieve the data for this adapter. The data must be set with
	 * {@link LibraryAdapter#commitQuery(Object)} before it takes effect.
	 *
	 * This should be called on a worker thread.
	 *
	 * @return The data. Contents depend on the sub-class.
	 */
	public Object query();

	/**
	 * Update the adapter with the given data.
	 *
	 * Must be called on the UI thread.
	 *
	 * @param data Data from {@link LibraryAdapter#query()}.
	 */
	public void commitQuery(Object data);

	/**
	 * Clear the data for this adapter.
	 *
	 * Must be called on the UI thread.
	 */
	public void clear();

	/**
	 * Creates the row data used by LibraryActivity.
	 */
	public Intent createData(View row);

	/**
	 * Extra for row data: media id. type: long.
	 */
	public static final String DATA_ID = "id";
	/**
	 * Special id for {@link #DATA_ID}: the row represented is a header view.
	 */
	public static final long HEADER_ID = -1;
	/**
	 * Special id for {@link #DATA_ID}: invalid id.
	 */
	public static final long INVALID_ID = -2;
	/**
	 * Extra for row data: media title. type: String.
	 */
	public static final String DATA_TITLE = "title";
	/**
	 * Extra for row data: media type. type: int. One of MediaUtils.TYPE_*.
	 */
	public static final String DATA_TYPE = "type";
	/**
	 * Extra for row data: canonical file path. type: String. Only present if
	 * type is {@link MediaUtils#TYPE_FILE}.
	 */
	public static final String DATA_FILE = "file";
	/**
	 * Extra for row data: if true, row has expander arrow. type: boolean.
	 */
	public static final String DATA_EXPANDABLE = "expandable";
}
