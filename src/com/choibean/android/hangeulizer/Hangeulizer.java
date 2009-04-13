package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.os.Bundle;

public class Hangeulizer extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Logger.setActivity(this);
		// Button myCopy = (Button) findViewById(R.id.copy);
		// Button myErase = (Button) findViewById(R.id.erase);
		HangeulParser myParser = new HangeulParser(this);
	}
}
