package com.mtp.pounder;

import java.awt.Frame;
import java.awt.Window;
import java.awt.Point;

import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;

import org.w3c.dom.Element;

import com.mtp.gui.WindowWatcher;

/**

Holds all the subclasses of RecordingItem that represent Window
events.

@author Matthew Pekar

**/
public class WindowItems {

	public static RecordingItem getItemForEvent(WindowWatcher ww, ComponentEvent e, long delay) {
		Object source  = e.getSource();
		if(! (source instanceof Window))
			return null;

		Window w = (Window)source;
		switch(e.getID()) {
		case ComponentEvent.COMPONENT_RESIZED:
			return new WindowSizeChangeItem(ww.getWindowID(w), w.getWidth(), w.getHeight(), delay);
		case ComponentEvent.COMPONENT_MOVED:
			return new WindowMovedItem(ww.getWindowID(w), w.getX(), w.getY(), delay);
		}

		return null;
	}

	public static RecordingItem getItemForEvent(WindowWatcher ww, WindowEvent e, long delay) {
		Window w = (Window)e.getSource();

		switch(e.getID()) {
		case WindowEvent.WINDOW_STATE_CHANGED:
			Frame f = (Frame)w;
			return new WindowStateChangedItem(ww.getWindowID(w), f.getExtendedState(), delay);
		case WindowEvent.WINDOW_GAINED_FOCUS:
			return new WindowGainedFocusItem(ww.getWindowID(w), delay);
		}

		return null;
	}

}
