package com.mtp.pounder;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Window;
import java.awt.Point;

import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import com.mtp.gui.WindowWatcher;

/**

Default Filter, if the Component generating the event is not present
in the watcher (that is, it has been filtered) then do not accept the
event.

@author Matthew Pekar

**/
public class DefaultFilter implements Filter {

	protected WindowWatcher windowWatcher;
		
	public DefaultFilter(WindowWatcher ww) {
		this.windowWatcher = ww;
	}

	/** Return true if event source is a Component and is present in
			our windowWatcher. **/
	public boolean accepts(AWTEvent e) {
		Object source = e.getSource();
		if(! (source instanceof Component))
			return false;

		Component c = (Component)source;
		Window w;
		if(c instanceof Window)
			w = (Window)c;
		else
			w = SwingUtilities.windowForComponent(c);
		if(windowWatcher.containsWindow(w))
			return true;

		return false;
	}

}
