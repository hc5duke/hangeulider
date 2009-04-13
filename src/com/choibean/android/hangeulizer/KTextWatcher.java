package com.choibean.android.hangeulizer;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class KTextWatcher implements TextWatcher {
	private static Pattern	vowelPattern;

	private EditText		input;
	private EditText		output;

	private static HashMap<String, Integer>	consonants, vowels, bachims,
			jaeums;

	public KTextWatcher(EditText input, EditText output) {
		this.input = input;
		this.output = output;
		setupObjects();
	}

	public void afterTextChanged(Editable e) {
	}

	public void beforeTextChanged(CharSequence cs, int a, int b, int c) {
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		try {
			String text = s.toString();
			boolean finalize = false;
			if (text.indexOf(' ') != -1)
				finalize = true;
			parseText(text, finalize);
		} catch (Exception e) {
			KStatusBar.setStatus(e.getMessage(), KStatusBar.ERROR);
		}
	}

	private void parseText(String text, boolean finalize) {
		String v = "", parts[];
		text = text.trim();
		parts = vowelPattern.split(text);
		Matcher m = vowelPattern.matcher(text);
		if (m.find()) {
			v = m.group();
		}

		if (parts.length < 1) {
			KStatusBar.setStatus("status: \"" + text + "\" is too short");
			return;
		}
		KStatusBar.setStatus("status: [" + parts[0]
				+ (parts.length > 1 ? "," + parts[1] : "-"));

		Integer consonant, vowel, bachim = null;
		vowel = vowels.get(v);
		int unicode = 44032;
		if (vowel == null) {
			unicode = 12593;
			consonant = jaeums.get(parts[0]);
			if (consonant == null) {
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

		KStatusBar.setStatus("status: [" + consonant + ", " + vowel + ", "
				+ bachim + "]");
		char c = (char) (unicode);
		KStatusBar.setStatus("status: c=" + c);

		if (finalize) {
			input.setText("");
			output.setText(output.getText().append(c));
		}
	}

	private static void setupObjects() {
		if (consonants != null)
			return;
		vowelPattern = Pattern.compile("([aeiouwy]+)");
		consonants = new HashMap<String, Integer>();
		vowels = new HashMap<String, Integer>();
		bachims = new HashMap<String, Integer>();
		jaeums = new HashMap<String, Integer>();

		consonants.put("g", new Integer(0));
		consonants.put("gg", new Integer(1));
		consonants.put("n", new Integer(2));
		consonants.put("d", new Integer(3));
		consonants.put("dd", new Integer(4));
		consonants.put("r", new Integer(5));
		consonants.put("m", new Integer(6));
		consonants.put("b", new Integer(7));
		consonants.put("bb", new Integer(8));
		consonants.put("s", new Integer(9));
		consonants.put("sh", new Integer(9));
		consonants.put("ss", new Integer(10));
		consonants.put("", new Integer(11));
		consonants.put("j", new Integer(12));
		consonants.put("jj", new Integer(13));
		consonants.put("ch", new Integer(14));
		consonants.put("k", new Integer(15));
		consonants.put("t", new Integer(16));
		consonants.put("p", new Integer(17));
		consonants.put("h", new Integer(18));
		consonants.put("gh", new Integer(0));
		consonants.put("z", new Integer(12));
		consonants.put("v", new Integer(7));
		consonants.put("f", new Integer(18));
		consonants.put("ph", new Integer(18));
		consonants.put("q", new Integer(15));
		consonants.put("rh", new Integer(11));
		consonants.put("l", new Integer(5));

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
		vowels.put("wae", new Integer(10));
		vowels.put("oi", new Integer(11));
		vowels.put("yo", new Integer(12));
		vowels.put("u", new Integer(13));
		vowels.put("oo", new Integer(13));
		vowels.put("weo", new Integer(14));
		vowels.put("wo", new Integer(14));
		vowels.put("we", new Integer(15));
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
		bachims.put("ng", new Integer(21));
		bachims.put("j", new Integer(22));
		bachims.put("ch", new Integer(23));
		bachims.put("k", new Integer(24));
		bachims.put("t", new Integer(25));
		bachims.put("p", new Integer(26));
		bachims.put("h", new Integer(27));
		bachims.put("ck", new Integer(24));

		int j = 0;
		jaeums.put("g", new Integer(j++)); // g
		jaeums.put("gg", new Integer(j++));// gg
		jaeums.put("gs", new Integer(j++));// gs
		jaeums.put("n", new Integer(j++)); // n
		jaeums.put("nj", new Integer(j++));// nj
		jaeums.put("nh", new Integer(j++));// nh
		jaeums.put("d", new Integer(j++)); // d
		jaeums.put("dd", new Integer(j++));// dd
		jaeums.put("r", new Integer(j++)); // r
		jaeums.put("rg", new Integer(j++));// rg
		jaeums.put("rm", new Integer(j++));// rm
		jaeums.put("rb", new Integer(j++));// rb
		jaeums.put("rs", new Integer(j++));// rs
		jaeums.put("rt", new Integer(j++));// rt
		jaeums.put("rp", new Integer(j++));// rp
		jaeums.put("rh", new Integer(j++));// rh
		jaeums.put("m", new Integer(j++));// m
		jaeums.put("b", new Integer(j++));// b
		jaeums.put("bb", new Integer(j++));// bb
		jaeums.put("bs", new Integer(j++));// bs
		jaeums.put("s", new Integer(j++));// s
		jaeums.put("ss", new Integer(j++));// ss
		jaeums.put("ng", new Integer(j++));// -
		jaeums.put("j", new Integer(j++));// j
		jaeums.put("jj", new Integer(j++));// jj
		jaeums.put("ch", new Integer(j++));// ch
		jaeums.put("k", new Integer(j++));// k
		jaeums.put("t", new Integer(j++));// t
		jaeums.put("p", new Integer(j++));// p
		jaeums.put("h", new Integer(j++));// h
	}
}