package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class VerbatimRecordingOptionsTest extends TestCase {

	public VerbatimRecordingOptionsTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(VerbatimRecordingOptionsTest.class);
	}

	protected VerbatimRecordingOptions existing;

	public void setUp() throws Exception {
		super.setUp();
		existing = new VerbatimRecordingOptions().retrieveDataFromSystem();
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

	public void testRetrieveDataFromSystemAndSaveDataToSystem() throws Exception {
		VerbatimRecordingOptions vro = new VerbatimRecordingOptions();
		flipAll(vro);

		vro.saveDataToSystem();
				
		assertEquals(vro, new VerbatimRecordingOptions().retrieveDataFromSystem());
	}

	public void testRetrieveData() throws Exception {
		VerbatimRecordingOptions vro1 = new VerbatimRecordingOptions();
		VerbatimRecordingOptions vro2 = new VerbatimRecordingOptions();
		assertEquals(vro1, vro2);

		flipAll(vro2);		
		assertTrue(! vro1.equals(vro2));

		vro1.retrieveData(vro2);
		assertEquals(vro1, vro2);
	}

	public void testClone() {
		VerbatimRecordingOptions vro1 = new VerbatimRecordingOptions();
		flipAll(vro1);

		assertEquals(vro1, vro1.clone());
	}

}
