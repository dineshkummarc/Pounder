package com.mtp.gui;

import java.awt.Window;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Container;
import java.awt.Component;

import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import javax.naming.TimeLimitExceededException;

/**

Class that begins observing Window events as soon as it is created.
Allows developer to retrieve windows and also control which will be
observed.

@author Matthew Pekar

**/
public class WindowWatcher implements AWTEventListener {

	/** Default timeout for waitTillWindowPresent(),
	 * waitTillWindowNotPresent() and other future wait functions.  Measured
	 * in milliseconds. **/
	public static long DEFAULT_WAIT_TIMEOUT = 5000;

	/** Next index to be used.  Guaranteed to be incremented in
			consistent manner. **/
	protected Integer id;
	/** HashMap of Window to Integer. **/
	protected HashMap windows;
	/** Ignore Window's with these names. **/
	protected HashMap filteredNames;
	/** Ignore Window's with these hash codes. **/
	protected HashMap filteredHashCodes;
	/** Time in milliseconds for wait functions. **/
	protected volatile long waitTimeout;
	/** Whether to ignore all incoming windows. **/
	protected volatile boolean filterAllWindows;
	/** Whether to remove Window's when removeWindow() is called.
	 * Default is true. **/
	protected volatile boolean removeWindows;

	/** Default constructor.  Initializes id to 0, creates maps,
			and attaches self as window event listener. **/
	public WindowWatcher() {
		this.id = new Integer(0);
		this.windows = new HashMap();
		this.filteredNames = new HashMap();
		this.filteredHashCodes = new HashMap();
		this.waitTimeout = DEFAULT_WAIT_TIMEOUT;
		this.filterAllWindows = false;
		this.removeWindows = true;
		long mask = AWTEvent.COMPONENT_EVENT_MASK |
			AWTEvent.WINDOW_EVENT_MASK;
		Toolkit.getDefaultToolkit().addAWTEventListener(this, mask);
	}

	/** Returns a new collection of the Window's being watched. **/
	public Collection getWindows() {
		Vector ret = new Vector(windows.size());
		ret.addAll(windows.keySet());
		return ret;
	}

	/** Set the amount of time to before a waitTillSomething() command
	 * fails. **/
	public void setWaitTimeout(long time) {
		this.waitTimeout = time;
	}

	/** Set whether Window's should be removed from our map as they
	 * disappear. **/
	public void setRemoveWindows(boolean whether) {
		this.removeWindows = whether;
	}

	/** Return whether Window's are removed from our map as they
	 * disappear. **/
	public boolean getRemoveWindows() {
		return this.removeWindows;
	}
		
	/** If true, ignore all new windows that appear. **/
	public void	setFilterAllWindows(boolean b) { 
		this.filterAllWindows = b; 
	}

	/** @return True if new windows are being ignored. **/
	public boolean getFilterAllWindows() { 
		return this.filterAllWindows;
	}

