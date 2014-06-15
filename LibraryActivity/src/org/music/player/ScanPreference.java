
package org.music.player;

import org.music.player.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * A preference that allows the MediaScanner to be triggered.
 */
public class ScanPreference extends Preference {
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
				setSummary(R.string.scan_in_progress);
				setEnabled(false);
			} else if (intent.getAction().equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
				setSummary(R.string.finished_scanning);
				setEnabled(true);
				context.unregisterReceiver(this);
			}
		}
	};

	public ScanPreference(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setTitle(R.string.media_scan);
		setSummary(R.string.tap_to_scan);
	}

	@Override
	public void onClick()
	{
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addDataScheme("file");
		getContext().registerReceiver(mReceiver, intentFilter);

		Uri storage = Uri.parse("file://" + Environment.getExternalStorageDirectory());
		getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, storage));
	}
}
