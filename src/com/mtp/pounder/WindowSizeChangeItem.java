package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Window;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/** 

RecordingItem for a Window size change. 

@author Matthew Pekar

**/
public class WindowSizeChangeItem extends RecordingItem {

	protected int windowID;
	protected int width, height;

	public WindowSizeChangeItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("width"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"width\"");
		if(! e.hasAttribute("height"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"height\"");
		if(! e.hasAttribute("windowID"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\"");

		this.width = Integer.valueOf(e.getAttribute("width")).intValue();
		this.height = Integer.valueOf(e.getAttribute("height")).intValue();
		this.windowID = Integer.valueOf(e.getAttribute("windowID")).intValue();
	}

	public WindowSizeChangeItem(int windowID, int width, int height, long delay) {
		super(delay);
		this.windowID = windowID;
		this.width = width;
		this.height = height;
	}

	public Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.WindowSizeChangeItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("width", String.valueOf(width));
		e.setAttribute("height", String.valueOf(height));
		e.setAttribute("windowID", String.valueOf(windowID));
	}

	public boolean equals(Object o) {
		WindowSizeChangeItem wsci = (WindowSizeChangeItem)o;
		return (windowID == wsci.windowID) && (width == wsci.width) && (height == wsci.height) &&
			(delay == wsci.delay);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Window w = ww.getWindowByID(windowID);
		if(w == null)
			throw new IllegalStateException(Strings.getString("WindowNotFound:") + windowID);
						
		w.setSize(width, height);
		w.validate();
	}

	public String toString() {
		return "Window Size Change: index=" + windowID + " width=" + width + " height= " + height;
	}

}
