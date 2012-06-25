package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Window;
import java.awt.Frame;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/** 

RecordingItem for a Frame changing state. 

@author Matthew Pekar

**/
public class WindowStateChangedItem extends RecordingItem {
				
	protected int windowID;
	protected int state;

	public WindowStateChangedItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("state"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"state\"");
		if(! e.hasAttribute("windowID"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\"");

		this.state = Integer.valueOf(e.getAttribute("state")).intValue();
		this.windowID = Integer.valueOf(e.getAttribute("windowID")).intValue();
	}

	public WindowStateChangedItem(int windowID, int state, long delay) {
		super(delay);
		this.windowID = windowID;
		this.state = state;
	}

	public Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.WindowStateChangedItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("state", String.valueOf(state));
		e.setAttribute("windowID", String.valueOf(windowID));
	}

	public boolean equals(Object o) {
		WindowStateChangedItem wsci = (WindowStateChangedItem)o;
		return (wsci.windowID == windowID) && (state == wsci.state) &&
			(delay == wsci.delay);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Window w = ww.getWindowByID(windowID);
		if(w == null)
			throw new IllegalStateException(Strings.getString("WindowNotFound:") + windowID);
						
		if(w instanceof Frame)
			((Frame)w).setExtendedState(state);
		else
			throw new IllegalStateException("Error in WindowStateChangedItem: window at index " + windowID + " is not a frame. ");
	} 

	protected String getStateAsString() {
		switch(state) {
		case Frame.NORMAL:
			return "Normal";
		case Frame.ICONIFIED:
			return "Iconified";
		case Frame.MAXIMIZED_BOTH:
			return "Maximized Both";
		case Frame.MAXIMIZED_HORIZ:
			return "Maximized Horizontal";
		case Frame.MAXIMIZED_VERT:
			return "Maximized Vertical";
		}

		return "";
	}

	public String toString() {
		return "Window State Change: index=" + windowID + "state=" + getStateAsString();
	}

}
