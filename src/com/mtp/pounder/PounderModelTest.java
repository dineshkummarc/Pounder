package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Point;

import org.w3c.dom.Node;

import java.io.File;

public class PounderModelTest extends TestCase {

	public PounderModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PounderModelTest.class);
	}

	protected PounderModel buildInstance() {
		return new PounderModel(false);
	}

	public void testGetClassLoader() throws Exception {
		PounderModel pm = buildInstance();
				
		assertTrue(pm.getTestClassLoader() instanceof DynamicClassLoader);

		pm.getPreferences().setUseSystemClassLoader(true);

		assertTrue(! (pm.getTestClassLoader() instanceof DynamicClassLoader));
	}


	public void testStartup() throws Exception {
		PounderModel pm = buildInstance();
	}

	public void testEquals() throws Exception {
		PounderModel m1 = buildInstance();
		PounderModel m2 = buildInstance();
		assertEquals(m1, m2);
	}

	public void testIsSaveNeeded() throws Exception {
		//empty model, no save needed
		PounderModel pm = buildInstance();
		assertTrue(! pm.isSaveNeeded());

		//if startup class exists, save is needed
		pm.setTestClass("javax.swing.JButton");
		assertTrue(pm.isSaveNeeded());

		//opened file, no save needed
		new PounderReader().readToModel(pm, new File("testFiles/simpleButtonClick.pnd"));
		assertTrue(! pm.isSaveNeeded());

		//adding a new item requires a save
		DummyRecordingItem item = new DummyRecordingItem();
		pm.getRecord().addItem(item);
		assertTrue(pm.isSaveNeeded());

		//removing the item should make the models equal again, no save required
		pm.getRecord().removeItem(item);
		assertTrue(! pm.isSaveNeeded());
	}

	public void testCommentsDifferentNotEquals() throws Exception {
		PounderModel m1 = buildInstance();
		PounderModel m2 = buildInstance();

		m1.getComment().insertString(0, "Foo", null);
		m2.getComment().insertString(0, "Bar", null);
				
		assertTrue(! m1.equals(m2));
	}

	public void testRecordsDifferentNotEquals() throws Exception {
		PounderModel m1 = buildInstance();
		PounderModel m2 = buildInstance();
				
		m1.getRecord().addItem(new DummyRecordingItem());
		assertTrue(! m1.equals(m2));
	}

	public void testTestInstanceFactorysDifferentNotEquals() throws Exception {
		PounderModel m1 = buildInstance();
		PounderModel m2 = buildInstance();
				
		m1.setTestClass("javax.swing.JButton");
		assertTrue(! m1.equals(m2));
	}

	public void testSameTestInstanceFactorysEquals() throws Exception {
		PounderModel m1 = buildInstance();
		PounderModel m2 = buildInstance();
				
		m1.setTestClass("javax.swing.JButton");
		m2.setTestClass("javax.swing.JButton");
		assertEquals(m1, m2);
	}

	public void testSetTestClassClearsRecording() throws Exception {
		PounderModel pm = buildInstance();
		assertEquals(0, pm.getRecord().size());

		pm.getRecord().addItem(new DummyRecordingItem());
		assertEquals(1, pm.getRecord().size());

		pm.setTestClass("javax.swing.JButton");
		assertEquals(0, pm.getRecord().size());
	}

	public void testIsRecording() throws Exception {
		PounderModel pm = buildInstance();
		assertTrue(! pm.isRecording());

		pm.setTestClass("javax.swing.JButton");
		pm.beginVerbatimRecording();
		assertTrue(pm.isRecording());
	}

	public void testIsPlaying() throws Exception {
		PounderModel pm = buildInstance();
		assertTrue(! pm.isPlaying());

		pm.setTestClass("javax.swing.JButton");
		pm.playback();
		assertTrue(pm.isPlaying());
	}

	public void testStopOnRecording() throws Exception {
		PounderModel pm = buildInstance();
		pm.setTestClass("javax.swing.JButton");
		pm.beginVerbatimRecording();
		pm.stop();
		assertTrue(! pm.isRecording());
	}

	public void testStopOnPlayback() throws Exception {
		PounderModel pm = buildInstance();
		pm.setTestClass("javax.swing.JButton");
		pm.playback();
		pm.stop();
		assertTrue(! pm.isPlaying());
	}

}






