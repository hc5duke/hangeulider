package com.choibean.android.hangeulize;

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
			}
			Log.d("press",ch);
			EditText input = hangeulizer.parser.input;
			Button copy = hangeulizer.parser.copy;
			input.setText(input.getText().append(ch));
			copy.requestFocus();
		}
	}
}
