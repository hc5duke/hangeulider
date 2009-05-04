package com.choibean.android.hangeulider;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HangeulParser implements TextWatcher {
	private static Pattern vowelPattern;

	protected Hangeulider mHangeulider;

	// FIXME: all this should move to a viewer class. this is a controller class
	public EditText input;
	public EditText output;
	protected Button preview;
	protected Button mode;
	protected Button copy;
	protected Button help;
	protected TextView helper;

	private static HashMap<String, Integer> consonants;
	private static HashMap<String, Integer> vowels;
	private static HashMap<String, Integer> bachims;
	private static HashMap<String, Integer> jaeums;
	private static HashMap<String, String> dubs;

	public HangeulParser(Hangeulider Hangeulider) {
		this.mHangeulider = Hangeulider;
		MyButtonListener buttonListener = new MyButtonListener(Hangeulider,
				output);
		output = (EditText) Hangeulider.findViewById(R.id.outputEdit);
		helper = (TextView) Hangeulider.findViewById(R.id.helperLabel);
		preview = (Button) Hangeulider.findViewById(R.id.previewButton);
		preview.setText("");
		preview.setOnClickListener(buttonListener);
		// preview.setLongClickable(true); // TODO: han ja

		copy = (Button) Hangeulider.findViewById(R.id.copyButton);
		copy.setOnClickListener(buttonListener);
		help = (Button) Hangeulider.findViewById(R.id.helpButton);
		help.setOnClickListener(buttonListener);
		input = (EditText) Hangeulider.findViewById(R.id.inputEdit);
		input.addTextChangedListener(this);
		input.requestFocus();
		Hangeulider.findViewById(R.id.outputClearButton).setOnClickListener(
				buttonListener);
		if (Hangeulider.isWide()) {
			mode = (Button) Hangeulider.findViewById(R.id.modeButton);
			mode.setOnClickListener(buttonListener);
		} else { // tall mode
			LinearLayout layout = (LinearLayout) Hangeulider
					.findViewById(R.id.LinearLayout01);
			for (int i = 0; i < layout.getChildCount(); i++) {
				Button button = (Button) layout.getChildAt(i);
				button.setOnClickListener(buttonListener);
			}
			layout = (LinearLayout) Hangeulider
					.findViewById(R.id.LinearLayout02);
			for (int i = 0; i < layout.getChildCount(); i++) {
				Button button = (Button) layout.getChildAt(i);
				button.setOnClickListener(buttonListener);
			}
			layout = (LinearLayout) Hangeulider
					.findViewById(R.id.LinearLayout03);
			for (int i = 0; i < layout.getChildCount(); i++) {
				Button button = (Button) layout.getChildAt(i);
				button.setOnClickListener(buttonListener);
			}
			layout = (LinearLayout) Hangeulider
					.findViewById(R.id.LinearLayout04);
			for (int i = 0; i < layout.getChildCount(); i++) {
				Button button = (Button) layout.getChildAt(i);
				button.setOnClickListener(buttonListener);
			}
			Button buttonBackspace = (Button) Hangeulider
					.findViewById(R.id.keyDelete);
			buttonBackspace.setOnClickListener(buttonListener);
			Button buttonSpace = (Button) Hangeulider
					.findViewById(R.id.keySpace);
			buttonSpace.setOnClickListener(buttonListener);
		}
		setupObjects();
	}

	public void afterTextChanged(Editable e) {
	}

	public void beforeTextChanged(CharSequence cs, int a, int b, int c) {
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// try {
		String text = s.toString();
		boolean finalize = false;
		if (text.indexOf(' ') != -1 || text.indexOf('\t') != -1
				|| text.indexOf('\n') != -1)
			finalize = true;
		if (text.length() > 0 && text.trim().equals("")) {
			Hangeulider.logD("text", "should enter space here");
			if (input != null)
				input.setText("");
			output.setText(output.getText().append(' '));
		} else {
			if (mHangeulider.getDubeolshikMode()) {
				parseDuBeolShik(text, finalize);
			} else {
				parseKonglish(text, finalize);
			}
		}
		// } catch (Exception e) {
		// Log.e("error", e.toString());
		// }
	}

	public void grabText() {
		CharSequence cs = preview.getText();
		output.setText(output.getText().append(cs));
		int len = output.length();
		if (len != 0)
			output.setSelection(len - 1, len);
		if (input != null)
			input.setText("");
		preview.setText("");

	}

	public void parseDuBeolShik(String text, boolean finalize) {
		StringBuffer sb = new StringBuffer();
		int length = text.length();
		for (int i = 0; i < length; i++) {
			String c = text.substring(i, i + 1);
			String s = dubs.get(c);
			if (s == null) {
				s = dubs.get(c.toLowerCase());
			}
			if (s != null) {
				sb.append(s);
			}
		}
		parseKonglish(sb.toString(), finalize);
		sb.insert(0, ">").insert(0, text).insert(0, "[dbs]");
		Hangeulider.logD("char", sb.toString());
	}

	public void parseKonglish(String text, boolean finalize) {
		String v = "", parts[];
		text = text.trim().toLowerCase();
		parts = vowelPattern.split(text);
		Matcher m = vowelPattern.matcher(text);
		if (m.find()) {
			v = m.group();
		}

		if (parts.length < 1 || parts[0] == "") {
			Hangeulider.logD("status", text + " = too short");
			preview.setText("");
			setModeText();
			return;
		}

		if (helper != null) {
			helper.setText(R.string.press_space);
			helper.setTextColor(0xd0ffff00);
		}
		Integer consonant, vowel, bachim = null;
		vowel = vowels.get(v);
		int unicode = 44032;
		if (vowel == null) {
			unicode = 12593;
			consonant = jaeums.get(parts[0]);
			if (consonant == null && parts[0].length() > 1) {
				consonant = jaeums.get(parts[0].charAt(parts[0].length() - 1));
			}
			if (consonant != null) {
				unicode += consonant;
			}
		} else {
			unicode += vowel * 28;
			consonant = consonants.get(parts[0]);
			if (consonant != null)
				unicode += consonant * 588;
			if (parts.length > 1) {
				bachim = bachims.get(parts[1]);
				if (bachim != null) {
					unicode += bachim;
				}
			}
		}

		// log = new
		// StringBuffer(consonant).append(',').append(vowel).append(',')
		// .append(bachim);
		// Hangeulider.logD("status", log.toString());

		char c = (char) (unicode);
		Hangeulider.logD("status", String.valueOf(c));

		if (finalize) {
			input.setText("");
			output.setText(output.getText().append(c));
			preview.setText("");
		} else {
			preview.setText("" + c);
		}
	}

	public void setModeText() {
		if (mHangeulider.getDubeolshikMode()) {
			if (helper != null)
				helper.setText(R.string.type_here2);
			if (mode != null)
				mode.setText(R.string.dbs_on);
		} else {
			if (helper != null)
				helper.setText(R.string.type_here1);
			if (mode != null)
				mode.setText(R.string.dbs_off);
		}
		if (helper != null)
			helper.setTextColor(0xa0ffffff);
	}

	private static void setupObjects() {
		if (consonants != null)
			return;
		vowelPattern = Pattern.compile("([aeiouwy]+)");
		consonants = new HashMap<String, Integer>();
		vowels = new HashMap<String, Integer>();
		bachims = new HashMap<String, Integer>();
		jaeums = new HashMap<String, Integer>();
		dubs = new HashMap<String, String>();

		consonants.put("g", new Integer(0));
		consonants.put("gh", new Integer(0));
		consonants.put("gg", new Integer(1));
		consonants.put("q", new Integer(1));// q
		consonants.put("n", new Integer(2));
		consonants.put("d", new Integer(3));
		consonants.put("dd", new Integer(4));
		consonants.put("th", new Integer(4));
		consonants.put("r", new Integer(5));
		consonants.put("l", new Integer(5));
		consonants.put("m", new Integer(6));
		consonants.put("b", new Integer(7));
		consonants.put("v", new Integer(8));
		consonants.put("bb", new Integer(8));
		consonants.put("s", new Integer(9));
		consonants.put("sh", new Integer(9));
		consonants.put("ss", new Integer(10));
		consonants.put("x", new Integer(10));// x
		consonants.put("", new Integer(11));
		consonants.put("ng", new Integer(11));
		consonants.put("rh", new Integer(11));
		consonants.put("j", new Integer(12));
		consonants.put("jj", new Integer(13));
		consonants.put("z", new Integer(13));
		consonants.put("c", new Integer(13));// ch
		consonants.put("ch", new Integer(14));
		consonants.put("k", new Integer(15));
		consonants.put("kh", new Integer(15));
		consonants.put("t", new Integer(16));
		consonants.put("p", new Integer(17));
		consonants.put("h", new Integer(18));
		consonants.put("f", new Integer(18));
		consonants.put("ph", new Integer(18));

		vowels.put("a", new Integer(0));
		vowels.put("ae", new Integer(1));
		vowels.put("ya", new Integer(2));
		vowels.put("yae", new Integer(3));
		vowels.put("eo", new Integer(4));
		vowels.put("e", new Integer(5));
		vowels.put("yeo", new Integer(6));
		vowels.put("ye", new Integer(7));
		vowels.put("o", new Integer(8));
		vowels.put("wa", new Integer(9));
		vowels.put("oa", new Integer(9));
		vowels.put("ua", new Integer(9));
		vowels.put("wae", new Integer(10));
		vowels.put("oae", new Integer(10));
		vowels.put("uae", new Integer(10));
		vowels.put("oi", new Integer(11));
		vowels.put("yo", new Integer(12));
		vowels.put("u", new Integer(13));
		vowels.put("oo", new Integer(13));
		vowels.put("weo", new Integer(14));
		vowels.put("ueo", new Integer(14));
		vowels.put("wo", new Integer(14));
		vowels.put("we", new Integer(15));
		vowels.put("ue", new Integer(15));
		vowels.put("wee", new Integer(16));
		vowels.put("wi", new Integer(16));
		vowels.put("ui", new Integer(16));
		vowels.put("yu", new Integer(17));
		vowels.put("yoo", new Integer(17));
		vowels.put("eu", new Integer(18));
		vowels.put("eui", new Integer(19));
		vowels.put("i", new Integer(20));
		vowels.put("ee", new Integer(20));
		vowels.put("yi", new Integer(20));
		vowels.put("yee", new Integer(20));
		vowels.put("y", new Integer(20));

		bachims.put("g", new Integer(1));
		bachims.put("gg", new Integer(2));
		bachims.put("gs", new Integer(3));
		bachims.put("n", new Integer(4));
		bachims.put("nj", new Integer(5));
		bachims.put("nh", new Integer(6));
		bachims.put("d", new Integer(7));
		bachims.put("l", new Integer(8));
		bachims.put("r", new Integer(8));
		bachims.put("lg", new Integer(9));
		bachims.put("rg", new Integer(9));
		bachims.put("lm", new Integer(10));
		bachims.put("rm", new Integer(10));
		bachims.put("lb", new Integer(11));
		bachims.put("rb", new Integer(11));
		bachims.put("ls", new Integer(12));
		bachims.put("rs", new Integer(12));
		bachims.put("lt", new Integer(13));
		bachims.put("rt", new Integer(13));
		bachims.put("lp", new Integer(14));
		bachims.put("rp", new Integer(14));
		bachims.put("lh", new Integer(15));
		bachims.put("rh", new Integer(15));
		bachims.put("m", new Integer(16));
		bachims.put("b", new Integer(17));
		bachims.put("bs", new Integer(18));
		bachims.put("s", new Integer(19));
		bachims.put("ss", new Integer(20));
		bachims.put("x", new Integer(20));
		bachims.put("ng", new Integer(21));
		bachims.put("j", new Integer(22));
		bachims.put("ch", new Integer(23));
		bachims.put("c", new Integer(23));
		bachims.put("k", new Integer(24));
		bachims.put("ck", new Integer(24));
		bachims.put("t", new Integer(25));
		bachims.put("p", new Integer(26));
		bachims.put("h", new Integer(27));
		bachims.put("f", new Integer(27));
		bachims.put("ph", new Integer(27));

		jaeums.put("g", new Integer(0)); // g
		jaeums.put("gg", new Integer(1));// gg
		jaeums.put("q", new Integer(1));// q
		jaeums.put("gs", new Integer(2));// gs
		jaeums.put("n", new Integer(3)); // n
		jaeums.put("nj", new Integer(4));// nj
		jaeums.put("nh", new Integer(5));// nh
		jaeums.put("d", new Integer(6)); // d
		jaeums.put("dd", new Integer(7));// dd
		jaeums.put("r", new Integer(8)); // r
		jaeums.put("l", new Integer(8)); // l
		jaeums.put("rg", new Integer(9));// rg
		jaeums.put("rm", new Integer(10));// rm
		jaeums.put("rb", new Integer(11));// rb
		jaeums.put("rs", new Integer(12));// rs
		jaeums.put("rt", new Integer(13));// rt
		jaeums.put("rp", new Integer(14));// rp
		jaeums.put("rh", new Integer(15));// rh
		jaeums.put("m", new Integer(16));// m
		jaeums.put("b", new Integer(17));// b
		jaeums.put("v", new Integer(18));// bb
		jaeums.put("bb", new Integer(18));// bb
		jaeums.put("bs", new Integer(19));// bs
		jaeums.put("s", new Integer(20));// s
		jaeums.put("ss", new Integer(21));// ss
		jaeums.put("x", new Integer(21));// x
		jaeums.put("ng", new Integer(22));// -
		jaeums.put("j", new Integer(23));// j
		jaeums.put("jj", new Integer(24));// jj
		jaeums.put("c", new Integer(25));// ch
		jaeums.put("ch", new Integer(25));// ch
		jaeums.put("k", new Integer(26));// k
		jaeums.put("t", new Integer(27));// t
		jaeums.put("p", new Integer(28));// p
		jaeums.put("h", new Integer(29));// h
		jaeums.put("f", new Integer(29));
		jaeums.put("ph", new Integer(29));

		dubs.put("q", "b");
		dubs.put("Q", "bb");
		dubs.put("w", "j");
		dubs.put("W", "jj");
		dubs.put("e", "d");
		dubs.put("E", "dd");
		dubs.put("r", "g");
		dubs.put("R", "gg");
		dubs.put("t", "s");
		dubs.put("T", "ss");

		dubs.put("y", "yo");
		dubs.put("u", "yeo");
		dubs.put("i", "ya");
		dubs.put("o", "ae");
		dubs.put("O", "yae");
		dubs.put("p", "e");
		dubs.put("P", "ye");

		dubs.put("a", "m");
		dubs.put("s", "n");
		dubs.put("d", "ng");
		dubs.put("f", "r");
		dubs.put("g", "h");

		dubs.put("h", "o");
		dubs.put("j", "eo");
		dubs.put("k", "a");
		dubs.put("l", "i");

		dubs.put("z", "k");
		dubs.put("x", "t");
		dubs.put("c", "ch");
		dubs.put("v", "p");

		dubs.put("b", "yu");
		dubs.put("n", "u");
		dubs.put("m", "eu");
	}
}