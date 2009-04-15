package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Hangeulizer extends Activity {

	public static final int modeDuBeolShik = 0;
	public static final int modeKonglish = 1;
	public static int inputMode = modeDuBeolShik;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Logger.setActivity(this);

		HangeulParser myParser = new HangeulParser(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Logger.log("[menu] " + item.getItemId());
		switch (item.getItemId()) {
		case R.id.dubeolshik:
			inputMode = modeDuBeolShik;
			Logger.log("[mode] 2bs");
			break;
		case R.id.konglish:
			inputMode = modeKonglish;
			Logger.log("[mode] kng");
			break;
		}
		return false;
	}
}
