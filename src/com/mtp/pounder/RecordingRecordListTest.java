package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.DefaultListModel;

import com.mtp.pounder.controller.PounderController;

public class RecordingRecordListTest extends TestCase {

	public RecordingRecordListTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(RecordingRecordListTest.class);
	}

	public void testAddSelectsBottomElement() throws Exception {
		PounderModel model = new PounderModel();
		RecordingRecord record = model.getRecord();
		RecordingRecordList rrl = new RecordingRecordList(new PounderController(model));
		record.addItem(new DummyRecordingItem());
		record.addItem(new DummyRecordingItem());
		assertEquals(1, rrl.getSelectedIndex());
	}

}
