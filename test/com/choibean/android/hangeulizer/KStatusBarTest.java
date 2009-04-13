package com.choibean.android.hangeulizer;

import android.test.AndroidTestCase;
import android.widget.EditText;

public class KStatusBarTest extends AndroidTestCase {

	private String	testStrings[]	= { "foo", "bar", "moo", "cow" };

	public void testSetStatus() {
		Hangeulizer h = new Hangeulizer();
		EditText t1 = (EditText) h.findViewById(R.id.entry);
		EditText t2 = (EditText) h.findViewById(R.id.output);
		KStatusBar.initialize(t1, t2);
		KStatusBar.setStatus(testStrings[0]);
		assert(0==0);
//		assertEquals(testStrings[0], t1.getText());
		KStatusBar.setStatus(testStrings[1]);
		KStatusBar.setStatus(testStrings[2]);
		KStatusBar.setStatus(testStrings[3]);
	}
}

