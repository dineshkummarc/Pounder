package com.mtp.pounder;

import java.awt.Frame;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.AWTEventListener;

import javax.swing.SwingUtilities;

import com.mtp.gui.WindowWatcher;

/**

Records all AWT events sent.

@author Matthew Pekar

**/
public class VerbatimRecording implements AWTEventListener, Recording, KeyEventDispatcher {

	/** After idle for this long we terminate. **/
	public static final long DEFAULT_MAX_IDLE_TIME = 100000; //100 seconds

	protected WindowWatcher windowWatcher;
	protected long mask;
	protected boolean finished, paused;
	protected RecordingRecord record;
	protected long lastTimeItemRecorded;
	protected Filter filter;
	protected long maxIdleTime;
	protected PounderPrefs prefs;
	protected ComponentIdentifierFactory componentIdentifierFactory;

	public VerbatimRecording(PounderPrefs prefs) {
		this(prefs, DEFAULT_MAX_IDLE_TIME);
	}

	public VerbatimRecording(PounderPrefs prefs, long maxIdleTime) {
		this.windowWatcher = null;
		this.mask = AWTEvent.ACTION_EVENT_MASK
			| AWTEvent.MOUSE_EVENT_MASK
			| AWTEvent.FOCUS_EVENT_MASK
			| AWTEvent.MOUSE_MOTION_EVENT_MASK
			| AWTEvent.MOUSE_WHEEL_EVENT_MASK
			| AWTEvent.TEXT_EVENT_MASK
			| AWTEvent.WINDOW_EVENT_MASK
			| AWTEvent.WINDOW_FOCUS_EVENT_MASK
			| AWTEvent.WINDOW_STATE_EVENT_MASK
			| AWTEvent.COMPONENT_EVENT_MASK
      | AWTEvent.INPUT_METHOD_EVENT_MASK;

		this.finished = false;
    this.paused = false;
		this.record = null;
		this.lastTimeItemRecorded = 0;
		this.prefs = prefs;
		this.filter = null;
		this.maxIdleTime = maxIdleTime;
		this.componentIdentifierFactory = new ComponentIdentifierFactory();
	}

	public synchronized void begin(RecordingRecord record, WindowWatcher ww) {
		this.windowWatcher = ww;
    this.windowWatcher.setFilterAllWindows(paused);
		this.record = record;
		this.record.clear();
		this.filter = new DefaultFilter(ww);

		lastTimeItemRecorded = System.currentTimeMillis();
		addListeners();
	}

