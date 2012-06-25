package com.mtp.pounder;

import com.mtp.gui.WindowWatcher;

import org.w3c.dom.Element;

/**

Utility class used in testing.  Has a public boolean that can be used
to determine whether it has been played.

@author nimdok

**/
public class CheckPlayedItem extends DummyRecordingItem {
				
	public boolean played;

	public CheckPlayedItem(long delay) {
		super(delay);
		played = false;
	}

	public CheckPlayedItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) {
		played = true;
	}

}
