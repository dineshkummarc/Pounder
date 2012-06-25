package com.mtp.pounder;

import java.util.prefs.Preferences;

/**

Options used to initialize a VerbatimRecording.

@author Matthew Pekar

**/
public class VerbatimRecordingOptions {
		
	public volatile boolean doKeyEvents, doMouseInputEvents, doMouseMotionEvents, doWindowEvents, doMouseDragEvents;

	public VerbatimRecordingOptions() {
		doKeyEvents = doMouseInputEvents = doMouseMotionEvents = doWindowEvents = doMouseDragEvents = true;
	}

	public Object clone() {
		VerbatimRecordingOptions ret = new VerbatimRecordingOptions();
		ret.retrieveData(this);
		return ret;
	}

	/** Retrieve data from preferences stored on system and return
	 * self. **/
	public VerbatimRecordingOptions retrieveDataFromSystem() {
		Preferences p = Preferences.userNodeForPackage(getClass());
		doKeyEvents = p.getBoolean("doKeyEvents", true);
		doMouseInputEvents = p.getBoolean("doMouseInputEvents", true);
		doMouseMotionEvents = p.getBoolean("doMouseMotionEvents", true);
		doWindowEvents = p.getBoolean("doWindowEvents", true);
		doMouseDragEvents = p.getBoolean("doMouseDragEvents", true);

		return this;
	}

	public void saveDataToSystem() {
		Preferences p = Preferences.userNodeForPackage(getClass());
		p.putBoolean("doKeyEvents", doKeyEvents);
		p.putBoolean("doMouseInputEvents", doMouseInputEvents);
		p.putBoolean("doMouseMotionEvents", doMouseMotionEvents);
		p.putBoolean("doWindowEvents", doWindowEvents);
		p.putBoolean("doMouseDragEvents", doMouseDragEvents);
	}

	public void retrieveData(VerbatimRecordingOptions vro) {
		this.doKeyEvents = vro.doKeyEvents;
		this.doMouseInputEvents = vro.doMouseInputEvents;
		this.doMouseMotionEvents = vro.doMouseMotionEvents;
		this.doWindowEvents = vro.doWindowEvents;
		this.doMouseDragEvents = vro.doMouseDragEvents;
	}

	public boolean equals(Object o) {
		if(o == null)
			return false;

		VerbatimRecordingOptions vro = (VerbatimRecordingOptions)o;
		return doKeyEvents == vro.doKeyEvents &&
			doMouseInputEvents == vro.doMouseInputEvents &&
			doMouseMotionEvents == vro.doMouseMotionEvents &&
			doWindowEvents == vro.doWindowEvents &&
			doMouseDragEvents == vro.doMouseDragEvents;
	}

	public String toString() {
		return "Verbatim Recording Options: \n" + 
			"doKeyEvents: " + doKeyEvents + "\n" +
			"doMouseInputEvents: " + doMouseInputEvents + "\n" +
			"doMouseMotionEvents: " + doMouseMotionEvents + "\n" + 
			"doWindowEvents: " + doWindowEvents + "\n" +
			"doMouseDragEvents: " + doMouseDragEvents + "\n";
	}

}
