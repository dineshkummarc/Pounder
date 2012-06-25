package com.mtp.pounder.assrt;

import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Collection;
import java.util.Vector;

import com.mtp.pounder.RecordingItemTest;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.PlaybackException;

import com.mtp.gui.WindowWatcher;

public abstract class WindowAssertItemTest extends RecordingItemTest {

	public WindowAssertItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(WindowAssertItemTest.class);
	}

	protected WindowWatcher windowWatcher;

	protected abstract WindowAssertItem buildTestItem(int windowID);
	protected abstract WindowAssertItem buildTestItem(String windowTitle);

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}

	public void testGetWindow() throws Exception {
		WindowAssertItem item = buildTestItem(0);
		JFrame f = new JFrame();
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);
		assertEquals(f, item.getWindow(windowWatcher));

		item = buildTestItem("Bluh");
		JFrame f2 = new JFrame();
		f2.setTitle("Bluh");
		f2.setVisible(true);
		windowWatcher.waitTillWindowPresent(f2);
		assertEquals(f2, item.getWindow(windowWatcher));
	}

	public void testGetWindowByID() throws Exception {
		WindowAssertItem item = buildTestItem(0);
		JFrame f = new JFrame();
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		assertEquals(f, item.getWindowByID(windowWatcher));
	}

	public void testGetWindowByIDNotThere() throws Exception {
		WindowAssertItem item = buildTestItem(0);
		boolean thrown = false;
		try {
			item.getWindowByID(windowWatcher);
		}
		catch(PlaybackException exc) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}

	public void testGetWindowByTitle() throws Exception {
		String title = "Ugh";
		WindowAssertItem item = buildTestItem(title);
		JFrame f = new JFrame();
		f.setTitle(title);
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		assertEquals(f, item.getWindowByTitle(windowWatcher));
	}

	public void testGetWindowByTitleOnJOptionPane() throws Exception {
		WindowAssertItem item = buildTestItem("Ugh");
		JFrame f = new JFrame();
		f.setVisible(true);
		Thread t = new Thread() {
				public void run() {
					JOptionPane.showConfirmDialog(frame, "message", "Ugh", JOptionPane.YES_NO_OPTION);
				}
			};
		t.start();

		while(windowWatcher.getWindowCount() < 2) {
			Thread.sleep(100);
		}

		assertNotNull(item.getWindowByTitle(windowWatcher));
		assertTrue(! f.equals(item.getWindowByTitle(windowWatcher)));
	}

	public void testGetWindowByTitleNotThere() throws Exception {
		WindowAssertItem item = buildTestItem("Foo");
		boolean thrown = false;
		try {
			item.getWindowByTitle(windowWatcher);
		}
		catch(PlaybackException exc) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}

	public void testDifferentWindowIDEquals() throws Exception {
		assertTrue(! new WindowShowingItem(32).equals(new WindowShowingItem(33)));
	}

	public void testDifferentTypeEquals() throws Exception {
		assertTrue(! buildTestItem(32).equals(buildTestItem("FOO")));
	}

	public void testDifferentTitleEquals() throws Exception {
		assertTrue(! buildTestItem("FOO").equals(buildTestItem("BAR")));
	}

	public void testWindowIDConstructor() throws Exception {
		WindowAssertItem wai = buildTestItem(32);
		assertEquals(32, wai.windowID);
		assertEquals(null, wai.windowTitle);
	}
		
	public void testTitleConstructor() throws Exception {
		WindowAssertItem wai = buildTestItem("ATitle");
		assertEquals(-1, wai.windowID);
		assertEquals("ATitle", wai.windowTitle);
	}

}






