package com.choibean.android.hangeulizer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MyButtonListener implements OnClickListener {
	Hangeulizer hangeulizer;
	EditText view;

	public MyButtonListener(Hangeulizer h, EditText tv) {
		this.hangeulizer = h;
		this.view = tv;
	}
	public void onClick(View v) {
		if (v.getId() == R.id.mode) {
			hangeulizer.toggleDubeolshikMode();
		} else {
			
		}
	}
}
