package com.choibean.android.hangeulize;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MyButtonListener implements OnClickListener {
	Hangeulize hangeulizer;
	EditText view;

	public MyButtonListener(Hangeulize h, EditText tv) {
		this.hangeulizer = h;
		this.view = tv;
	}
	public void onClick(View v) {
		if (v.getId() == R.id.modeButton) {
			hangeulizer.toggleDubeolshikMode();
		} else {
			
		}
	}
}
