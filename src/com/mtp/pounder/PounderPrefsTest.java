package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Point;

import java.io.File;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class PounderPrefsTest extends TestCase {

	public PounderPrefsTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PounderPrefsTest.class);
	}

	protected PounderPrefs existing;

	public void setUp() throws Exception {
		super.setUp();
		existing = new PounderPrefs().retrieveDataFromSystem();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		existing.saveDataToSystem();
	}

	protected void flipAll(VerbatimRecordingOptions vro) {
		vro.doKeyEvents = ! vro.doKeyEvents;
		vro.doMouseInputEvents = ! vro.doMouseInputEvents;
		vro.doMouseMotionEvents = ! vro.doMouseMotionEvents;
		vro.doWindowEvents = ! vro.doWindowEvents;
		vro.doMouseDragEvents = ! vro.doMouseDragEvents;
	}

	protected PounderPrefs getNonDefaultInstance() {
		PounderPrefs ret = new PounderPrefs();

		Point dtwl = ret.getDefaultTestWindowLocation().getValue();
		ret.getDefaultTestWindowLocation().setX(dtwl.x + 1);
		ret.getDefaultTestWindowLocation().setY(dtwl.y + 1);
		flipAll(ret.getVerbatimRecordingOptions());
		ret.setDisplayScript(! ret.getDisplayScript());
		ret.setUseSystemClassLoader(! ret.getUseSystemClassLoader());
		ret.setFastPlaybackEnabled(! ret.getFastPlaybackEnabled());
		ret.setSavePrefsOnExit(! ret.getSavePrefsOnExit());
		ret.setItemDelayEnabled(! ret.getItemDelayEnabled());
		ret.setIgnoreUnnamed(! ret.getIgnoreUnnamed());
		ret.setPlaybackAttempts(ret.getPlaybackAttempts() + 1);
		ret.setFailedPlaybackDelay(ret.getFailedPlaybackDelay() + 1);
		ret.setHomeDirectory(new File("wooofs oafas doassdoas df"));

		return ret;
	}

	public void testRetrieveDataFromSystemAndSaveDataToSystem() throws Exception {
		PounderPrefs pp = getNonDefaultInstance();
		pp.saveDataToSystem();
				
		assertEquals(pp, new PounderPrefs().retrieveDataFromSystem());
	}

	public void testCopyConstructorAndRetrieveData() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		assertEquals(p1, new PounderPrefs(p1));
	}

	public void testEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();

		assertEquals(p1, p2);
	}

	public void testDifferentIgnoreUnnamedEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setIgnoreUnnamed(! p1.getIgnoreUnnamed());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentSavePrefsOnExitEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setSavePrefsOnExit(! p1.getSavePrefsOnExit());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentHomeDirectoriesEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setHomeDirectory(new File("clearlynotavlaiddirectory"));
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentFailedPlaybackDelayEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setPlaybackOptions(p2.getItemDelayEnabled(), p2.getPlaybackAttempts(), p2.getFailedPlaybackDelay() + 1);
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentPlaybackAttemptsEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setPlaybackOptions(p2.getItemDelayEnabled(), p2.getPlaybackAttempts() + 1, p2.getFailedPlaybackDelay());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentItemDelayEnabledEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setPlaybackOptions(! p2.getItemDelayEnabled(), p2.getPlaybackAttempts(), p2.getFailedPlaybackDelay());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentFastPlaybackEnabledEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setFastPlaybackEnabled(! p2.getFastPlaybackEnabled());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentDisplayScriptEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setDisplayScript(! p2.getDisplayScript());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentUseSystemClassLoaderEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
				
		p2.setUseSystemClassLoader(! p2.getUseSystemClassLoader());
		assertTrue(! p1.equals(p2));
	}

	public void testDifferentVerbatimRecordingOptionsEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();

		VerbatimRecordingOptions vro = new VerbatimRecordingOptions();
		vro.doKeyEvents = ! p1.getVerbatimRecordingOptions().doKeyEvents;

		p2.setVerbatimRecordingOptions(vro);

		assertTrue(! p1.equals(p2));
	}

	public void testDifferentDefaultTestWindowLocationEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
		p2.getDefaultTestWindowLocation().setX(3423);
		p2.getDefaultTestWindowLocation().setY(2819);

		assertTrue(! p1.equals(p2));
	}

	public void testDifferentPointsEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
		p2.getDefaultTestWindowLocation().setX(5);
		p2.getDefaultTestWindowLocation().setY(77);

		assertTrue(! p1.equals(p2));
	}

	public void testDifferentEventDetectorsEquals() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();
		p2.getEventDetector().setEndEvent(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_P, KeyEvent.CHAR_UNDEFINED));

		assertTrue(! p1.equals(p2));
	}

	public void testRetrieveData() throws Exception {
		PounderPrefs p1 = new PounderPrefs();
		PounderPrefs p2 = new PounderPrefs();

		assertEquals(p1, p2);

		p2.setPlaybackOptions(false, 12238, 98281);
		p2.setDisplayScript(false);
		p2.getDefaultTestWindowLocation().setX(3342);
		p2.getDefaultTestWindowLocation().setY(1289);

		assertTrue(! p1.equals(p2));

		p1.retrieveData(p2);
		assertEquals(p1, p2);
	}

}






