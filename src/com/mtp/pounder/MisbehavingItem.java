package com.mtp.pounder;

import com.mtp.gui.WindowWatcher;

/**

Utility class for testing.  Throws a PlaybackException when played.

@author Matthew Pekar

**/
public class MisbehavingItem extends DummyRecordingItem {
				
	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		playCount += 1;
		throw new Exception("Foo");
	}
}
