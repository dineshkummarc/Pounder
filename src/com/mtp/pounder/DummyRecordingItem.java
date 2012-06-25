package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mtp.gui.WindowWatcher;

/**

A RecordingItem that does nothing.

@author Matthew Pekar

**/
public class DummyRecordingItem extends RecordingItem {

	public int playCount = 0;

	public DummyRecordingItem(long delay) {
		super(delay);
	}
		
	public DummyRecordingItem() {
		super(0);
	}

	public DummyRecordingItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		playCount += 1;
	}

	public Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.DummyRecordingItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);
	}

	public boolean equals(Object o) {
		if(! (o instanceof DummyRecordingItem))
			return false;

		return super.equals(o);
	}

	public String toString() {
		return "DummyRecordingItem:" + getAttribs();
	}
}
