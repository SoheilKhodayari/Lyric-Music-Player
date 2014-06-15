
package org.music.player;

import org.music.player.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

/**
 * 2x2 widget that shows title, artist, a (hidden) play/pause button, a (hidden)
 * next button, and cover art in the background.
 */
public class FourSquareWidget extends AppWidgetProvider {
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
		sEnabled = manager.getAppWidgetIds(new ComponentName(context, FourSquareWidget.class)).length != 0;
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

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.four_square_widget);

		boolean playing = (state & PlaybackService.FLAG_PLAYING) != 0;
		int playResource = R.drawable.play;
		int nextResource = R.drawable.next;
		Bitmap cover = null;

		if ((state & PlaybackService.FLAG_NO_MEDIA) != 0) {
			views.setViewVisibility(R.id.buttons, View.INVISIBLE);
			views.setViewVisibility(R.id.title, View.INVISIBLE);
			views.setInt(R.id.artist, "setText", R.string.no_songs);
		} else if (song == null) {
			views.setViewVisibility(R.id.buttons, View.VISIBLE);
			views.setViewVisibility(R.id.title, View.INVISIBLE);
			views.setInt(R.id.artist, "setText", R.string.app_name);
		} else {
			views.setViewVisibility(R.id.title, View.VISIBLE);
			views.setViewVisibility(R.id.buttons, View.VISIBLE);
			views.setTextViewText(R.id.title, song.title);
			views.setTextViewText(R.id.artist, song.artist);
			cover = song.getCover(context);
			playResource = playing ? R.drawable.hidden_pause : R.drawable.hidden_play;
			nextResource = R.drawable.hidden_next;
		}

		views.setImageViewResource(R.id.play_pause, playResource);
		views.setImageViewResource(R.id.next, nextResource);

		if (cover == null) {
			views.setImageViewResource(R.id.cover, R.drawable.fallback_cover);
		} else {
			views.setImageViewBitmap(R.id.cover, cover);
		}


		Intent intent;
		PendingIntent pendingIntent;

		ComponentName service = new ComponentName(context, PlaybackService.class);

		intent = new Intent(context, LibraryActivity.class);
		intent.setAction(Intent.ACTION_MAIN);
		pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.title, pendingIntent);
		views.setOnClickPendingIntent(R.id.artist, pendingIntent);

		intent = new Intent(PlaybackService.ACTION_TOGGLE_PLAYBACK).setComponent(service);
		pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.play_pause, pendingIntent);

		intent = new Intent(PlaybackService.ACTION_NEXT_SONG).setComponent(service);
		pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.next, pendingIntent);

		manager.updateAppWidget(new ComponentName(context, FourSquareWidget.class), views);
	}
}
