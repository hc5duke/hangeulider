package com.choibean.android.hangeulize;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
			int id = v.getId();
			Log.d("click", "<id> = " + id);
			String ch = "";
			HangeulParser parser = hangeulizer.parser;
			EditText input = parser.input;
			EditText output = parser.output;
			Editable editable = input.getText();
			Button copy = parser.copy;

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
					input.setText(editable.subSequence(0, editable.length() - 1));
				} else {
					int len = output.length();
					if (len > 0) {
						output.setText(output.getText().subSequence(0, len - 1));
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
