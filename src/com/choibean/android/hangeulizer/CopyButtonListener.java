package com.choibean.android.hangeulizer;

import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class CopyButtonListener implements OnClickListener {
	EditText view;
	ClipboardManager clipboard;

	public CopyButtonListener(EditText tv, ClipboardManager clipboard) {
		this.view = tv;
		this.clipboard = clipboard;
	}

	public void onClick(View v) {
		this.clipboard.setText(this.view.getText());
		//
	}
}
