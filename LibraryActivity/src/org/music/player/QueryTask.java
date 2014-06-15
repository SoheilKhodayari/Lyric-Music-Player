

package org.music.player;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Represents a pending query.
 */
public class QueryTask {
	public Uri uri;
	public final String[] projection;
	public final String selection;
	public final String[] selectionArgs;
	public String sortOrder;

	/**
	 * Used for {@link SongTimeline#addSongs(android.content.Context, QueryTask)}.
	 * One of SongTimeline.MODE_*.
	 */
	public int mode;

	/**
	 * Type of the group being query. One of MediaUtils.TYPE_*.
	 */
	public int type;

	/**
	 * Data. Required value depends on value of mode. See individual mode
	 * documentation for details.
	 */
	public long data;

	/**
	 * Create the tasks. All arguments are passed directly to
	 * ContentResolver.query().
	 */
	public QueryTask(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		this.uri = uri;
		this.projection = projection;
		this.selection = selection;
		this.selectionArgs = selectionArgs;
		this.sortOrder = sortOrder;
	}

	/**
	 * Run the query. Should be called on a background thread.
	 *
	 * @param resolver The ContentResolver to query with.
	 */
	public Cursor runQuery(ContentResolver resolver)
	{
		return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
	}
}
