package com.mtp.pounder;

import javax.swing.JPanel;

import java.awt.AWTEvent;

import java.awt.event.KeyEvent;

/**

Class for detecting when a Recording should be stopped.

@author Matthew Pekar

**/
public class EventDetector {

	protected JPanel dummyPanel;
	protected KeyEvent endEvent, pauseEvent;

	public EventDetector() {
		this.dummyPanel = new JPanel();
		this.endEvent = new KeyEvent(dummyPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_E, KeyEvent.CHAR_UNDEFINED);
	}

	public Object clone() {
		EventDetector ret = new EventDetector();
		ret.setEndEvent(copyEvent(endEvent));
		ret.setPauseEvent(copyEvent(pauseEvent));
		return ret;
	}

	protected KeyEvent copyEvent(KeyEvent e) {
		if(e == null)
			return null;

		return new KeyEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), e.getKeyCode(), e.getKeyChar());
	}

	public boolean equals(Object o) {
		EventDetector ed = (EventDetector)o;
		return areKeyEventsEqual(endEvent, ed.endEvent) && 
			areKeyEventsEqual(pauseEvent, ed.pauseEvent);
	}

	public synchronized KeyEvent getEndEvent() {
		return endEvent;
	}

	/** Assumes given event will not change. **/
	public synchronized void setEndEvent(KeyEvent e) {
		this.endEvent = e;
	}

	public synchronized void setPauseEvent(KeyEvent e) {
		this.pauseEvent = e;
	}

	public synchronized boolean isEndEvent(AWTEvent e) {
		if(e instanceof KeyEvent)
			return areKeyEventsEqual((KeyEvent)e, endEvent);
		return false;
	}

	protected boolean areKeyEventsEqual(KeyEvent e1, KeyEvent e2) {
		//check for nulls
		if(e1 == null) {
			if(e2 == null)
				return true;
			return false;
		}

		if(e1.getID() != e2.getID())
			return false;

		if(e1.getModifiersEx() != e2.getModifiersEx())
			return false;

		if(e1.getID() == KeyEvent.KEY_TYPED)
			return e1.getKeyChar() == e2.getKeyChar();

		return e1.getKeyCode() == e2.getKeyCode();
	}

}
