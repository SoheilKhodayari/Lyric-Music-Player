package org.music.player;

import org.music.player.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Compact playback activity that displays itself like a dialog. That is, the
 * window is not fullscreen but only as large as it needs to be. Includes a
 * CoverView and control buttons.
 */
public class MiniPlaybackActivity extends PlaybackActivity {
	@Override
	public void onCreate(Bundle state)
	{
		super.onCreate(state);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mini_playback);

		mCoverView = (CoverView)findViewById(R.id.cover_view);
		mCoverView.setOnClickListener(this);
		mCoverView.setup(mLooper, this, CoverBitmap.STYLE_OVERLAPPING_BOX);

		View previousButton = findViewById(R.id.previous);
		previousButton.setOnClickListener(this);
		mPlayPauseButton = (ImageButton)findViewById(R.id.play_pause);
		mPlayPauseButton.setOnClickListener(this);
		View nextButton = findViewById(R.id.next);
		nextButton.setOnClickListener(this);

		mShuffleButton = (ImageButton)findViewById(R.id.shuffle);
		mShuffleButton.setOnClickListener(this);
		registerForContextMenu(mShuffleButton);
		mEndButton = (ImageButton)findViewById(R.id.end_action);
		mEndButton.setOnClickListener(this);
		registerForContextMenu(mEndButton);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId()) {
		case R.id.cover_view:
			startActivity(new Intent(this, FullPlaybackActivity.class));
			finish();
			break;
		default:
			super.onClick(view);
		}
	}
}
