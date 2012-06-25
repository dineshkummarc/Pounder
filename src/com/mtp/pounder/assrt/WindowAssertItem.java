package com.mtp.pounder.assrt;

import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.ComponentIdentifierFactory;
import com.mtp.pounder.PlaybackException;

import com.mtp.gui.WindowWatcher;

import java.util.Iterator;

import java.awt.Frame;
import java.awt.Window;
import java.awt.Dialog;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mtp.i18n.Strings;

/**

An assertion performed on some part of a specific window.

@author Matthew Pekar

**/
public abstract class WindowAssertItem extends RecordingItem {

	protected int windowID;
	protected String windowTitle;

	public WindowAssertItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(e.hasAttribute("windowID")) {
			String windowIDString = e.getAttribute("windowID");
			this.windowID = Integer.valueOf(windowIDString).intValue();
			this.windowTitle = null;
		}
		else if(e.hasAttribute("windowTitle")) {
			this.windowID = -1;
			this.windowTitle = e.getAttribute("windowTitle");
		}
		else
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\" or \"windowTitle\"");
	}

	public WindowAssertItem(int windowID) {
		super(0);
		this.windowID = windowID;
		this.windowTitle = null;
	}

	public WindowAssertItem(String windowTitle) {
		super(0);
		this.windowID = -1;
		this.windowTitle = windowTitle;
	}

	public int getWindowID() {
		return windowID;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	/** Throws PlaybackException if Window is not found. **/
	public Window getWindow(WindowWatcher ww) throws PlaybackException {
		int windowID = getWindowID();

		return (windowID >= 0) ? getWindowByID(ww) : getWindowByTitle(ww);
	}

	protected Window getWindowByID(WindowWatcher ww) throws PlaybackException {
		int windowID = getWindowID();

		if(! ww.containsWindow(windowID))
			throw new PlaybackException(Strings.getString("AssertionFailedWindowNotVisible:") + windowID);
		
		return ww.getWindowByID(windowID);
	}

	protected Window getWindowByTitle(WindowWatcher ww) throws PlaybackException {
		String title = getWindowTitle();

		Iterator i = ww.getWindows().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
				if(w instanceof Frame) {
					Frame f = (Frame)w;
					if(title.equals(f.getTitle()))
						return f;
				}
				else if(w instanceof Dialog) {
					Dialog d = (Dialog)w;
					if(title.equals(d.getTitle()))
						return d;
				}
		}
		
		throw new PlaybackException(Strings.getString("AssertionFailedWindowNotVisible:") + title);
	}

	public boolean equals(Object o) {
		if(! super.equals(o))
			return false;

		WindowAssertItem wsi = (WindowAssertItem)o;

		if(windowID != wsi.windowID)
			return false;

		if(windowTitle == null)
			return wsi.windowTitle == null ? true : false;

		return windowTitle.equals(wsi.windowTitle);
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		if(windowID != -1)
			e.setAttribute("windowID", String.valueOf(windowID));
		else
			e.setAttribute("windowTitle", windowTitle);
	}

	protected String getAttribs() {
		if(windowID >= 0)
			return " windowID=" + windowID + super.getAttribs();
		else
			return " title=" + windowTitle + super.getAttribs();
	}

}
