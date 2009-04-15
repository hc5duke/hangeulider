package com.choibean.android.hangeulizer;

import android.view.View;
import android.view.View.OnClickListener;

public class PreviewButtonListener implements OnClickListener {
	HangeulParser parser;
	public PreviewButtonListener(HangeulParser hp) {
		this.parser = hp;
	}
	public void onClick(View v) {
		parser.grabText();
	}
}
