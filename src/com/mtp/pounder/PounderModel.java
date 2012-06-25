package com.mtp.pounder;

import java.awt.Component;
import java.awt.Window;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;

import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

import java.io.File;
import java.io.FileInputStream;

import java.util.Iterator;
import java.util.Enumeration;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.mtp.model.StatusModel;
import com.mtp.model.FileModel;
import com.mtp.model.Listeners;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Keeps track of the current pounding taking place.  This includes the
list of actions needed for the pounding, the component to be test
pounded, and possibly an XML file for output.

@author Matthew Pekar

**/
public class PounderModel implements PounderConstants {

	protected RecordingThread recordingThread;
	protected RecordingRecord record;
	protected StatusModel statusModel;
	protected PlaybackThread playbackThread;
	protected TestInstanceFactory testInstanceFactory;
	protected PounderPrefs preferences;
	protected DefaultComboBoxModel loadedTestObjects;
	protected DefaultBoundedRangeModel progressModel;
	protected PlainDocument comment;
	protected Listeners listeners;
	protected FileModel fileModel;

	/** Default constructor.  Calls other constructor with new
	 * PounderPrefs. **/
	public PounderModel() {
		this(false);
	}

	/** Loads data from system as specified. **/
	public PounderModel(boolean loadDataFromSystem) {
		this(loadDataFromSystem ? new PounderPrefs().retrieveDataFromSystem() : new PounderPrefs());
	}

	/** Initialize with given PounderPrefs. **/
	public PounderModel(PounderPrefs preferences) {
		if(preferences == null)
			throw new IllegalArgumentException("Preferences Cannot Be Null");

		this.preferences = preferences;
		this.recordingThread = null;
		this.record = new RecordingRecord();
		this.statusModel = new StatusModel();
		this.statusModel.setStatus(Strings.getString("Welcome"));
		this.playbackThread = null;
		this.testInstanceFactory = null;
		this.loadedTestObjects = new DefaultComboBoxModel();
		this.progressModel = new DefaultBoundedRangeModel();
		this.comment = new PlainDocument();
		this.listeners = new Listeners();
		this.fileModel = new FileModel();

		try {
			this.comment.insertString(0, Strings.getString("CreatedWithPounderVersion") + VERSION, null);
		}
		catch(BadLocationException exc) {
			throw new RuntimeException("Error initializing PounderModel comment", exc);
		}
	}

	/** Return true if a save is needed.  This is true if the current
	 * file is null and no data exists, or if the data in the file
	 * doesn't match what is present. **/
	public boolean isSaveNeeded() {
		if(fileModel.getFile() == null) {
			return testInstanceFactory != null;
		}

		try {
			PounderModel onFile = new PounderReader().readModel(fileModel.getFile());
			return ! onFile.equals(this);
		}
		catch(Exception exc) {
			return true;
		}
	}

	public FileModel getFileModel() {
		return fileModel;
	}

	public void addListener(PounderModelListener l) {
		listeners.add(l);
	}

	public void removeListener(PounderModelListener l) {
		listeners.remove(l);
	}

