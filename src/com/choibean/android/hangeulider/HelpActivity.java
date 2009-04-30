package com.choibean.android.hangeulider;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends Activity {
	CharSequence message;
	final String mimeType = "text/html";
	final String encoding = "utf-8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		String text = getString(R.string.help_text);
		loadView(R.id.webview1, text);
	}

	protected void loadView(int id, String text) {
		WebView wv;
		wv = (WebView) findViewById(id);
		wv.loadData(text, mimeType, encoding);
	}
}
