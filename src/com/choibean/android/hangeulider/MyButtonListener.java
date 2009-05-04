package com.choibean.android.hangeulider;

import android.content.Intent;
import android.text.ClipboardManager;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MyButtonListener implements OnClickListener {
	Hangeulider mHangeulider;
	EditText view;

	public MyButtonListener(Hangeulider h, EditText tv) {
		this.mHangeulider = h;
		this.view = tv;
	}

	public void onClick(View v) {
		int id = v.getId();
		HangeulParser parser = Hangeulider.parser;
		Hangeulider.logD("click", "<id> = " + id);
		if (id == R.id.modeButton) {
			mHangeulider.toggleDubeolshikMode();
		} else if (id == R.id.previewButton) {
			parser.grabText();
		} else if (id == R.id.outputClearButton) {
			((EditText) mHangeulider.findViewById(R.id.outputEdit)).setText("");
		} else if (id == R.id.helpButton) {
			Hangeulider hang = Hangeulider.getInstance();
			Intent myIntent = new Intent(hang, HelpActivity.class);
			// TODO: get this to work
			hang.startActivity(myIntent);
		} else if (id == R.id.copyButton) {
			if (this.view == null)
				this.view = (EditText) this.mHangeulider
						.findViewById(R.id.outputEdit);
			Editable text = this.view.getText();
			if (text.length() > 0) {
				ClipboardManager clipboard = (ClipboardManager) mHangeulider
						.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
				clipboard.setText(text);
				CharSequence msg = mHangeulider
						.getString(R.string.copied_to_clipboard);
				StringBuffer message = new StringBuffer().append('[').append(
						this.view.getText()).append(']').append(' ')
						.append(msg);
				Toast.makeText(Hangeulider.getInstance(), message,
						Toast.LENGTH_SHORT).show();
			} else {
				CharSequence msg = mHangeulider.getString(R.string.copy_failed);
				Toast.makeText(Hangeulider.getInstance(), msg,
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
			case R.id.keyAe:
				ch = "ae";
				break;
			case R.id.keyYa:
				ch = "ya";
				break;
			case R.id.keyYae:
				ch = "yae";
				break;
			case R.id.keyEo:
				ch = "eo";
				break;
			case R.id.keyE:
				ch = "e";
				break;
			case R.id.keyYeo:
				ch = "yeo";
				break;
			case R.id.keyYe:
				ch = "ye";
				break;
			case R.id.keyO:
				ch = "o";
				break;
			case R.id.keyYo:
				ch = "yo";
				break;
			case R.id.keyU:
				ch = "u";
				break;
			case R.id.keyYu:
				ch = "yu";
				break;
			case R.id.keyEu:
				ch = "eu";
				break;
			case R.id.keyI:
				ch = "i";
				break;
			case R.id.keySpace:
				if (input.getText().length() == 0) {
					input.setText(" ");
				}
				parser.grabText();
				ch = "";
				break;
			case R.id.keyDelete:
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
			Hangeulider.logD("press", ch);
			if (!ch.equals(""))
				input.setText(input.getText().append(ch));
			// copy.requestFocus();
		}
	}
}
