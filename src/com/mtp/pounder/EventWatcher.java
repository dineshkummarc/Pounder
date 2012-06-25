package com.mtp.pounder;

import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.AWTEventListener;

import java.awt.Frame;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.KeyEventPostProcessor;

/**

Little utility class I use for debugging.

@author Matthew Pekar

**/
public class EventWatcher implements AWTEventListener {

	public EventWatcher() {
		long mask = AWTEvent.FOCUS_EVENT_MASK;
		/*
			AWTEvent.ACTION_EVENT_MASK
			| AWTEvent.MOUSE_EVENT_MASK
			| AWTEvent.FOCUS_EVENT_MASK
			| AWTEvent.MOUSE_MOTION_EVENT_MASK
			| AWTEvent.MOUSE_WHEEL_EVENT_MASK
			| AWTEvent.TEXT_EVENT_MASK
			| AWTEvent.WINDOW_EVENT_MASK
			| AWTEvent.WINDOW_FOCUS_EVENT_MASK
			| AWTEvent.WINDOW_STATE_EVENT_MASK
			| AWTEvent.COMPONENT_EVENT_MASK;
		*/

		Toolkit.getDefaultToolkit().addAWTEventListener(this, mask);
	}

	public void eventDispatched(AWTEvent e) {
		//				FocusEvent fe = (FocusEvent)e;
		//				if(fe.getID() == FocusEvent.FOCUS_GAINED)
		//						System.out.println("Focus gained: " + fe.getSource().getClass());
	}

}