	protected void addListeners() {
		Toolkit.getDefaultToolkit().addAWTEventListener(this, mask);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	public synchronized boolean isFinished() {
		if(finished)
			return finished;
				
		return ((System.currentTimeMillis() - lastTimeItemRecorded) > maxIdleTime);
	}

	/** Terminate recording. **/
	public synchronized void terminate() {
		if(finished)
			return;

		finished = true;

		if(record != null) {
			//add this so script will be proper length
			record.addItem(new DummyRecordingItem(System.currentTimeMillis() - lastTimeItemRecorded));
		}

		removeListeners();
	}

	protected void removeListeners() {
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		eventDispatched(e);
		return false;
	}

	protected int getWindowID(Component c) {
		if(c instanceof Window)
			return windowWatcher.getWindowID((Window)c);

		Window w = SwingUtilities.windowForComponent(c);
		return windowWatcher.getWindowID(SwingUtilities.windowForComponent(c));
	}

	protected boolean eventShouldBeIgnored(AWTEvent e) {
		if(! prefs.getIgnoreUnnamed())
			return false;

		if(e instanceof ComponentEvent) {
			ComponentEvent ce = (ComponentEvent)e;
			return ce.getComponent().getName() == null;
		}

		return false;
	}

  public synchronized void setPaused(boolean b) {
    paused = b;

    if(windowWatcher != null)
      windowWatcher.setFilterAllWindows(b);
    if(! b)
      lastTimeItemRecorded = System.currentTimeMillis();
  }

  public synchronized boolean isPaused() {
    return paused;
  }

	public synchronized void eventDispatched(AWTEvent e) {
		if(prefs.getEventDetector().isEndEvent(e)) {
			terminate();
			return;
		}

    if(paused)
      return;

		if(eventShouldBeIgnored(e))
			return;

		if(! filter.accepts(e))
			return;

		try {
			matchEvent(e);
		}
		catch(Exception exc) {
			exc.printStackTrace();
			terminate();
		}
	}

	/** See if this event is one we'd like to record, and do so if it
	 * is. **/
	protected void matchEvent(AWTEvent e) {
		VerbatimRecordingOptions options = prefs.getVerbatimRecordingOptions();

		long currentTime = System.currentTimeMillis();
		long delay = currentTime - lastTimeItemRecorded;

		RecordingItem newItem = null;
		if((e instanceof MouseWheelEvent) && options.doMouseInputEvents) {
			MouseWheelEvent mwe = (MouseWheelEvent)e;
			newItem = new MouseWheelItem(mwe, getWindowID(mwe.getComponent()), delay, componentIdentifierFactory);
		}
		else if(e instanceof MouseEvent && (options.doMouseInputEvents || options.doMouseMotionEvents)) {
			MouseEvent me = (MouseEvent)e;
			newItem = matchMouseEvent(me, delay, options);
		}
		else if(e instanceof WindowEvent && options.doWindowEvents) {
			WindowEvent we = (WindowEvent)e;
			newItem = WindowItems.getItemForEvent(windowWatcher, we, delay);
		}
		else if(e instanceof KeyEvent && options.doKeyEvents) {
			KeyEvent ke = (KeyEvent)e;
			Component c = ke.getComponent();
			newItem = new KeyItem(ke, getWindowID(c), delay, componentIdentifierFactory);
		}
		else if(e instanceof ComponentEvent && options.doWindowEvents) {
			ComponentEvent ce = (ComponentEvent)e;
			newItem = WindowItems.getItemForEvent(windowWatcher, ce, delay);
		}
    else if(e instanceof InputMethodEvent && options.doKeyEvents) {
      InputMethodEvent ime = (InputMethodEvent)e;
			newItem = new InputMethodItem(ime, getWindowID((Component)ime.getSource()), delay, componentIdentifierFactory);
    }

		if(newItem != null) {
			record.addItem(newItem);
			lastTimeItemRecorded = currentTime;		
		}

	}

	/** Whether or not a drag is currently occuring. **/
	protected boolean dragging = false;
	/** Mask of buttons that were pressed when the drag started. **/
	protected int dragStartButtons = 0;

	/** Store data necessary to recover from a drag.  Swing ignores a
	 * very important mouse release. **/
	protected void startDrag(MouseEvent me) {
		dragging = true;
		dragStartButtons = me.getModifiersEx();
	}

	/** Release any mouse buttons that were not pressed at the start. **/
	protected void endDrag(MouseEvent me, long delay) {
		int modifiers = me.getModifiersEx();
		int windowID = getWindowID(me.getComponent());
		if((dragStartButtons & MouseEvent.BUTTON1_DOWN_MASK) > 0)
			modifiers = addMouseReleaseEvent(MouseEvent.BUTTON1, me.getComponent(), windowID, modifiers, me.getX(), me.getY(), delay);
		if((dragStartButtons & MouseEvent.BUTTON2_DOWN_MASK) > 0)
			modifiers = addMouseReleaseEvent(MouseEvent.BUTTON2, me.getComponent(), windowID, modifiers, me.getX(), me.getY(), delay);
		if((dragStartButtons & MouseEvent.BUTTON3_DOWN_MASK) > 0)
			addMouseReleaseEvent(MouseEvent.BUTTON3, me.getComponent(), windowID, modifiers, me.getX(), me.getY(), delay);

		dragging = false;
		dragStartButtons = 0;
	}

	protected int addMouseReleaseEvent(int button, Component c, int windowID, int startModifiers, int x, int y, long delay) {				
		int modifiers = 0;
		if(button == MouseEvent.BUTTON1) {
			modifiers = startModifiers & (~ MouseEvent.BUTTON1_DOWN_MASK);
		}
		else if(button == MouseEvent.BUTTON2) {
			modifiers = startModifiers & (~ MouseEvent.BUTTON2_DOWN_MASK);
		}
		else if(button == MouseEvent.BUTTON3) {
			modifiers = startModifiers & (~ MouseEvent.BUTTON3_DOWN_MASK);
		}

		MouseEvent releaseEvent = new MouseEvent(c, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), modifiers, x, y, 0, false, button);
		record.addItem(new MouseClickItem(releaseEvent, windowID, delay, componentIdentifierFactory));

		return modifiers;
	}

	/** Check whether the given MouseEvent is the end of a drag.  If
	 * so call endDrag(). **/
	protected void checkIsDragEnd(MouseEvent me, long delay) {
		if(me.getID() == MouseEvent.MOUSE_MOVED)
			endDrag(me, delay);
	}

	/** Check whether the mouse release event should end the drag.
	 * Set 'dragging' to false if it does. **/
	protected void checkMouseReleaseEndsDrag(MouseEvent me) {
		if((me.getButton() == MouseEvent.BUTTON1) && (me.getID() == MouseEvent.MOUSE_RELEASED))
			dragging = false;
	}

	/** Return a RecordingItem to represent the given MouseEvent if
	 * possible. **/
	protected RecordingItem matchMouseEvent(MouseEvent me, long delay, VerbatimRecordingOptions options) {
		int id = me.getID();

		switch(id) {
		case MouseEvent.MOUSE_PRESSED:
		case MouseEvent.MOUSE_RELEASED: {
			checkMouseReleaseEndsDrag(me);
			if(options.doMouseInputEvents)
				return new MouseClickItem(me, getWindowID(me.getComponent()), delay, componentIdentifierFactory);
		}

		case MouseEvent.MOUSE_ENTERED:
		case MouseEvent.MOUSE_EXITED:
		case MouseEvent.MOUSE_MOVED:
			if(dragging)
				checkIsDragEnd(me, delay);
			if(options.doMouseMotionEvents) {
				return new MouseMotionItem(me, getWindowID(me.getComponent()), delay, componentIdentifierFactory);
			}
			break;
		case MouseEvent.MOUSE_DRAGGED: {
			if(options.doMouseDragEvents) {
				if(! dragging)
					startDrag(me);
				return new MouseMotionItem(me, getWindowID(me.getComponent()), delay, componentIdentifierFactory);
			}
		}
		}

		return null;
	}

}
