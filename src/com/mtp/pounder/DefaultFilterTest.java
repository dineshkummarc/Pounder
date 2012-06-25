package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.AWTEvent; 

import java.awt.event.TextEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.mtp.gui.WindowWatcher;

public class DefaultFilterTest extends TestCase implements PounderConstants {

	protected JFrame outsideWindow;
	protected WindowWatcher windowWatcher;
	protected DefaultFilter filter;

	public DefaultFilterTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DefaultFilterTest.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		outsideWindow = new JFrame();
		windowWatcher = new WindowWatcher();
		filter = new DefaultFilter(windowWatcher);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		filter = null;
		windowWatcher.disconnect(true);
		outsideWindow.dispose();
		outsideWindow = null;
	}

	public void testRejectsNonComponentSource() throws Exception {
		AWTEvent event = new TextEvent(this, TextEvent.TEXT_VALUE_CHANGED);
		assertTrue(! filter.accepts(event));
	}

	public void testRejectsOutsideWindow() throws Exception {
		AWTEvent event = new TextEvent(outsideWindow, TextEvent.TEXT_VALUE_CHANGED);
		assertTrue(! filter.accepts(event));
	}

	public void testAcceptsInsideWindow() throws Exception {
		JFrame f = new JFrame();
		JTextField tf = new JTextField();
		f.getContentPane().add(tf);
		f.setVisible(true);

		windowWatcher.waitTillWindowPresent(f);
		AWTEvent event = new TextEvent(tf, TextEvent.TEXT_VALUE_CHANGED);
		assertTrue(filter.accepts(event));
	}

}
