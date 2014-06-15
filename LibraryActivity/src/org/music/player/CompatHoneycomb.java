
package org.music.player;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Framework methods only in Honeycomb or above go here.
 */
@TargetApi(11)
public class CompatHoneycomb {
	/**
	 * Add ActionBar tabs for LibraryActivity.
	 *
	 * @param activity The activity to add to.
	 */
	public static void addActionBarTabs(final LibraryActivity activity)
	{
		ActionBar.TabListener listener = new ActionBar.TabListener() {
			private final LibraryActivity mActivity = activity;

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft)
			{
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft)
			{
				mActivity.mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft)
			{
			}
		};

		ActionBar ab = activity.getActionBar();
		ab.removeAllTabs();
		int[] order = activity.mPagerAdapter.mTabOrder;
		int[] titles = LibraryPagerAdapter.TITLES;
		for (int i = 0, n = activity.mPagerAdapter.getCount(); i != n; ++i) {
			ab.addTab(ab.newTab().setText(titles[order[i]]).setTabListener(listener));
		}
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	/**
	 * Call {@link ListView#setFastScrollAlwaysVisible(boolean)} on the given
	 * ListView with value true.
	 */
	public static void setFastScrollAlwaysVisible(ListView view)
	{
		view.setFastScrollAlwaysVisible(true);
	}

	/**
	 * Call {@link MenuItem#setActionView(View)} on the given MenuItem.
	 */
	public static void setActionView(MenuItem item, View view)
	{
		item.setActionView(view);
	}

	/**
	 * Call {@link MenuItem#setShowAsAction(int)} on the given MenuItem.
	 */
	public static void setShowAsAction(MenuItem item, int mode)
	{
		item.setShowAsAction(mode);
	}

	/**
	 * Select the ActionBar tab at the given position.
	 *
	 * @param activity The activity that owns the ActionBar.
	 * @param position The tab's position.
	 */
	public static void selectTab(Activity activity, int position)
	{
		ActionBar ab = activity.getActionBar();
		if (position < ab.getTabCount()) {
			ab.selectTab(ab.getTabAt(position));
		}
	}

	/**
	 * Call {@link android.provider.MediaStore.Audio.Genres#getContentUriForAudioId(String,int)}
	 * on the external volume.
	 */
	public static Uri getContentUriForAudioId(int id)
	{
		return MediaStore.Audio.Genres.getContentUriForAudioId("external", id);
	}

	/**
	 * Call {@link KeyEvent#hasNoModifiers()}.
	 */
	public static boolean hasNoModifiers(KeyEvent event)
	{
		return event.hasNoModifiers();
	}

	/**
	 * Call {@link KeyEvent#hasModifiers(int)}.
	 */
	public static boolean hasModifiers(KeyEvent event, int modifiers)
	{
		return event.hasModifiers(modifiers);
	}
}
