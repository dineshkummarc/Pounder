package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;

public class RecordingRecordTest extends TestCase {

	public RecordingRecordTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(RecordingRecordTest.class);
	}

	public void testAddAssertionWhenSelectionMessedUp() throws Exception {
		RecordingRecord rr = new RecordingRecord();
		for(int i=0;i < 10;i++) {
			rr.addElement(new DummyRecordingItem());
		}
		rr.selectionModel.setSelectionInterval(0, 9);
		for(int i=0;i < 10;i++) {
			rr.removeElementAt(0);
		}

		rr.addAssertion(new DummyRecordingItem());
	}

	public void testEquals() throws Exception {
		RecordingRecord r1 = new RecordingRecord();
		RecordingRecord r2 = new RecordingRecord();

		assertEquals(r1, r2);

		r1.addItem(new DummyRecordingItem());
		r2.addItem(new DummyRecordingItem());
		assertEquals(r1, r2);

		r1.addItem(new DummyRecordingItem(67));
		r2.addItem(new DummyRecordingItem(67));
		assertEquals(r1, r2);

		r1.addItem(new DummyRecordingItem(80));
		r2.addItem(new DummyRecordingItem(67));
		assertTrue(! r1.equals(r2));
	}

	public void testDifferenLengthsEquals() {
		RecordingRecord r1 = new RecordingRecord();
		RecordingRecord r2 = new RecordingRecord();

		r1.addItem(new DummyRecordingItem());
		assertTrue(! r1.equals(r2));
	}

}
