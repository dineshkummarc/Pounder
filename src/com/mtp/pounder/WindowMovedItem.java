package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Window;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/** 

Recording item for a Window moving. 

@author Matthew Pekar

**/
public class WindowMovedItem extends RecordingItem {

	protected int windowID;
	protected int x, y;

	public WindowMovedItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("x"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"x\"");
		if(! e.hasAttribute("y"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"y\"");
		if(! e.hasAttribute("windowID"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\"");

		this.x = Integer.valueOf(e.getAttribute("x")).intValue();
		this.y = Integer.valueOf(e.getAttribute("y")).intValue();
		this.windowID = Integer.valueOf(e.getAttribute("windowID")).intValue();
	}

				
	public WindowMovedItem(int windowID, int x, int y, long delay) {
		super(delay);
		this.windowID = windowID;
		this.x = x;
		this.y = y;
	}

	public Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.WindowMovedItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("x", String.valueOf(x));
		e.setAttribute("y", String.valueOf(y));
		e.setAttribute("windowID", String.valueOf(windowID));
	}

	public boolean equals(Object o) {
		WindowMovedItem wmi = (WindowMovedItem)o;
		return (windowID == wmi.windowID) && (x == wmi.x) && (y == wmi.y) &&
			(delay == wmi.delay);
	}
				
	public void playback(WindowWatcher ww, PounderPrefs prefs) throws IllegalStateException {
		Window w = ww.getWindowByID(windowID);
		if(w == null)
			throw new IllegalStateException(Strings.getString("WindowNotFound:") + windowID);
						
		w.setLocation(x, y);
		w.repaint();
	}

	public String toString() {
		return "Window Moved: index=" + windowID + " x=" + x + " y=" + y;
	}

}
