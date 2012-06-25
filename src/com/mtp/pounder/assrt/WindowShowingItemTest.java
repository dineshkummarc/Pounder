package com.mtp.pounder.assrt;

import javax.swing.JOptionPane;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Collection;
import java.util.Vector;

import com.mtp.pounder.RecordingItemTest;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.PlaybackException;

import com.mtp.gui.WindowWatcher;

public class WindowShowingItemTest extends WindowAssertItemTest {

	public WindowShowingItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(WindowShowingItemTest.class);
	}

	protected WindowAssertItem buildTestItem(int windowID) {
		return new WindowShowingItem(windowID);
	}
	protected WindowAssertItem buildTestItem(String windowTitle) {
		return new WindowShowingItem(windowTitle);
	}

	public Collection buildTestItems() throws Exception {
		Collection ret = new Vector();
		ret.add(buildTestItem(32));
		ret.add(buildTestItem("Foo"));
		return ret;
	}

	public void testPlaybackUsingWindowIDExceptionThrown() throws Exception {
		boolean playbackExceptionThrown = false;
		try {
			new WindowShowingItem(32).playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

	public void testPlaybackUsingWindowID() throws Exception {
		frame.setVisible(true);
		windowWatcher.waitTillWindowPresent(frame);

		new WindowShowingItem(0).playback(windowWatcher, new PounderPrefs());
	}

	public void testPlaybackUsingTitleExceptionThrown() throws Exception {
		boolean playbackExceptionThrown = false;
		try {
			new WindowShowingItem("TheTitle").playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

	public void testPlaybackUsingWindowTitle() throws Exception {
		frame.setVisible(true);
		frame.setTitle("TheTitle");
		windowWatcher.waitTillWindowPresent(frame);

		new WindowShowingItem("TheTitle").playback(windowWatcher, new PounderPrefs());
	}

	public void testPlaybackUsingWindowTitleOnJOptionPane() throws Exception {
		frame.setVisible(true);
		frame.setTitle("TheTitle");
		windowWatcher.waitTillWindowPresent(frame);
		Thread t = new Thread() {
				public void run() {
					JOptionPane.showConfirmDialog(frame, "Changing Class Will Delete Recorded Script.  OK?", "Delete Script?", JOptionPane.YES_NO_OPTION);
				}
			};
		t.start();

		while(windowWatcher.getWindowCount() < 2) {
			Thread.sleep(100);
		}

		new WindowShowingItem("TheTitle").playback(windowWatcher, new PounderPrefs());
		new WindowShowingItem("Delete Script?").playback(windowWatcher, new PounderPrefs());
	} 

}






