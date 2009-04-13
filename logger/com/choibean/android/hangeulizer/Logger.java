package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.widget.TextView;

public class Logger {
	public static int ERROR = -1;
	public static int OK = 0;
	public static int ALERT = 1;
	private static final int COLOR_ERROR = 0xff0000;
	private static final int COLOR_OK = 0x00aa00;
	private static final int COLOR_ALERT = 0xffaa0;
	protected static StringBuffer archivedText = new StringBuffer();
	protected static TextView currentTextView;
	protected static TextView archiveTextView;
	protected static Activity activity;

	public static void setActivity(Activity a) {
		activity = a;
	}

	public static void setStatus(String text) {
		setStatus(text, 0);
	}

	public static void setStatus(String text, int status) {
		int color = COLOR_OK;
		if (status == -1) {
			color = COLOR_ERROR;
		} else if (status == 1) {
			color = COLOR_ALERT;
		}
		if (currentTextView == null) {
			currentTextView = (TextView) activity.findViewById(R.id.status);
		}
		if (archiveTextView == null) {
			archiveTextView = (TextView) activity.findViewById(R.id.status2);
		}
		archivedText.insert(0, '\n').insert(0, currentTextView.getText());
		archiveTextView.setText(archivedText);

		currentTextView.setBackgroundColor(color);
		currentTextView.setText(text);
	}

}
