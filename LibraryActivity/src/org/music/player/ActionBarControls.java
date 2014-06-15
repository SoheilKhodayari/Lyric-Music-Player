package org.music.player;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * LinearLayout that contains some hacks for sizing inside an ActionBar.
 */
public class ActionBarControls extends LinearLayout {
	public ActionBarControls(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void onMeasure(int ws, int hs)
	{
		super.onMeasure(ws, hs);

		float density = getResources().getDisplayMetrics().density;

		int width = MeasureSpec.getSize(ws);
		int widthMode = MeasureSpec.getMode(ws);
		if (widthMode != MeasureSpec.EXACTLY)
			width = (int)(200 * density);

		setMeasuredDimension(width, (int)(40 * density));

		ViewGroup.LayoutParams lp = getLayoutParams();
		try {
			lp.getClass().getField("expandable").set(lp, true);
		} catch (Exception e) {
			Log.d("VanillaMusic", "Failed to set controls expandable", e);
		}
	}
}
