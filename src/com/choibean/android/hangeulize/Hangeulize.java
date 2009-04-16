package com.choibean.android.hangeulize;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Hangeulize extends Activity {

	protected static HangeulParser parser;
	protected static final int modeDuBeolShik = 0;
	protected static final int modeKonglish = 1;
	protected static int inputMode = 1;
	protected NotificationManager mNotificationManager;
	private int YOURAPP_NOTIFICATION_ID = 30294;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			Log.d("Hangeul", "onCreate(null)");
			parser = new HangeulParser(this);
			setDubeolshikMode(false);
		} else {
			Log.d("Hangeul", "onCreate(**something**)");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("Hangeul", "onSIS: got to Child");
	}

	@Override
	protected void onStart() {
		super.onStart();
		notify(this, true);
		Log.d("status", "start");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("status", "stop");
	}

	protected void notify(Context context, Boolean on) {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		MenuItem dbs = menu.findItem(R.id.dubeolshik);
		Log.d("mode", String.valueOf(getDubeolshikMode()));
		if (getDubeolshikMode()) {
			dbs.setIcon(android.R.drawable.checkbox_on_background);
			dbs.setChecked(true);
		} else {
			dbs.setIcon(android.R.drawable.checkbox_off_background);
			dbs.setChecked(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("menu ", String.valueOf(item.getItemId()));
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
		}
		setDubeolshikMode(item.isChecked());
		return false;
	}

	public void toggleDubeolshikMode() {
		setDubeolshikMode(!getDubeolshikMode());
	}

	public boolean getDubeolshikMode() {
		return (inputMode == modeDuBeolShik);
	}

	public void setDubeolshikMode(boolean dubeolshik) {
		Log.d("mode", String.valueOf(dubeolshik));
		if (dubeolshik) {
			inputMode = modeDuBeolShik;
		} else {
			inputMode = modeKonglish;
		}
		parser.setModeText();
	}
}
