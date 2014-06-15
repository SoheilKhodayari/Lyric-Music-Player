package org.music.player;

import org.music.player.R;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Simple dialog to prompt to user to enter a playlist name. Has an EditText to
 * enter the name and two buttons, create and cancel. Create changes to
 * overwrite if a name that already exists is selected.
 */
public class NewPlaylistDialog extends Dialog implements TextWatcher, View.OnClickListener {
	/**
	 * The create/overwrite button.
	 */
	private Button mPositiveButton;
	/**
	 * The text entry view.
	 */
	private EditText mText;
	/**
	 * Whether the dialog has been accepted. The dialog is accepted if create
	 * was clicked.
	 */
	private boolean mAccepted;
	/**
	 * The text to display initially. When the EditText contains this text, the
	 * positive button will be disabled.
	 */
	private final String mInitialText;
	/**
	 * The resource containing the string describing the default positive
	 * action (e.g. "Create" or "Rename").
	 */
	private final int mActionRes;
	/**
	 * An intent that is simply stored in the dialog.
	 */
	private final Intent mIntent;

	/**
	 * Create a NewPlaylistDialog.
	 *
	 * @param context A Context to use.
	 * @param initialText The text to show initially. The positive button is
	 * disabled when the EditText contains this text.
	 * @param actionText A string resource describing the default positive
	 * action (e.g. "Create").
	 * @param intent An optional intent to store with the dialog.
	 */
	public NewPlaylistDialog(Context context, String initialText, int actionText, Intent intent)
	{
		super(context);
		mInitialText = initialText;
		mActionRes = actionText;
		mIntent = intent;
	}

	@Override
	protected void onCreate(Bundle state)
	{
		super.onCreate(state);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		setContentView(R.layout.new_playlist_dialog);

		setTitle(R.string.choose_playlist_name);

		mPositiveButton = (Button)findViewById(R.id.create);
		mPositiveButton.setOnClickListener(this);
		mPositiveButton.setText(mActionRes);
		View negativeButton = findViewById(R.id.cancel);
		negativeButton.setOnClickListener(this);

		mText = (EditText)findViewById(R.id.playlist_name);
		mText.addTextChangedListener(this);
		mText.setText(mInitialText);
		mText.requestFocus();
	}

	/**
	 * Returns the playlist name currently entered in the dialog.
	 */
	public String getText()
	{
		return mText.getText().toString();
	}

	/**
	 * Returns the stored intent.
	 */
	public Intent getIntent()
	{
		return mIntent;
	}

	public void afterTextChanged(Editable s)
	{
		// do nothing
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		// do nothing
	}

	public void onTextChanged(CharSequence text, int start, int before, int count)
	{
		String string = text.toString();
		if (string.equals(mInitialText)) {
			mPositiveButton.setEnabled(false);
		} else {
			mPositiveButton.setEnabled(true);
			// Update the action button based on whether there is an
			// existing playlist with the given name.
			ContentResolver resolver = getContext().getContentResolver();
			int res = Playlist.getPlaylist(resolver, string) == -1 ? mActionRes : R.string.overwrite;
			mPositiveButton.setText(res);
		}
	}

	/**
	 * Returns whether the dialog has been accepted. The dialog is accepted
	 * when the create/overwrite button is clicked.
	 */
	public boolean isAccepted()
	{
		return mAccepted;
	}

	public void onClick(View view)
	{
		switch (view.getId()) {
		case R.id.create:
			mAccepted = true;
			// fall through
		case R.id.cancel:
			dismiss();
			break;
		}
	}
}
