

package org.music.player;

import org.music.player.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * CursorAdapter backed by MediaStore playlists.
 */
public class TabOrderAdapter extends BaseAdapter implements DragListView.DragAdapter {
	private final TabOrderActivity mActivity;
	private final LayoutInflater mInflater;
	private int[] mTabIds;

	/**
	 * Create a tab order adapter.
	 *
	 * @param activity The activity that will own this adapter. The activity
	 * will be notified when items have been moved.
	 */
	public TabOrderAdapter(TabOrderActivity activity)
	{
		mActivity = activity;
		mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Set the array containing the order of tab ids.
	 */
	public void setTabIds(int[] ids)
	{
		mTabIds = ids;
		notifyDataSetChanged();
	}

	/**
	 * Returns the array containing the order of tab ids. Do not modify the array.
	 */
	public int[] getTabIds()
	{
		return mTabIds;
	}

	@Override
	public void move(int from, int to)
	{
		if (from == to)
			return;

		int[] ids = mTabIds;
		int tempId = ids[from];

		if (from > to) {
			System.arraycopy(ids, to, ids, to + 1, from - to);
		} else {
			System.arraycopy(ids, from + 1, ids, from, to - from);
		}

		ids[to] = tempId;
		notifyDataSetChanged();
		mActivity.save();
	}

	@Override
	public void remove(int position)
	{
		// not implemented
	}

	@Override
	public int getCount()
	{
		return LibraryPagerAdapter.MAX_ADAPTER_COUNT;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return mTabIds[position];
	}

	@Override
	public View getView(int position, View convert, ViewGroup parent)
	{
		DragTextView text;
		if (convert == null) {
			text = (DragTextView)mInflater.inflate(R.layout.tab_order_row, null);
		} else {
			text = (DragTextView)convert;
		}
		text.setText(LibraryPagerAdapter.TITLES[mTabIds[position]]);
		return text;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}
}
