package com.choibean.android.hangeulize;

import android.text.ClipboardManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MyButtonListener implements OnClickListener {
	Hangeulize mHangeulize;
	EditText view;

	public MyButtonListener(Hangeulize h, EditText tv) {
		this.mHangeulize = h;
		this.view = tv;
	}

	public void onClick(View v) {
		int id = v.getId();
		HangeulParser parser = Hangeulize.parser;
		Log.d("click", "<id> = " + id);
		if (id == R.id.modeButton) {
			mHangeulize.toggleDubeolshikMode();
		} else if (id == R.id.previewButton) {
			parser.grabText();
		} else if (id == R.id.helpButton) {
			// CharSequence message = mHangeulize.getText(R.string.app_name);
			// AlertDialog dialog = new AlertDialog(mHangeulize);
			// dialog.setMessage(message);
		} else if (id == R.id.copyButton) {
			Editable text = this.view.getText();
			if (text.length() > 0) {
				ClipboardManager clipboard = (ClipboardManager) mHangeulize
						.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
				clipboard.setText(text);
				CharSequence msg = mHangeulize
						.getString(R.string.copied_to_clipboard);
				StringBuffer message = new StringBuffer().append('[').append(
						this.view.getText()).append(']').append(' ')
						.append(msg);
				Toast.makeText(Hangeulize.getInstance(), message,
						Toast.LENGTH_SHORT).show();
			} else {
				CharSequence msg = mHangeulize.getString(R.string.copy_failed);
				Toast.makeText(Hangeulize.getInstance(), msg,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			String ch = "";
			EditText input = parser.input;
			EditText output = parser.output;
			Editable editable = input.getText();

			switch (id) {
			case R.id.keyG:
				ch = "g";
				break;
			case R.id.keyN:
				ch = "n";
				break;
			case R.id.keyD:
				ch = "d";
				break;
			case R.id.keyR:
				ch = "r";
				break;
			case R.id.keyM:
				ch = "m";
				break;
			case R.id.keyB:
				ch = "b";
				break;
			case R.id.keyS:
				ch = "s";
				break;
			case R.id.keyNg:
				ch = "ng";
				break;
			case R.id.keyJ:
				ch = "j";
				break;
			case R.id.keyCh:
				ch = "ch";
				break;
			case R.id.keyK:
				ch = "k";
				break;
			case R.id.keyT:
				ch = "t";
				break;
			case R.id.keyP:
				ch = "p";
				break;
			case R.id.keyH:
				ch = "h";
				break;
			case R.id.keyA:
				ch = "a";
				break;
			case R.id.keyYa:
				ch = "ya";
				break;
			case R.id.keyEo:
				ch = "eo";
				break;
			case R.id.keyYeo:
				ch = "yeo";
				break;
			case R.id.keyO:
				ch = "o";
				break;
			case R.id.keyU:
				ch = "u";
				break;
			case R.id.keyEu:
				ch = "eu";
				break;
			case R.id.keySp:
				parser.grabText();
				ch = "";
				break;
			case R.id.keyBs:
				if (editable.length() > 0) {
					input.setText(editable
							.subSequence(0, editable.length() - 1));
				} else {
					int len = output.length();
					if (len > 0) {
						output
								.setText(output.getText().subSequence(0,
										len - 1));
					}
				}
				ch = "";
				break;
			}
			Log.d("press", ch);
			if (!ch.equals(""))
				input.setText(input.getText().append(ch));
			// copy.requestFocus();
		}
	}
}
