package com.choibean.android.hangeulizer;

import com.choibean.android.hangeulize.R;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class Logger {
	public static final boolean debug = true;

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
		currentTextView = (TextView) activity.findViewById(R.id.log1);
		currentTextView.setText("");
		archiveTextView = (TextView) activity.findViewById(R.id.log2);
		archiveTextView.setText("");
		if (!debug) {
			currentTextView.setVisibility(View.GONE);
			archiveTextView.setVisibility(View.GONE);
		}
	}

	public static void log(Object o) {
		log(o, 0);
	}

	public static void log(Object o, int status) {
		if (!debug || o == null)
			return;
		String text = o.toString();
		int color = COLOR_OK;
		if (status == -1) {
			color = COLOR_ERROR;
		} else if (status == 1) {
			color = COLOR_ALERT;
		}
		archivedText.insert(0, '\n').insert(0, currentTextView.getText());
		if (archiveTextView != null) {
			archiveTextView.setText(archivedText);
		}
		if (currentTextView != null) {
			currentTextView.setBackgroundColor(color);
			currentTextView.setText(text);
		}
	}

}
