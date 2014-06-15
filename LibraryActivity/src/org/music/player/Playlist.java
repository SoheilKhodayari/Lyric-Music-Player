package org.music.player;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Provides various playlist-related utility functions.
 */
public class Playlist {
	/**
	 * Queries all the playlists known to the MediaStore.
	 *
	 * @param resolver A ContentResolver to use.
	 * @return The queried cursor.
	 */
	public static Cursor queryPlaylists(ContentResolver resolver)
	{
		Uri media = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
		String[] projection = { MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME };
		String sort = MediaStore.Audio.Playlists.NAME;
		return resolver.query(media, projection, null, null, sort);
	}

	/**
	 * Retrieves the id for a playlist with the given name.
	 *
	 * @param resolver A ContentResolver to use.
	 * @param name The name of the playlist.
	 * @return The id of the playlist, or -1 if there is no playlist with the
	 * given name.
	 */
	public static long getPlaylist(ContentResolver resolver, String name)
	{
		long id = -1;

		Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
			new String[] { MediaStore.Audio.Playlists._ID },
			MediaStore.Audio.Playlists.NAME + "=?",
			new String[] { name }, null);

		if (cursor != null) {
			if (cursor.moveToNext())
				id = cursor.getLong(0);
			cursor.close();
		}

		return id;
	}

	/**
	 * Create a new playlist with the given name. If a playlist with the given
	 * name already exists, it will be overwritten.
	 *
	 * @param resolver A ContentResolver to use.
	 * @param name The name of the playlist.
	 * @return The id of the new playlist.
	 */
	public static long createPlaylist(ContentResolver resolver, String name)
	{
		long id = getPlaylist(resolver, name);

		if (id == -1) {
			// We need to create a new playlist.
			ContentValues values = new ContentValues(1);
			values.put(MediaStore.Audio.Playlists.NAME, name);
			Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values);
			id = Long.parseLong(uri.getLastPathSegment());
		} else {
			// We are overwriting an existing playlist. Clear existing songs.
			Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id);
			resolver.delete(uri, null, null);
		}

		return id;
	}

	/**
	 * Run the given query and add the results to the given playlist. Should be
	 * run on a background thread.
	 *
	 * @param resolver A ContentResolver to use.
	 * @param playlistId The MediaStore.Audio.Playlist id of the playlist to
	 * modify.
	 * @param query The query to run. The audio id should be the first column.
	 * @return The number of songs that were added to the playlist.
	 */
	public static int addToPlaylist(ContentResolver resolver, long playlistId, QueryTask query)
	{
		if (playlistId == -1)
			return 0;

		// Find the greatest PLAY_ORDER in the playlist
		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);
		String[] projection = new String[] { MediaStore.Audio.Playlists.Members.PLAY_ORDER };
		Cursor cursor = resolver.query(uri, projection, null, null, null);
		int base = 0;
		if (cursor.moveToLast())
			base = cursor.getInt(0) + 1;
		cursor.close();

		Cursor from = query.runQuery(resolver);
		if (from == null)
			return 0;

		int count = from.getCount();
		if (count > 0) {
			ContentValues[] values = new ContentValues[count];
			for (int i = 0; i != count; ++i) {
				from.moveToPosition(i);
				ContentValues value = new ContentValues(2);
				value.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf(base + i));
				value.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, from.getLong(0));
				values[i] = value;
			}
			resolver.bulkInsert(uri, values);
		}

		from.close();

		return count;
	}

	/**
	 * Delete the playlist with the given id.
	 *
	 * @param resolver A ContentResolver to use.
	 * @param id The Media.Audio.Playlists id of the playlist.
	 */
	public static void deletePlaylist(ContentResolver resolver, long id)
	{
		Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, id);
		resolver.delete(uri, null, null);
	}

	/**
	 * Rename the playlist with the given id.
	 *
	 * @param resolver A ContentResolver to use.
	 * @param id The Media.Audio.Playlists id of the playlist.
	 * @param newName The new name for the playlist.
	 */
	public static void renamePlaylist(ContentResolver resolver, long id, String newName)
	{
		long existingId = getPlaylist(resolver, newName);
		// We are already called the requested name; nothing to do.
		if (existingId == id)
			return;
		// There is already a playlist with this name. Kill it.
		if (existingId != -1)
			deletePlaylist(resolver, existingId);

		ContentValues values = new ContentValues(1);
		values.put(MediaStore.Audio.Playlists.NAME, newName);
		resolver.update(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values, "_id=" + id, null);
	}
}
