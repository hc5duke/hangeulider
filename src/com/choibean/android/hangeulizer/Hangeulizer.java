package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Hangeulizer extends Activity {

	protected static HangeulParser parser;
	protected static final int modeDuBeolShik = 0;
	protected static final int modeKonglish = 1;
	protected static int inputMode = 1;

	public void toggleDubeolshikMode() {
		setDubeolshikMode(!getDubeolshikMode());
	}

	public boolean getDubeolshikMode() {
		return (inputMode == modeDuBeolShik);
	}

	public void setDubeolshikMode(boolean dubeolshik) {
		if (dubeolshik) {
			inputMode = modeDuBeolShik;
			Logger.log("[mode] 2bs");
		} else {
			inputMode = modeKonglish;
			Logger.log("[mode] kng");
		}
		parser.setModeText();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.main);
			Logger.setActivity(this);
			parser = new HangeulParser(this);
			setDubeolshikMode(false);
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		MenuItem dbs = menu.findItem(R.id.dubeolshik);
		if (getDubeolshikMode()) {
			Logger.log("[2] doo");
			dbs.setIcon(android.R.drawable.checkbox_on_background);
			dbs.setChecked(true);
		} else {
			Logger.log("[1] doo");
			dbs.setIcon(android.R.drawable.checkbox_off_background);
			dbs.setChecked(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Logger.log("[menu] " + item.getItemId());
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
		}
		setDubeolshikMode(item.isChecked());
		return false;
	}
}
