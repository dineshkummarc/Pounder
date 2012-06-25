package com.mtp.pounder;

import java.awt.Frame;
import java.awt.Window;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Responsible for handling the Recording process.

@author Matthew Pekar

**/
public class RecordingThread extends Thread {

	protected Recording recording;
	protected PounderModel model;
	protected volatile WindowWatcher windowWatcher;
	protected volatile boolean stopRequested;
	protected ClassLoader classLoader;

	public RecordingThread(PounderModel model, Recording r) {
		this.model = model;
		this.recording = r;

    initWindowWatcher();
	}

	/** Set whether stop should occur when possible. **/
	public void setStopRequested(boolean b) {
		stopRequested = b;

		//we cannot disconnect the WindowWatcher immediately,
		//but we can refuse new Window's
		if(windowWatcher != null)
			windowWatcher.setFilterAllWindows(true);

    interrupt();
	}

	protected void buildTestObject() throws Exception {
		TestInstanceFactory testInstanceFactory = model.getTestInstanceFactory();
		testInstanceFactory.showNewTestInstance(model.getTestClassLoader(), 
																						model.getPreferences(), windowWatcher);
	}

	protected void initWindowWatcher() {
		this.windowWatcher = new WindowWatcher();
		this.windowWatcher.setRemoveWindows(false);
	}

  public void setPaused(boolean b) {
    recording.setPaused(b);
  }

  public boolean isPaused() {
    return recording.isPaused();
  }

	public void run() {
		model.getStatusModel().setStatus(Strings.getString("BeginRecording"));

		try {
			buildTestObject();
		}
		catch(Exception exc) {
			model.getStatusModel().handleException(exc);
			doTermination();
			return;
		}

		recording.begin(model.getRecord(), windowWatcher);

		while(! recording.isFinished()) {
			try { 
				Thread.sleep(100);
			}
			catch(InterruptedException exc) {
			}

			if(stopRequested)
				break;
		}

		doTermination();
		model.getStatusModel().setStatus(Strings.getString("EndRecording"));
	}

	protected void doTermination() {
		recording.terminate();
		model.recordingFinished();
		if(windowWatcher != null)
			windowWatcher.disconnect(true);
	}

}
