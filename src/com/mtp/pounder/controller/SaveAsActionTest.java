package com.mtp.pounder.controller;

import com.mtp.pounder.*;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class SaveAsActionTest extends TestCase implements PounderConstants {

	public SaveAsActionTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(SaveAsActionTest.class);
	}

	private boolean fileChooserDisplayed = false;
	public void testAlwaysDisplaysFileChooser() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		SaveAsAction sa = new SaveAsAction(pc, pm) {
				protected File showFileChooser() {
					fileChooserDisplayed = true;
					return null;
				}
			};

		//displayed for null File
		sa.actionPerformed(null);
		assertTrue(fileChooserDisplayed);

		fileChooserDisplayed = false;
				
		//displayed even if File exists
		File f = File.createTempFile("bluh", FILE_EXTENSION);
		pm.getFileModel().setFile(f);
		sa.actionPerformed(null);
		assertTrue(fileChooserDisplayed);
	}

	private File testFile;
	public void testSaveAs() throws Exception {
		testFile = File.createTempFile("bluh", FILE_EXTENSION);
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		SaveAsAction sa = new SaveAsAction(pc, pm) {
				public File getFileForWriting() {
					return testFile;
				}
			};
		sa.actionPerformed(null);

		assertEquals(pm, new PounderReader().readModel(testFile));
	}

}
