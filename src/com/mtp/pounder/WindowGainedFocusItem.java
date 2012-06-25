package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Window;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/** 

RecordingItem for a Window gaining focus. 

@author Matthew Pekar

**/
public class WindowGainedFocusItem extends RecordingItem {

	protected int windowID;

	public WindowGainedFocusItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("windowID"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\"");

		this.windowID = Integer.valueOf(e.getAttribute("windowID")).intValue();
	}


	public WindowGainedFocusItem(int windowID, long delay) {
		super(delay);
		this.windowID = windowID;
	}

	public Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.WindowGainedFocusItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("windowID", String.valueOf(windowID));
	}

	public boolean equals(Object o) {
		WindowGainedFocusItem wgfi = (WindowGainedFocusItem)o;
		return (windowID == wgfi.windowID) && (delay == wgfi.delay);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws IllegalStateException {
		Window w = ww.getWindowByID(windowID);
		if(w == null)
			throw new IllegalStateException(Strings.getString("WindowNotFound:") + windowID);
						
		w.requestFocus();
	}

	public String toString() {
		return "Window Focused: index=" + windowID;
	}

}
