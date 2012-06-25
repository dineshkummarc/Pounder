package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import java.io.File;

import com.mtp.pounder.controller.PounderController;

public class PounderFrameTest extends TestCase {

	public PounderFrameTest(String name) {
		super(name);
	}
	
	public static Test suite() {
		return new TestSuite(PounderFrameTest.class);
	}

	public void testFileChangeUpdatesTitle() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		PounderFrame pf = new PounderFrame(pc);
		pm.getFileModel().setFile(new File("foo.bar"));
		assertEquals("foo.bar", pf.getTitle());
	}

	private boolean dataSaved = false;
	public void testDisposeSavesPrefsAppropriately() throws Exception {
		PounderPrefs prefs = new PounderPrefs() {
				public void saveDataToSystem() {
					dataSaved = true;
				}
			};
		PounderModel model = new PounderModel(prefs);
		PounderFrame frame = new PounderFrame(model);

		dataSaved = false;
		prefs.setSavePrefsOnExit(true);
		frame.dispose();
		assertTrue(dataSaved);

		dataSaved = false;
		prefs.setSavePrefsOnExit(false);
		frame.dispose();
		assertTrue(! dataSaved);
	}

	private boolean stopCalled = false;
	public void testDisposeCallsStop() throws Exception {
		PounderModel model = new PounderModel();
		PounderController pc = new PounderController(model) {
				public AbstractAction getStopAction() {
					return new AbstractAction() {
							public void actionPerformed(ActionEvent e) {
								stopCalled = true;
							}
						};
				}
			};

		PounderFrame pf = new PounderFrame(pc);
		model.beginVerbatimRecording();
		pf.dispose();
				
		assertTrue(stopCalled);
	}

}