	/** Tells all windows I'm watching to update their UI. **/
	public synchronized void updateUIs() {
		Iterator i = copyWindows().keySet().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
			SwingUtilities.updateComponentTreeUI(w);
		}
	}

	/** Returns number of windows being watched. **/
	public synchronized int getWindowCount() {
		return windows.size();
	}

	/** Returns a new copy of the windows HashMap. **/
	public synchronized HashMap copyWindows() {
		HashMap ret = new HashMap();
		ret.putAll(windows);
		return ret;
	}

	/** Clears windows HashMap with synchronization. **/
	protected synchronized void clearWindows() {
		windows.clear();
	}

	/** Call dispose() on all windows then clear the HashMap. **/ 
	public void disposeWindows() {
		Iterator i = copyWindows().keySet().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
			w.setVisible(false);
			w.dispose();
		}
		clearWindows();
	}

	/** Removes as listener for window events and dispose()'s all
	 * windows if requested.  Be very careful here, potential thread
	 * lock when dispose()'ing Window's. **/		
	public void disconnect(boolean dispose) {
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
		if(dispose)
			disposeWindows();
	}

	/** Return Window with given name or null if none exists.  Not
	 * responsible for duplicates. **/
	public Window getWindowByName(String name) {
		Iterator i = windows.keySet().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
			if(name.equals(w.getName()))
				return w;
		}

		return null;
	}

	/** Return Window with given title or null if none exists.  Not
	 * responsible for duplicates. **/
	public Window getWindowByTitle(String title) {
		Iterator i = windows.keySet().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
			if(! (w instanceof Frame))
				continue;
			
			Frame f = (Frame)w;
			if(title.equals(f.getTitle()))
				return f;
		}

		return null;
	}

	/** Returns id of given Window and throws NoSuchElementException
			if not found. **/
	public synchronized int getWindowID(Window w) {
		return ((Integer)windows.get(w)).intValue();
	}

	/** Returns Window with given id or null if it's not present. **/
	public synchronized Window getWindowByID(int id) {
		Iterator i = windows.keySet().iterator();
		while(i.hasNext()) {
			Object o = i.next();
			Integer ret = (Integer)windows.get(o);
			if(ret.intValue() == id)
				return (Window)o;
		}

		return null;
	}

	/** Uses waitTimeout varaible as timeout. **/
	public void waitTillWindowPresent(Window w) throws InterruptedException, TimeLimitExceededException {
		this.waitTillWindowPresent(w, waitTimeout);
	}

	/** Uses waitTimeout varaible as timeout. **/
	public void waitTillWindowNotPresent(Window w) throws InterruptedException, TimeLimitExceededException {
		this.waitTillWindowNotPresent(w, waitTimeout);
	}


	/** This method returns after the given Window has been added or
	 * the timeout is reached. **/
	public void waitTillWindowPresent(Window w, long timeout) throws InterruptedException, TimeLimitExceededException {
		long startTime = System.currentTimeMillis();
		while(! containsWindow(w)) {
			try { Thread.sleep(50); } catch(InterruptedException exc) {};
			if((System.currentTimeMillis() - startTime) > timeout)
				throw new TimeLimitExceededException("Waited " + timeout + " milliseconds with no appearance of Window: " + w.hashCode() + " " + w);
		}
	}

	/** This method returns after the given Window has been closed or
	 * the timeout is reached. **/
	public void waitTillWindowNotPresent(Window w, long timeout) throws InterruptedException, TimeLimitExceededException {
		long startTime = System.currentTimeMillis();
		while(containsWindow(w)) {
			try { Thread.sleep(50); } catch(InterruptedException exc) {};
			if((System.currentTimeMillis() - startTime) > timeout)
				throw new TimeLimitExceededException("Waited " + timeout + " milliseconds but Window still present: " + w);
		}
	}

	/** Returns true if given Window is being watched. **/
	public synchronized boolean containsWindow(Window w) {
		return windows.containsKey(w);
	}

	/** Returns true if given Window id is being watched. **/
	public synchronized boolean containsWindow(int id) {
		Iterator i = windows.keySet().iterator();
		while(i.hasNext()) {
			Object o = i.next();
			Integer theInt = (Integer)windows.get(o);
			if(theInt.intValue() == id)
				return true;
		}
				
		return false;
	}

	/** Returns true if given Window should not be watched. **/
	protected synchronized boolean filtered(Window w) {
		if(filterAllWindows) {
			return true;
		}

		String name = w.getName();
		if(filteredNames.containsKey(name)) {
			return true;
		}

		if(filteredHashCodes.containsKey(new Integer(w.hashCode()))) {
			return true;
		}
				
		return false;
	}

	/** Don't let Window's with this name be added. **/
	public synchronized void filterWindow(String name) {
		filteredNames.put(name, name);
		removeFilteredWindows();
	}

	/** Filter Window's with this hash code. **/
	public synchronized void filterWindow(int hashCode) {
		Integer id = new Integer(hashCode);
		filteredHashCodes.put(id, id);
		removeFilteredWindows();
	}

	/** Go through all Window's and remove any that are filtered. **/
	protected void removeFilteredWindows() {
		//first remove any children
		Iterator i = windows.keySet().iterator();
		while(i.hasNext()) {
			Window w = (Window)i.next();
			if(filtered(w))
				i.remove();
		}
	}

	/** Hashes given Window at current id and increments current id by 1. **/
	protected synchronized void addWindow(Window w) {
		if(windows.containsKey(w)) {
			return;
		}

		if(! filtered(w)) {
			windows.put(w, id);
			id = new Integer(id.intValue() + 1);
		}
	}

	/** Remove given window. **/ 
	protected synchronized void removeWindow(Window w) {
		if(removeWindows) {
			windows.remove(w);
			if(windows.containsKey(w)) 
				throw new IllegalStateException("Huh?  Should be removed!");
		}
	}

	/** Update 'windows' based on WindowEvent. **/
	public synchronized void eventDispatched(AWTEvent e) {
		if(e instanceof WindowEvent) {
			WindowEvent we = (WindowEvent)e;
			if(we.getID() == WindowEvent.WINDOW_CLOSED)
				removeWindow((Window)we.getSource());
		}
		else {
			int id = e.getID();
			if(id != ComponentEvent.COMPONENT_HIDDEN &&
				 id != ComponentEvent.COMPONENT_SHOWN)
				return;

			ComponentEvent ce = (ComponentEvent)e;

			Component c = ce.getComponent();
			if(! (c instanceof Window))
				return;

			if(id == ComponentEvent.COMPONENT_SHOWN)
				addWindow((Window)c);
			else
				removeWindow((Window)c);
		}
	}
													 
}
