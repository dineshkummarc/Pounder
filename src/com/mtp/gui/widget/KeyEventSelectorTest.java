package com.mtp.gui.widget;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

import java.io.File;
import java.io.FileInputStream;

import java.net.URL;

import java.awt.Component;

import java.awt.event.KeyEvent;

public class KeyEventSelectorTest extends TestCase {

	public KeyEventSelectorTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(KeyEventSelectorTest.class);
	}

	public void testSetKey() throws Exception {
		KeyEventSelector kes = new KeyEventSelector();
		kes.keyListener.eventDispatched(new KeyEvent(kes, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_CAPS_LOCK, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_UNKNOWN));

		KeyEvent e = kes.getKeyEvent();
		assertTrue(e != null);
		assertEquals(e.getKeyCode(), KeyEvent.VK_CAPS_LOCK);
		assertEquals(e.getModifiersEx(), KeyEvent.SHIFT_DOWN_MASK);
	}

}
