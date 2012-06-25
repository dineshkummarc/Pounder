package com.mtp.pounder.controller;

import com.mtp.pounder.*;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import com.mtp.i18n.Strings;

/**

Controller for managing the actions and updating whether they are
enabled.

@author Matthew Pekar

**/
public class PounderController implements PounderModelListener {

	private PounderFileChooser fileChooser;
	private PounderModel model;
	private PounderFrame frame;
	private AbstractAction setTestClassAction, saveAction, openAction, saveAsAction, editPreferencesAction, viewCommentAction, newInstanceAction, recordVerbatimAction, playAction, stopAction, aboutAction, closeAction, setFastPlaybackAction, setUseSystemClassLoaderAction, updateDefaultWindowXAction, updateDefaultWindowYAction, addAssertAction, viewExceptionStackTraceAction, setIgnoreUnnamedAction, pauseAction, resumeAction;

	public PounderController(PounderModel pm) {
		this.model = pm;
		this.model.addListener(this);

		initActions();
	}

	public PounderModel getModel() {
		return model;
	}

	public synchronized void setFrame(PounderFrame f) {
		this.frame = f;
	}

	public synchronized PounderFrame getFrame() {
		return frame;
	}

	public void pounderModelChanged() {
		updateActionStates();
	}

	public PounderFileChooser getFileChooser() {
		if(fileChooser == null)
			fileChooser = new PounderFileChooser(model.getPreferences());

		return fileChooser;
	}

	protected void updateActionStates() {
		boolean playing = model.isPlaying();
		boolean recording = model.isRecording();
    boolean paused = model.isPaused();
		boolean testClassSet = model.isTestClassSet();
		boolean recordingOrPlaying = recording || playing;
		boolean notRecordingOrPlaying = ! recordingOrPlaying;
		boolean recordingOrPlayingAndPaused = (recording || playing) && paused;
		boolean recordingOrPlayingAndNotPaused = (recording || playing) && (! paused);
    boolean notRecordingOrPlayingOrPaused = (! (recording || playing)) || paused;

    pauseAction.setEnabled(recordingOrPlayingAndNotPaused);

    resumeAction.setEnabled(paused);

		playAction.setEnabled((! recordingOrPlaying) && testClassSet);
		recordVerbatimAction.setEnabled((! recordingOrPlaying) && testClassSet);

		newInstanceAction.setEnabled(((! recordingOrPlaying) && testClassSet) || paused);

		stopAction.setEnabled(recordingOrPlaying);

		openAction.setEnabled(notRecordingOrPlaying);
		saveAction.setEnabled(notRecordingOrPlaying);
		saveAsAction.setEnabled(notRecordingOrPlaying);
		setUseSystemClassLoaderAction.setEnabled(notRecordingOrPlaying);
		setTestClassAction.setEnabled(notRecordingOrPlaying);
		updateDefaultWindowXAction.setEnabled(notRecordingOrPlaying);
		updateDefaultWindowYAction.setEnabled(notRecordingOrPlaying);

		aboutAction.setEnabled(notRecordingOrPlayingOrPaused);
		addAssertAction.setEnabled(notRecordingOrPlayingOrPaused && testClassSet);
		setIgnoreUnnamedAction.setEnabled(notRecordingOrPlayingOrPaused);
		setFastPlaybackAction.setEnabled(notRecordingOrPlayingOrPaused);
		editPreferencesAction.setEnabled(notRecordingOrPlayingOrPaused);
		viewCommentAction.setEnabled(notRecordingOrPlayingOrPaused);
		viewExceptionStackTraceAction.setEnabled(notRecordingOrPlayingOrPaused);
	}

	protected void initActions() {
		setTestClassAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};

		saveAction = new SaveAction(this, model);

		openAction = new OpenAction(this, model);

		saveAsAction = new SaveAsAction(this, model);

		closeAction = new CloseAction(this, model);

		editPreferencesAction = new EditPreferencesAction(this, model);

		viewCommentAction = new ViewCommentAction(this, model);

		newInstanceAction = new NewInstanceAction(this, model);

		recordVerbatimAction = new RecordVerbatimAction(this, model);

		playAction = new PlayAction(this, model);

    pauseAction = new PauseAction(model);

    resumeAction = new ResumeAction(model);

		stopAction = new StopAction(this, model);

		aboutAction = new AboutAction(this, model);

		String fastPlaybackString = Strings.getString("FastPlayback");
		setFastPlaybackAction = new AbstractAction(fastPlaybackString) {
				public void actionPerformed(ActionEvent e) {
				}
			};

		String ignoreUnnamedString = Strings.getString("IgnoreUnnamed");
		setIgnoreUnnamedAction = new AbstractAction(ignoreUnnamedString) {
				public void actionPerformed(ActionEvent e) {
				}
			};

		String systemClassLoaderString = Strings.getString("SystemClassLoader");
		setUseSystemClassLoaderAction = new AbstractAction(systemClassLoaderString) {
				public void actionPerformed(ActionEvent e) {
				}
			};

		updateDefaultWindowXAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};

		updateDefaultWindowYAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};
		
		String assertString = Strings.getString("Assert");
		addAssertAction = new AbstractAction(assertString) {
				public void actionPerformed(ActionEvent e) {
				}
			};

		viewExceptionStackTraceAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};

		updateActionStates();
	}

	public AbstractAction getViewExceptionStackTraceAction() {
		return viewExceptionStackTraceAction;
	}

	public AbstractAction getAddAssertAction() {
		return addAssertAction;
	}

	public AbstractAction getUpdateDefaultWindowXAction() {
		return updateDefaultWindowXAction;
	}

	public AbstractAction getUpdateDefaultWindowYAction() {
		return updateDefaultWindowYAction;
	}

	public AbstractAction getSetUseSystemClassLoaderAction() {
		return setUseSystemClassLoaderAction;
	}

	public AbstractAction getSetFastPlaybackAction() {
		return setFastPlaybackAction;
	}

	public AbstractAction getSetIgnoreUnnamedAction() {
		return setIgnoreUnnamedAction;
	}

	public AbstractAction getCloseAction() {
		return closeAction;
	}

	public AbstractAction getSetTestClassAction() {
		return setTestClassAction;
	}

	public AbstractAction getSaveAction() {
		return saveAction;
	}

	public AbstractAction getOpenAction() {
		return openAction;
	}

	public AbstractAction getSaveAsAction() {
		return saveAsAction;
	}

	public AbstractAction getAboutAction() {
		return aboutAction;
	}

	public AbstractAction getEditPreferencesAction() {
		return editPreferencesAction;
	}

	public AbstractAction getViewCommentAction() {
		return viewCommentAction;
	}

	public AbstractAction getNewInstanceAction() {
		return newInstanceAction;
	}

	public AbstractAction getRecordVerbatimAction() {
		return recordVerbatimAction;
	}

	public AbstractAction getPlayAction() {
		return playAction;
	}

	public AbstractAction getPauseAction() {
		return pauseAction;
	}

	public AbstractAction getResumeAction() {
		return resumeAction;
	}

	public AbstractAction getStopAction() {
		return stopAction;
	}

}
