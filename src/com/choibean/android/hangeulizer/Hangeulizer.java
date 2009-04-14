package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Hangeulizer extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Logger.setActivity(this);

		HangeulParser myParser = new HangeulParser(this);
	}
}
