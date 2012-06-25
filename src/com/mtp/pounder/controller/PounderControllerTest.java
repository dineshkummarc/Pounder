package com.mtp.pounder.controller;

import com.mtp.pounder.*;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.gui.WindowWatcher;

public class PounderControllerTest extends TestCase {

	public PounderControllerTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PounderControllerTest.class);
	}

	protected WindowWatcher windowWatcher;

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}
		
	/** Assert action states when no TestInstanceFactory is present and
	 * no playing or recording is occuring. **/
	protected void checkStatusWhenTestInstanceFactoryNotPresentAndNotDoingAnything(PounderController pc) throws Exception {
		assertTrue(pc.getSetTestClassAction().isEnabled());
		assertTrue(pc.getSaveAction().isEnabled());
		assertTrue(pc.getOpenAction().isEnabled());
		assertTrue(pc.getSaveAsAction().isEnabled());
		assertTrue(pc.getAboutAction().isEnabled());
		assertTrue(pc.getEditPreferencesAction().isEnabled());
		assertTrue(pc.getViewCommentAction().isEnabled());
		assertTrue(pc.getCloseAction().isEnabled());
		assertTrue(pc.getSetFastPlaybackAction().isEnabled());
		assertTrue(pc.getSetIgnoreUnnamedAction().isEnabled());
		assertTrue(pc.getSetUseSystemClassLoaderAction().isEnabled());
		assertTrue(pc.getUpdateDefaultWindowXAction().isEnabled());
		assertTrue(pc.getUpdateDefaultWindowYAction().isEnabled());
		assertTrue(pc.getViewExceptionStackTraceAction().isEnabled());

		assertTrue(! pc.getNewInstanceAction().isEnabled());
		assertTrue(! pc.getRecordVerbatimAction().isEnabled());
		assertTrue(! pc.getPlayAction().isEnabled());
		assertTrue(! pc.getStopAction().isEnabled());
		assertTrue(! pc.getAddAssertAction().isEnabled());
		assertTrue(! pc.getPauseAction().isEnabled());
		assertTrue(! pc.getResumeAction().isEnabled());
	}

	/** Assert action states when a TestInstanceFactory is present and
	 * no playing or recording is occuring. **/
	protected void checkStatusWhenTestInstanceFactoryPresentAndNotDoingAnything(PounderController pc) throws Exception {
		assertTrue(pc.getSetTestClassAction().isEnabled());
		assertTrue(pc.getSaveAction().isEnabled());
		assertTrue(pc.getOpenAction().isEnabled());
		assertTrue(pc.getSaveAsAction().isEnabled());
		assertTrue(pc.getEditPreferencesAction().isEnabled());
		assertTrue(pc.getViewCommentAction().isEnabled());
		assertTrue(pc.getNewInstanceAction().isEnabled());
		assertTrue(pc.getRecordVerbatimAction().isEnabled());
		assertTrue(pc.getPlayAction().isEnabled());
		assertTrue(pc.getAboutAction().isEnabled());
		assertTrue(pc.getCloseAction().isEnabled());
		assertTrue(pc.getSetFastPlaybackAction().isEnabled());
		assertTrue(pc.getSetIgnoreUnnamedAction().isEnabled());
		assertTrue(pc.getSetUseSystemClassLoaderAction().isEnabled());
		assertTrue(pc.getUpdateDefaultWindowXAction().isEnabled());
		assertTrue(pc.getUpdateDefaultWindowYAction().isEnabled());
		assertTrue(pc.getAddAssertAction().isEnabled());
		assertTrue(pc.getViewExceptionStackTraceAction().isEnabled());

		assertTrue(! pc.getStopAction().isEnabled());
		assertTrue(! pc.getPauseAction().isEnabled());
		assertTrue(! pc.getResumeAction().isEnabled());
	}

	/** Assert that we can only stop or quit while playing or
	 * recording is ocurring. */
	protected void checkStatusWhilePlayingOrRecording(PounderController pc) throws Exception {
		assertTrue(pc.getStopAction().isEnabled());
		assertTrue(pc.getCloseAction().isEnabled());
		assertTrue(pc.getPauseAction().isEnabled());

		assertTrue(! pc.getResumeAction().isEnabled());
		assertTrue(! pc.getSetUseSystemClassLoaderAction().isEnabled());
		assertTrue(! pc.getSetFastPlaybackAction().isEnabled());
		assertTrue(! pc.getSetIgnoreUnnamedAction().isEnabled());
		assertTrue(! pc.getSetTestClassAction().isEnabled());
		assertTrue(! pc.getSaveAction().isEnabled());
		assertTrue(! pc.getOpenAction().isEnabled());
		assertTrue(! pc.getSaveAsAction().isEnabled());
		assertTrue(! pc.getAboutAction().isEnabled());
		assertTrue(! pc.getEditPreferencesAction().isEnabled());
		assertTrue(! pc.getViewCommentAction().isEnabled());
		assertTrue(! pc.getNewInstanceAction().isEnabled());
		assertTrue(! pc.getRecordVerbatimAction().isEnabled());
		assertTrue(! pc.getPlayAction().isEnabled());
		assertTrue(! pc.getUpdateDefaultWindowXAction().isEnabled());
		assertTrue(! pc.getUpdateDefaultWindowYAction().isEnabled());
		assertTrue(! pc.getAddAssertAction().isEnabled());
		assertTrue(! pc.getViewExceptionStackTraceAction().isEnabled());
	}

  //check status of all actions while paused
  protected void checkStatusWhilePaused(PounderController pc) throws Exception {
    assertTrue(! pc.getPlayAction().isEnabled());
    assertTrue(! pc.getRecordVerbatimAction().isEnabled());
    assertTrue(! pc.getPauseAction().isEnabled());
		assertTrue(! pc.getSetUseSystemClassLoaderAction().isEnabled());
		assertTrue(! pc.getSaveAction().isEnabled());
		assertTrue(! pc.getOpenAction().isEnabled());
		assertTrue(! pc.getSaveAsAction().isEnabled());
		assertTrue(! pc.getUpdateDefaultWindowXAction().isEnabled());
		assertTrue(! pc.getUpdateDefaultWindowYAction().isEnabled());
		assertTrue(! pc.getSetTestClassAction().isEnabled());

		assertTrue(pc.getResumeAction().isEnabled());
		assertTrue(pc.getStopAction().isEnabled());
		assertTrue(pc.getCloseAction().isEnabled());
		assertTrue(pc.getSetFastPlaybackAction().isEnabled());
		assertTrue(pc.getSetIgnoreUnnamedAction().isEnabled());
		assertTrue(pc.getAboutAction().isEnabled());
		assertTrue(pc.getEditPreferencesAction().isEnabled());
		assertTrue(pc.getViewCommentAction().isEnabled());
		assertTrue(pc.getNewInstanceAction().isEnabled());
		assertTrue(pc.getAddAssertAction().isEnabled());
		assertTrue(pc.getViewExceptionStackTraceAction().isEnabled());
  }

	protected static class PlayForeverPounderModel extends PounderModel {
		boolean playForever = false;
		public void playback() {
			playForever = true;
			super.playback();
		}
				
		public boolean isPlaying() {
			return playForever;
		}
	}

	protected static class RecordForeverPounderModel extends PounderModel {
		boolean recordForever = false;
		public void beginVerbatimRecording() {
			recordForever = true;
			super.beginVerbatimRecording();
		}
				
		public boolean isRecording() {
			return recordForever;
		}
	}

	protected PounderController buildController(PounderModel pm) {
		PounderController ret = new PounderController(pm);
		PounderFrame pf = new PounderFrame(ret);
		ret.setFrame(pf);
		return ret;
	}

  public void testStatusWhileRecordingPaused() throws Exception {
    PounderModel pm = new RecordForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.beginVerbatimRecording();
    pm.setPaused(true);

    assertTrue(pm.isPaused());
		PounderController pc = buildController(pm);

		checkStatusWhilePaused(pc);
  }

  public void testStatusWhilePlayingPaused() throws Exception {
		PounderModel pm = new PlayForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.playback();
    pm.setPaused(true);
    assertTrue(pm.isPaused());
		PounderController pc = buildController(pm);

		checkStatusWhilePaused(pc);
  }

	public void testStatusWhileRecordingBeforeFireChange() throws Exception {
		PounderModel pm = new RecordForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.beginVerbatimRecording();
		PounderController pc = buildController(pm);

		checkStatusWhilePlayingOrRecording(pc);
	}

	public void testStatusWhileRecordingAfterFireChange() throws Exception {
		PounderModel pm = new RecordForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		PounderController pc = buildController(pm);
		pm.beginVerbatimRecording();

		checkStatusWhilePlayingOrRecording(pc);
	}

	public void testStatusWhilePlayingBeforeFireChange() throws Exception {
		PounderModel pm = new PlayForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.playback();
		PounderController pc = buildController(pm);

		checkStatusWhilePlayingOrRecording(pc);
	}

	public void testStatusWhilePlayingAfterFireChange() throws Exception {
		PounderModel pm = new PlayForeverPounderModel();
		pm.setTestClass("javax.swing.JButton");
		PounderController pc = buildController(pm);
		pm.playback();

		checkStatusWhilePlayingOrRecording(pc);
	}

	/** Make sure default values are correct. **/
	public void testStatusAfterConstructor() throws Exception {
		PounderController pc = buildController(new PounderModel());

		checkStatusWhenTestInstanceFactoryNotPresentAndNotDoingAnything(pc);
	}

	public void testStatusWhileTestInstanceFactoryPresentBeforeFireChange() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		PounderController pc = buildController(pm);

		checkStatusWhenTestInstanceFactoryPresentAndNotDoingAnything(pc);
	}

	public void testStatusWhileTestInstanceFactoryNotPresentAfterFireChange() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = buildController(pm);
		pm.setTestClass("javax.swing.JButton");
		pm.setTestInstanceFactory(null);

		checkStatusWhenTestInstanceFactoryNotPresentAndNotDoingAnything(pc);
	}

	public void testStatusWhileTestInstanceFactoryPresentAfterFireChange() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = buildController(pm);
		pm.setTestClass("javax.swing.JButton");

		checkStatusWhenTestInstanceFactoryPresentAndNotDoingAnything(pc);		
	}

}
