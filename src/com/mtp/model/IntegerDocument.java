package com.mtp.model;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**

Document for editing integer values, positive and negative.

@author Matthew Pekar

**/
public class IntegerDocument extends PlainDocument {
		
	public IntegerDocument() {
	}

	public void insertString(int offs, String s) throws BadLocationException {
		insertString(offs, s, null);
	}

	public int getValue() {
		try {
			return Integer.valueOf(getText(0, getLength())).intValue();
		}
		catch(BadLocationException e) {
			return -1;
		}
	}
		
	public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
		try {
			Integer i = Integer.valueOf(s);
		}
		catch(NumberFormatException ugh) {
			return;
		}

		super.insertString(offs, s, a);
	}

}