	public synchronized void fireChange() {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			PounderModelListener l = (PounderModelListener)i.next();
			l.pounderModelChanged();
		}
	}

	/** Return our comment. **/ 
	public PlainDocument getComment() {
		return comment;
	}

	/** Return model used to track progress of playback and other
	 * actions. **/
	public DefaultBoundedRangeModel getProgressModel() {
		return progressModel;
	}

	/** Returns the DefaultComboBoxModel used to track our loaded test
	 * objects. **/
	public DefaultComboBoxModel getLoadedTestObjects() {
		return loadedTestObjects;
	}

	/** Returns true if we are currently recording. **/
	public boolean isRecording() {
		return recordingThread != null;
	}

	/** Returns true if we are currently playing. **/
	public boolean isPlaying() {
		return playbackThread != null;
	}

	/** Returns true if the test class is set. **/
	public boolean isTestClassSet() {
		return testInstanceFactory != null;
	}

	/** Override equals.  Return true based on equality of our
	 * testInstanceFactory, and record.  Note that preferences are not
	 * taken into account. **/
	public boolean equals(Object o) {
		PounderModel pm = (PounderModel)o;
		if(pm == null)
			return false;

		if(testInstanceFactory != null) {
			if(! testInstanceFactory.equals(pm.testInstanceFactory))
				return false;
		}
		else if(pm.testInstanceFactory != null)
			return false;

		if(! record.equals(pm.record))
			return false;

		try {
			String c1 = comment.getText(0, comment.getLength());
			String c2 = pm.comment.getText(0, pm.comment.getLength());
			if(! c1.equals(c2))
				return false;
		}
		catch(BadLocationException exc) {
			throw new RuntimeException("Error comparing comments in PounderModel", exc);
		}

		return true;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer("Setup Initializer: " + testInstanceFactory + "\n" + 
																				"Record Length: " + record.getSize() + "\nRecord:\n");
		Enumeration e = record.elements();
		while(e.hasMoreElements()) {
			RecordingItem ri = (RecordingItem)e.nextElement();
			ret.append(ri.toString() + "\n");
		}
		ret.append("\nComment: ");
		try {
			ret.append(comment.getText(0, comment.getLength()));
		}
		catch(BadLocationException exc) {
		}

		return ret.toString();
	}

	public PounderPrefs getPreferences() {
		return preferences;
	}

	public void setPreferences(PounderPrefs p) {
		this.preferences.retrieveData(p);
	}

	public TestInstanceFactory getTestInstanceFactory() {
		return testInstanceFactory;
	}

	public ClassLoader getTestClassLoader() {
		if(preferences.getUseSystemClassLoader())
			return getClass().getClassLoader();

		return new DynamicClassLoader(getClass().getClassLoader());
	}

	public void newInstance() {
		if(testInstanceFactory == null)
			throw new IllegalStateException("TestInstanceFactory is null");
						
		try {
			testInstanceFactory.showNewTestInstance(getTestClassLoader(), preferences);
		}
		catch(Throwable t) {
			statusModel.handleException(t);
		}
	}

  public void setPaused(boolean b) {
    if(playbackThread != null)
			playbackThread.setPaused(b);
		if(recordingThread != null) {
      recordingThread.setPaused(b);
    }

    fireChange();
  }

  public boolean isPaused() {
    if(playbackThread != null)
			return playbackThread.isPaused();
		if(recordingThread != null)
      return recordingThread.isPaused();

    return false;
  }

	/** Stop recording or playing. **/
	public void stop() {
		if(playbackThread != null) {
			playbackThread.setStopRequested(true);
			playbackFinished();
		}
		if(recordingThread != null) {
			recordingThread.setStopRequested(true);
			recordingFinished();
		}
	}

	public void playbackFinished() {
		this.playbackThread = null;
		fireChange();
	}

	public void recordingFinished() {
		this.recordingThread = null;
		fireChange();
	}

	public StatusModel getStatusModel() {
		return statusModel;
	}

	public RecordingRecord getRecord() {
		return record;
	}

  protected void finalize() throws Throwable {
    super.finalize();
    stop();
  }

	public synchronized void playback() {
		playbackThread = new PlaybackThread(this, getTestClassLoader());
		playbackThread.start();
		fireChange();
	}

	public synchronized void beginVerbatimRecording() {
		try {
			this.recordingThread = new RecordingThread(this, new VerbatimRecording(preferences));
			recordingThread.start();
			fireChange();
		}
		catch(Exception exc) {
			statusModel.setStatus(exc.getMessage());
			stop();
		}
	}

	protected boolean loadedTestObjectExists(String name) {
		for(int i=0;i < loadedTestObjects.getSize();i++) {
			String s = (String)loadedTestObjects.getElementAt(i);
			if(s.equals(name))
				return true;
		}

		return false;
	}

	protected void addLoadedTestObject(String name) {
		if(! loadedTestObjectExists(name))
			loadedTestObjects.addElement(name);
	}

	public void setTestInstanceFactory(TestInstanceFactory si) {
		record.clear();
		testInstanceFactory = si;
		if(testInstanceFactory != null) {
			addLoadedTestObject(testInstanceFactory.getSetupClass());
			loadedTestObjects.setSelectedItem(testInstanceFactory.getSetupClass());
		}
		fireChange();
	}

	/** Attempt to load an instance of this component from 'name'. **/
	public void setTestClass(String name) {
		try {
			setTestInstanceFactory(new TestInstanceFactory(name));
			statusModel.setStatus(Strings.getString("TestClassSetTo:") + name);
		}
		catch(Throwable t) {
			statusModel.handleException(t);
			t.printStackTrace();
		}
	}

}
