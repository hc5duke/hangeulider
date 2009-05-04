package com.choibean.android.hangeulider;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Hangeulider extends Activity {

	protected static HangeulParser parser;
	protected static final int modeDubeolshik = 0;
	protected static final int modeKonglish = 1;
	protected static int inputMode = 1;
	protected NotificationManager mNotificationManager;
	protected Menu mMenu = null;
	private int YOURAPP_NOTIFICATION_ID = 0;

	private static Hangeulider mInstance;

	public static Hangeulider getInstance() {
		return mInstance;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(isWide() ? R.layout.wide : R.layout.tall);

		// load previous text
		mSaved = (EditText) findViewById(R.id.outputEdit);
		parser = new HangeulParser(this);
		setDubeolshikMode(false);

		if (savedInstanceState == null) {
			logD("State", "onCreate(null)");
		} else {
			logD("State", "onCreate(**something**)");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		logD("State", "onSIS: got to Child");
		Set<String> set = outState.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			logD("State", outState.get(iterator.next()).getClass().getName());
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		toggleNotification(true);
		logD("status", "start");
	}

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences prefs = getPreferences(0);
		String restoredText = prefs.getString("text", null);
		if (restoredText != null) {
			mSaved.setText(restoredText, TextView.BufferType.EDITABLE);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		SharedPreferences.Editor editor = getPreferences(0).edit();
		editor.putString("text", mSaved.getText().toString());
		editor.commit();
	}

	private EditText mSaved;

	protected void sendNotification(Context context) {
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		ComponentName comp = new ComponentName(context.getPackageName(),
				getClass().getName());
		Intent intent = new Intent().setComponent(comp);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification n = new Notification(R.drawable.flag,
				getString(R.string.app_name), System.currentTimeMillis());
		n.setLatestEventInfo(context, getString(R.string.notification_title),
				getString(R.string.notification_motto), pendingIntent);
		n.flags = Notification.FLAG_ONGOING_EVENT;
		mNotificationManager.notify(YOURAPP_NOTIFICATION_ID, n);
		if (mMenu != null) {
			MenuItem notiMode = mMenu.findItem(R.id.notificationMenuItem);
			notiMode.setChecked(true);
		}
	}

	protected void cancelNotification() {
		mNotificationManager.cancel(YOURAPP_NOTIFICATION_ID);
		if (mMenu != null) {
			MenuItem notiMode = mMenu.findItem(R.id.notificationMenuItem);
			notiMode.setChecked(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		mMenu = menu;
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		logD("mode", String.valueOf(getDubeolshikMode()));
		mMenu = menu;
		if (mMenu == null) {
			return false;
		}
		MenuItem notification = mMenu.findItem(R.id.notificationMenuItem);
		boolean nMode = notification.isChecked();
		int iconId = nMode ? android.R.drawable.checkbox_on_background
				: android.R.drawable.checkbox_off_background;
		notification.setIcon(iconId);
		notification.setChecked(nMode);

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		logD("menu ", String.valueOf(id));
		if (id == R.id.notificationMenuItem) {
			if (item.isChecked()) {
				toggleNotification(false);
			} else {
				toggleNotification(true);
			}
		} else if (id == R.id.exitMenuItem) {
			this.finish();
		}
		// cancelNotification
		return false;
	}

	public void toggleNotification(boolean toggle) {
		if (toggle) {
			sendNotification(this);
		} else {
			cancelNotification();
		}

	}

	public void toggleDubeolshikMode() {
		setDubeolshikMode(!getDubeolshikMode());
	}

	public boolean getDubeolshikMode() {
		// tall mode has no dbs
		return (Hangeulider.inputMode == modeDubeolshik) && isWide();
	}

	public void setDubeolshikMode(boolean dubeolshik) {
		logD("mode", String.valueOf(dubeolshik));
		Hangeulider.inputMode = dubeolshik ? modeDubeolshik : modeKonglish;
		ImageView dbs = (ImageView) findViewById(R.id.dbs);
		if (dbs != null) {
			if (dubeolshik) {
				dbs.setVisibility(View.VISIBLE);
			} else {
				dbs.setVisibility(View.INVISIBLE);
			}
		}
		parser.setModeText();
	}

	public boolean isWide() {
		Display display = getWindowManager().getDefaultDisplay();
		return display.getWidth() > display.getHeight();
	}

	public static void logD(String tag, String msg) {
		Log.d(tag, msg);
	}
}
