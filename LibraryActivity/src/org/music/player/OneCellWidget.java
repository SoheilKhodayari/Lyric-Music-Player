package org.music.player;

import org.music.player.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

/**
 * 1x1 widget that shows title, album art, and hidden next and play/pause
 * buttons.
 */
public class OneCellWidget extends AppWidgetProvider {
	private static boolean sEnabled;

	@Override
	public void onEnabled(Context context)
	{
		sEnabled = true;
	}

	@Override
	public void onDisabled(Context context)
	{
		sEnabled = false;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] ids)
	{
		Song song = null;
		int state = 0;

		if (PlaybackService.hasInstance()) {
			PlaybackService service = PlaybackService.get(context);
			song = service.getSong(0);
			state = service.getState();
		}

		sEnabled = true;
		updateWidget(context, manager, song, state);
	}

	/**
	 * Check if there are any instances of this widget placed.
	 */
	public static void checkEnabled(Context context, AppWidgetManager manager)
	{
		sEnabled = manager.getAppWidgetIds(new ComponentName(context, OneCellWidget.class)).length != 0;
	}

	/**
	 * Populate the widgets with the given ids with the given info.
	 *
	 * @param context A Context to use.
	 * @param manager The AppWidgetManager that will be used to update the
	 * widget.
	 * @param song The current Song in PlaybackService.
	 * @param state The current PlaybackService state.
	 */
	public static void updateWidget(Context context, AppWidgetManager manager, Song song, int state)
	{
		if (!sEnabled)
			return;

		SharedPreferences settings = PlaybackService.getSettings(context);
		boolean doubleTap = settings.getBoolean(PrefKeys.DOUBLE_TAP, false);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.one_cell_widget);

		boolean playing = (state & PlaybackService.FLAG_PLAYING) != 0;
		views.setImageViewResource(R.id.play_pause, playing ? R.drawable.hidden_pause : R.drawable.hidden_play);

		ComponentName service = new ComponentName(context, PlaybackService.class);

		Intent playPause = new Intent(doubleTap ? PlaybackService.ACTION_TOGGLE_PLAYBACK_DELAYED : PlaybackService.ACTION_TOGGLE_PLAYBACK);
		playPause.setComponent(service);
		views.setOnClickPendingIntent(R.id.play_pause, PendingIntent.getService(context, 0, playPause, 0));

		Intent next = new Intent(doubleTap ? PlaybackService.ACTION_NEXT_SONG_DELAYED : PlaybackService.ACTION_NEXT_SONG);
		next.setComponent(service);
		views.setOnClickPendingIntent(R.id.next, PendingIntent.getService(context, 0, next, 0));

		Bitmap cover = null;
		if ((state & PlaybackService.FLAG_NO_MEDIA) != 0) {
			views.setInt(R.id.title, "setText", R.string.no_songs);
		} else if (song == null) {
			views.setInt(R.id.title, "setText", R.string.app_name);
		} else {
			views.setTextViewText(R.id.title, song.title);
			cover = song.getCover(context);
		}

		if (cover == null) {
			views.setImageViewResource(R.id.cover, R.drawable.fallback_cover);
		} else {
			views.setImageViewBitmap(R.id.cover, cover);
		}

		manager.updateAppWidget(new ComponentName(context, OneCellWidget.class), views);
	}
}
