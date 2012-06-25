package com.mtp.gui;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Window;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.util.NoSuchElementException;
import java.util.Collection;

import com.mtp.gui.WindowWatcher;

public class WindowWatcherTest extends TestCase {

	protected JFrame frame;
	protected WindowWatcher ww, windowDisposer;

	public WindowWatcherTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(WindowWatcherTest.class);
	}

	public void setUp() throws Exception {
		super.setUp();

		ww = new WindowWatcher();
		windowDisposer = new WindowWatcher();
		frame = new JFrame();
	}

	public void tearDown() throws Exception {
		super.tearDown();

		frame.setVisible(false);
		frame.dispose();
		frame = null;
		ww.disconnect(true);
		windowDisposer.disconnect(true);
		ww = null;
	} 

	public void testConstructor() throws Exception {
		assertTrue(ww.getWindowCount() == 0);
	}

	public void testGetWindows() throws Exception {
		ww.addWindow(frame);

		assertTrue(ww.getWindows() != ww.windows);
		assertTrue(ww.getWindows() != ww.windows.keySet());

		assertEquals(1, ww.getWindows().size());
		assertTrue(ww.getWindows().contains(frame));
	}

	public void testContainsWindowByID() throws Exception {
		assertTrue(! ww.containsWindow(0));

		ww.addWindow(new JFrame());
		assertTrue(ww.containsWindow(0));
	}

	public void testSetVisibleCalledTwiceGivesSameID() throws Exception {
		frame.setVisible(true);
		ww.waitTillWindowPresent(frame);
		frame.setVisible(true);
		ww.waitTillWindowPresent(frame);

		assertEquals(1, ww.getWindowCount());		
		assertEquals(0, ww.getWindowID(frame));
	}

	public void testRemoveWindows() throws Exception {
		ww.setRemoveWindows(false);

		frame.setVisible(true);
		ww.waitTillWindowPresent(frame);
		assertEquals(1, ww.getWindowCount());
		frame.setVisible(false);

		try {
			ww.waitTillWindowNotPresent(frame, 1000);
		}
		catch(Exception exc) {
		}
		assertTrue(ww.containsWindow(frame));
	}

	public void testAddWindowTwiceMaintainsSameID() throws Exception {
		WindowWatcher ww = new WindowWatcher();
		Window w = new JFrame();

		ww.addWindow(w);
		assertEquals(0, ww.getWindowID(w));

		ww.addWindow(w);
		assertEquals(0, ww.getWindowID(w));
	}

	public void testSetFilterAllWindows() throws Exception {
		WindowWatcher wwHere = new WindowWatcher();
		wwHere.setFilterAllWindows(true);

		JFrame frame1 = new JFrame();
		frame1.setVisible(true);

		JFrame frame2 = new JFrame();
		frame2.setName("Foo");
		frame2.setVisible(true);

		assertEquals(0, wwHere.getWindowCount());
	}

	public void testWaitTillWindowPresent() throws Exception {
		Thread buildWindowSlowly = new Thread() {
				JFrame myFrame = frame;
				public void run() {
					try {
						Thread.sleep(500);
					}
					catch(Exception exc) {
						throw new RuntimeException(exc);
					}
					myFrame.setVisible(true);
				}
			};

		buildWindowSlowly.start();
		ww.waitTillWindowPresent(frame);
		assertTrue(ww.containsWindow(frame));
	}

	public void testWaitTillWindowNotPresent() throws Exception {
		Thread closeWindowThread = new Thread() {
				JFrame myFrame = frame;
				public void run() {
					myFrame.setVisible(false);
				}
			};

		frame.setVisible(true);
		ww.waitTillWindowPresent(frame);
		assertTrue(ww.containsWindow(frame));

		closeWindowThread.start();
		ww.waitTillWindowNotPresent(frame, 2000);
		assertTrue(! ww.containsWindow(frame));
	}

	public void testFilterRemovesFilteredWindows() throws Exception {
		JFrame frame1 = new JFrame();
		frame1.setVisible(true);

		JFrame frame2 = new JFrame();
		frame2.setName("Foo");
		frame2.setVisible(true);

		ww.waitTillWindowPresent(frame1);
		ww.waitTillWindowPresent(frame2);

		ww.filterWindow(frame1.hashCode());
		ww.filterWindow(frame2.getName());
		assertEquals(0, ww.getWindowCount());
	}

	public void testFilterWindowByHashCode() throws Exception {
		JFrame frame1 = new JFrame();
		ww.filterWindow(frame1.hashCode());
		frame1.setVisible(true);

		try {
			ww.waitTillWindowPresent(frame1, 500);
		}
		catch(Exception exc) {
			//should fail
		}

		assertEquals(0, ww.getWindowCount());
	}

	public void testGetWindowByName() throws Exception {
		JFrame frame1 = new JFrame();
		frame1.setVisible(true);
		frame1.setName("Foo");

		JFrame frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setName("Bar");

		ww.waitTillWindowPresent(frame1);
		ww.waitTillWindowPresent(frame2);

		assertEquals(frame1, ww.getWindowByName("Foo"));
		assertEquals(frame2, ww.getWindowByName("Bar"));
		assertEquals(null, ww.getWindowByName("NonExistantWindow"));
	}

	public void testGetWindowByTitle() throws Exception {
		JFrame frame1 = new JFrame();
		frame1.setVisible(true);
		frame1.setTitle("Foo");

		JFrame frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setTitle("Bar");

		ww.waitTillWindowPresent(frame1);
		ww.waitTillWindowPresent(frame2);

		assertEquals(frame1, ww.getWindowByTitle("Foo"));
		assertEquals(frame2, ww.getWindowByTitle("Bar"));
		assertEquals(null, ww.getWindowByName("NonExistantWindow"));
	}

	public void testGetWindowByID() throws Exception {
		JFrame frame1 = new JFrame();
		frame1.setVisible(true);

		JFrame frame2 = new JFrame();
		frame2.setVisible(true);

		ww.waitTillWindowPresent(frame1);
		ww.waitTillWindowPresent(frame2);

		assertEquals(frame1, ww.getWindowByID(0));
		assertEquals(frame2, ww.getWindowByID(1));
		assertEquals(null, ww.getWindowByID(2));
	}

	/** Test not guaranteed. **/
	public void testRemovalDoesNotChangeIndex() throws Exception {
		JFrame frame1 = new JFrame();
		frame1.setVisible(true);

		JFrame frame2 = new JFrame();
		frame2.setVisible(true);

		ww.waitTillWindowPresent(frame1);
		ww.waitTillWindowPresent(frame2);

		assertEquals(frame2, ww.getWindowByID(1));

		frame1.dispose();
		frame1 = null;

		ww.waitTillWindowNotPresent(frame1);

		assertEquals(frame2, ww.getWindowByID(1));
	}

	/** Test not guaranteed. **/
	public void testWindowClosedDetected() throws Exception {
		JFrame frame = new JFrame();
		frame.setName("Foo");
		frame.show();

		ww.waitTillWindowPresent(frame);
		assertEquals(frame, ww.getWindowByID(0));

		frame.hide();
		frame.dispose();
		frame = null;
		ww.waitTillWindowNotPresent(frame);

		assertEquals(null, ww.getWindowByID(0));

		ww.disconnect(true);
	}

	public void testDisposeRemovesWindow() throws Exception {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		ww.waitTillWindowPresent(frame);
		assertEquals(1, ww.getWindowCount());

		frame.dispose();
		ww.waitTillWindowNotPresent(frame);
		assertEquals(0, ww.getWindowCount());
	}

}






