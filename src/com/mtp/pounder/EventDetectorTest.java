package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class EventDetectorTest extends TestCase {

	public EventDetectorTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(EventDetectorTest.class);
	}

	public void testEquals() throws Exception {
		EventDetector e1 = new EventDetector();
		EventDetector e2 = new EventDetector();
		assertEquals(e1, e2);

		e1.setEndEvent(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, 0, KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_Q, KeyEvent.CHAR_UNDEFINED));
		assertTrue(! e1.equals(e2));
	}

}






