package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JFrame;

import java.awt.Panel;

import org.w3c.dom.Node;

import com.mtp.gui.WindowWatcher;

public class PlaybackThreadTest extends TestCase {

	protected boolean playbackFinishedCalled = false;

	public PlaybackThreadTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PlaybackThreadTest.class);
	}

	public void testSetStopRequested() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		final PlaybackThread pbt = new PlaybackThread(pm, getClass().getClassLoader());

		RecordingRecord record = pm.getRecord();
		CheckPlayedItem cpi = new CheckPlayedItem(0);
		record.addItem(cpi);

		RecordingItem doStopItem = new DummyRecordingItem() {
				PlaybackThread playbackThread = pbt;
				public void playback(WindowWatcher ww, PounderPrefs prefs) {
					playbackThread.setStopRequested(true);
				}
			};
		record.addItem(doStopItem);

		CheckPlayedItem cpi2 = new CheckPlayedItem(0);
		record.addItem(cpi2);
				
		pbt.start();
		pbt.join();

		assertTrue(cpi.played);
		assertTrue(! cpi2.played);
	}

	public void testPlaybackFinishedCalledOnError() throws Exception {
		playbackFinishedCalled = false;
		PounderModel pm = new PounderModel() {
				public void playbackFinished() {
					playbackFinishedCalled = true;
				}
			};
		pm.getPreferences().setItemDelayEnabled(false);
		pm.getPreferences().setPlaybackAttempts(0);
		pm.setTestClass("javax.swing.JButton");

		RecordingRecord record = pm.getRecord();
		record.addItem(new MisbehavingItem());

		PlaybackThread pbt = new PlaybackThread(pm, getClass().getClassLoader());
		pbt.start();
		pbt.join();
		assertTrue(playbackFinishedCalled);
	}

}






