package org.music.player;

import java.io.Serializable;

/**
 * Limiter is a constraint for MediaAdapter and FileSystemAdapter used when
 * a row is "expanded".
 */
public class Limiter implements Serializable {
	private static final long serialVersionUID = -4729694243900202614L;

	/**
	 * The type of the limiter. One of MediaUtils.TYPE_ARTIST, TYPE_ALBUM,
	 * TYPE_GENRE, or TYPE_FILE.
	 */
	public final int type;
	/**
	 * Each element will be given a separate view each representing a higher
	 * different limiters. The first element is the broadest limiter, the last
	 * the most specific. For example, an album limiter would look like:
	 * { "Some Artist", "Some Album" }
	 * Or a file limiter:
	 * { "sdcard", "Music", "folder" }
	 */
	public final String[] names;
	/**
	 * The data for the limiter. This varies according to the type of the
	 * limiter.
	 */
	public final Object data;

	/**
	 * Create a limiter with the given data. All parameters initialize their
	 * corresponding fields in the class.
	 */
	public Limiter(int type, String[] names, Object data)
	{
		this.type = type;
		this.names = names;
		this.data = data;
	}
}