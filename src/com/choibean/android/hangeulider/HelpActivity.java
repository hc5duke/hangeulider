package com.choibean.android.hangeulider;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class HelpActivity extends Activity {
	WebView wv;
	CharSequence message;
	final String mimeType = "text/html";
	final String encoding = "utf-8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		message = getText(R.string.help_text);
		wv = (WebView) findViewById(R.id.webview1);
		wv.loadData(message.toString(), mimeType, encoding);
	}
}
