package com.choibean.android.hangeulizer;

import android.widget.TextView;

public class KStatusBar {
	private static TextView	currentStatus, archivedStatus;
	public static int ERROR = -1, OK = 0, ALERT = 1;
	private static int errorColor = 0xff0000, okColor = 0x00aa00, alertColor = 0xffaa0; 
	public static void initialize(TextView current, TextView archive) {
		currentStatus = current;
		archivedStatus = archive;
	}

	public static void setStatus(String text) {
		setStatus(text, 0);
	}
	public static void setStatus(String text, int status) {
		int color = okColor;
		if (status == -1) {
			color = errorColor;
		} else if (status == 1) {
			color = alertColor;
		}
		if (currentStatus != null) {
			if (archivedStatus != null) {
				archivedStatus.setText(currentStatus.getText() + "\n" + archivedStatus.getText());
			}
			currentStatus.setBackgroundColor(color);
			currentStatus.setText(text);
		}
	}

}
