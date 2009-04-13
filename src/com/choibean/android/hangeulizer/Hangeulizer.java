package com.choibean.android.hangeulizer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class Hangeulizer extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		EditText myInput = (EditText) findViewById(R.id.entry);
		EditText myOutput = (EditText) findViewById(R.id.output);
		Logger.setActivity(this);
		// Button myCopy = (Button) findViewById(R.id.copy);
		// Button myErase = (Button) findViewById(R.id.erase);
		myInput.addTextChangedListener(new HangeulParser(myInput, myOutput));
	}
}
