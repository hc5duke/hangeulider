package com.choibean.android.hangeulizer;

import android.view.View;
import android.view.View.OnClickListener;

public class PreviewListener implements OnClickListener {
	HangeulParser parser;
	public PreviewListener(HangeulParser hp) {
		this.parser = hp;
	}
	public void onClick(View v) {
		Logger.log("whoosh!");
		parser.grabText();
	}
}
