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

Asserts that a window is showing.

@author Matthew Pekar

**/
public class WindowShowingItem extends WindowAssertItem {

	public WindowShowingItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);
	}

	public WindowShowingItem(int windowID) {
		super(windowID);
	}

	public WindowShowingItem(String title) {
		super(title);
	}

	public boolean equals(Object o) {
		if(! super.equals(o))
			return false;

		return (o instanceof WindowShowingItem);
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.assrt.WindowShowingItem");
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		if(getWindow(ww) == null) {
			if(getWindowID() >= 0)
				throw new PlaybackException(Strings.getString("AssertionFailedWindowNotVisible:") + getWindowID());
			throw new PlaybackException(Strings.getString("AssertionFailedWindowNotVisible:") + getWindowTitle());
		}
	}

	public String toString() {
		return "Assert Window Showing: " + getAttribs();
	}

}
