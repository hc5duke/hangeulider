package com.choibean.android.hangeulizer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class EraseButtonListener implements OnClickListener {
	EditText view;
	public EraseButtonListener(EditText tv) {
		this.view = tv;
	}
	public void onClick(View v) {
		this.view.setText("");
	}
}
