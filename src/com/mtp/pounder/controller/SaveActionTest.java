package com.mtp.pounder.controller;

import com.mtp.pounder.*;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class SaveActionTest extends TestCase implements PounderConstants {

	public SaveActionTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(SaveActionTest.class);
	}

	private boolean fileChooserDisplayed = false;
	public void testSaveWithNoFileDisplaysFileChooserWidget() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		SaveAction sa = new SaveAction(pc, pm) {
				protected File showFileChooser() {
					fileChooserDisplayed = true;
					return null;
				}
			};

		sa.actionPerformed(null);
		assertTrue(fileChooserDisplayed);
	}

	public void testFixExtension() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		SaveAction sa = new SaveAction(pc, pm);

		File f = File.createTempFile("bluh", FILE_EXTENSION);
		f.deleteOnExit();
		assertEquals(f, sa.fixExtension(f));

		File xml = File.createTempFile("bluh", ".xml");
		xml.deleteOnExit();
		assertEquals(xml, sa.fixExtension(xml));
				
		File bad = File.createTempFile("bluh", ".html");
		bad.deleteOnExit();
		assertTrue(sa.fixExtension(bad).getName().endsWith(FILE_EXTENSION));
	}

	public void testSave() throws Exception {
		PounderModel pm = new PounderModel();
		pm.getRecord().addItem(new DummyRecordingItem());
		PounderController pc = new PounderController(pm);
		SaveAction sa = new SaveAction(pc, pm);
		File f = File.createTempFile("bluh", FILE_EXTENSION);
		f.deleteOnExit();
		pm.getFileModel().setFile(f);
		sa.actionPerformed(null);

		assertEquals(pm, new PounderReader().readModel(f));
		assertTrue(! pm.isSaveNeeded());
	}

}
