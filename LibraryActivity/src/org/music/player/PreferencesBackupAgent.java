

package org.music.player;

import android.annotation.TargetApi;
import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

/**
 * Saves application preferences to the backup manager.
 */
@TargetApi(8)
public class PreferencesBackupAgent extends BackupAgentHelper {
	private static final String BACKUP_KEY = "prefs";

	@Override
	public void onCreate()
	{
		// This is the preference name used by PreferenceManager.getDefaultSharedPreferences
		String prefs = getPackageName() + "_preferences";
		addHelper(BACKUP_KEY, new SharedPreferencesBackupHelper(this, prefs));
	}
}
