package com.choibean.android.hangeulider;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.Resources;
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
		// String text = getString(R.string.help_text);
		byte[] b;
		char[] c;
		String text = "";
		try {
			InputStream is = this.getResources().openRawResource(R.raw.help);
			b = new byte[is.available()];
			c = new char[is.available()];
			is.read(b);
			for (int i = 0; i < b.length; i++) {
				c[i] = (char) b[i];
			}
			text = String.valueOf(c);
			is.close();
		} catch (IOException e) {
			Hangeulider.logD("io", "trying to open raw help file");
		} catch (Resources.NotFoundException e) {
			Hangeulider.logD("io", e.toString());
			Hangeulider.logD("io", e.getMessage());
		}
		WebView wv = (WebView) findViewById(R.id.webview1);
		wv.loadDataWithBaseURL(null, text, "text/html", "utf-8", "about:blank");
	}

	protected void loadView(int id, String text) {
		WebView wv;
		wv = (WebView) findViewById(id);
		wv.loadData(text, mimeType, encoding);
	}
}
