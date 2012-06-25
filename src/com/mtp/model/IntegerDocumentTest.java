package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.text.BadLocationException;

public class IntegerDocumentTest extends TestCase {

	public IntegerDocumentTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(IntegerDocumentTest.class);
	}

	public void testOnlyIntCharsAllowed() throws BadLocationException {
		IntegerDocument id = new IntegerDocument();
		id.insertString(0, "abzdfsd.,m./kl");

		assertEquals(0, id.getValue());
	}

	public void testGetValue() throws BadLocationException {
		IntegerDocument id = new IntegerDocument();
		id.insertString(0, "123456789", null);
		assertEquals(123456789, id.getValue());
				
		id = new IntegerDocument();
		id.insertString(0, "-123456789", null);
		assertEquals(-123456789, id.getValue());
	}

	public void testGetValueNullStringReturnsNegativeOne() throws BadLocationException {
		IntegerDocument id = new IntegerDocument();
		assertEquals(-1, id.getValue());
	}

}
