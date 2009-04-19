package com.choibean.android.hangeulize;

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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Hangeulize extends Activity {

	protected static HangeulParser parser;
	protected static final int modeDuBeolShik = 0;
	protected static final int modeKonglish = 1;
	protected static int inputMode = 1;
	protected NotificationManager mNotificationManager;
	protected Menu mMenu = null;
	private int YOURAPP_NOTIFICATION_ID = 30294;

	private static Hangeulize mInstance;

	public static Hangeulize getInstance() {
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
			Log.d("State", "onCreate(null)");
		} else {
			Log.d("State", "onCreate(**something**)");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("State", "onSIS: got to Child");
		Set<String> set = outState.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			Log.d("State", outState.get(iterator.next()).getClass().getName());
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		toggleNotification(true);
		Log.d("status", "start");
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
		Log.d("mode", String.valueOf(getDubeolshikMode()));
		mMenu = menu;
		if (mMenu == null) {
			return false;
		}
		MenuItem dbsMode = mMenu.findItem(R.id.dubeolshikMenuItem);
		boolean dMode = dbsMode.isChecked();
		int iconId = dMode ? android.R.drawable.checkbox_on_background
				: android.R.drawable.checkbox_off_background;
		dbsMode.setIcon(iconId);
		dbsMode.setChecked(dMode);

		MenuItem notification = mMenu.findItem(R.id.notificationMenuItem);
		boolean nMode = notification.isChecked();
		iconId = nMode ? android.R.drawable.checkbox_on_background
				: android.R.drawable.checkbox_off_background;
		notification.setIcon(iconId);
		notification.setChecked(nMode);

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Log.d("menu ", String.valueOf(id));
		if (id == R.id.dubeolshikMenuItem) {
			if (item.isChecked()) {
				item.setChecked(false);
			} else {
				item.setChecked(true);
			}
			setDubeolshikMode(item.isChecked());
		} else if (id == R.id.notificationMenuItem) {
			if (item.isChecked()) {
				toggleNotification(false);
			} else {
				toggleNotification(true);
			}
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
		return (Hangeulize.inputMode == modeDuBeolShik);
	}

	public void setDubeolshikMode(boolean dubeolshik) {
		Log.d("mode", String.valueOf(dubeolshik));
		Hangeulize.inputMode = dubeolshik ? modeDuBeolShik : modeKonglish;

		parser.setModeText();
	}

	public boolean isWide() {
		Display display = getWindowManager().getDefaultDisplay();
		return display.getWidth() > display.getHeight();
	}
}
