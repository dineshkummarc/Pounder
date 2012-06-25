package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Component;
import java.awt.Window;
import java.awt.Robot;
import java.awt.GraphicsDevice;
import java.awt.AWTException;

import javax.swing.SwingUtilities;

import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Item that operates on a single Component.

@author Matthew Pekar

**/
public abstract class ComponentItem extends RecordingItem {

	/** Hashmap of GraphicDevice's to Robot's. **/
	protected static Map robots = Collections.synchronizedMap(new HashMap());

	/** ID of Window in which the click occured. **/
	protected int windowID;
	/** Identifier for Component in which the click occured. **/
	protected ComponentIdentifier component;

	public ComponentItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("windowID"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"windowID\"");

		this.windowID = Integer.valueOf(e.getAttribute("windowID")).intValue();
		this.component = f.buildFromString(e.getAttribute("component"));
	}

	public ComponentItem(long delay, int windowID, ComponentIdentifier component) {
		super(delay);

		this.windowID = windowID;
		this.component = component;
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);
		e.setAttribute("windowID", String.valueOf(windowID));
		e.setAttribute("component", component.asString());
	}

	public boolean equals(Object o) {
		ComponentItem i = (ComponentItem)o;
		return super.equals(o) &&
			windowID == i.windowID &&
			component.equals(i.component);
	}

	public ComponentIdentifier getComponentIdentifier() {
		return component;
	}

	public void setComponentIdentifier(ComponentIdentifier ci) {
		this.component = ci;
	}

	public int getWindowID() {
		return windowID;
	}

	public void setWindowID(int wid) {
		this.windowID = wid;
	}

	/** Return a Robot for the given Component.  Makes sure it's aimed
	 * at the correct screen. **/
	public Robot getRobot(Component c) throws IllegalStateException, AWTException {
		Window w;
		if(c instanceof Window)
			w = (Window)c;
		else 
			w = SwingUtilities.windowForComponent(c);
		if(w == null)
			throw new IllegalStateException("Window not found for Component: " + c);

		GraphicsDevice gd = w.getGraphicsConfiguration().getDevice();
		if(! robots.containsKey(gd)) {
			Robot r = new Robot(gd);
			r.setAutoDelay(0);
			r.setAutoWaitForIdle(true);
			robots.put(gd, r);
		}
	
		return (Robot)robots.get(gd);
	}

	public Component getComponent(WindowWatcher ww) throws NoSuchElementException {
		Window w = ww.getWindowByID(windowID);
		if(w == null)
			throw new NoSuchElementException("Window not present: " + windowID);
		return component.getComponent(w);
	}

	public String getAttribs() {
		return " component=" + component + " windowID=" + windowID + super.getAttribs();
	}

}
