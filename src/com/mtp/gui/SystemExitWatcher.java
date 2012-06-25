package com.mtp.gui;

import java.awt.Window;

/**

When the last window has been closed, exit the system.

Should only be instantiated once in the main() function.

@author Matthew Pekar

**/
public class SystemExitWatcher extends WindowWatcher {

	private static SystemExitWatcher instance = null;
		
	public static void instantiate() {
		if(instance == null)
			instance = new SystemExitWatcher();
	}
		
	public static SystemExitWatcher getInstance() {
		return instance;
	}

	private SystemExitWatcher() {
	}

	protected synchronized void removeWindow(Window w) {
		super.removeWindow(w);
		if(getWindowCount() == 0)
			System.exit(0);
	}

}
