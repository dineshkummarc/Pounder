package com.mtp.pounder;

import java.awt.event.MouseEvent;

import java.awt.Point;
import java.awt.Window;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.Robot;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Represents a mouse click.

@author Matthew Pekar

**/
public class MouseClickItem extends ComponentItem {

	/** Coordinates are stored as a percentage of their component.
	 * For example, in a 100-pixel wide Component, pixel 50 would be
	 * stored as 0.5. **/
	protected double x, y;
	/** Pressed or released. **/
	protected int type; 
	protected int button;

	public MouseClickItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("x"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"x\"");
		if(! e.hasAttribute("y"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"y\"");
		if(! e.hasAttribute("type"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"type\"");
		if(! e.hasAttribute("button"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"button\"");

		this.x = Double.valueOf(e.getAttribute("x")).doubleValue();
		this.y = Double.valueOf(e.getAttribute("y")).doubleValue();
		this.type = Integer.valueOf(e.getAttribute("type")).intValue();
		this.button = Integer.valueOf(e.getAttribute("button")).intValue();
	}

	public MouseClickItem(MouseEvent e, int windowID, long delay, ComponentIdentifierFactory f) {
		super(delay, windowID, f.build(e.getComponent()));

		Component c = e.getComponent();
		Point p = e.getPoint();
		this.x = p.getX() / c.getWidth();
		this.y = p.getY() / c.getHeight();
		this.type = e.getID();
		this.button = e.getButton();
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.MouseClickItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("x", String.valueOf(x));
		e.setAttribute("y", String.valueOf(y));
		e.setAttribute("type", String.valueOf(type));
		e.setAttribute("button", String.valueOf(button));
	}

	public boolean equals(Object o) {
		MouseClickItem mci = (MouseClickItem)o;
		return super.equals(o) &&
			(x == mci.x) && (y == mci.y) &&
			(type == mci.type) &&
			(button == mci.button);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Component c = getComponent(ww);
		int xpixel = (int)(x * c.getWidth());
		int ypixel = (int)(y * c.getHeight());

		if(! c.isShowing())
			throw new IllegalStateException("Component must be showing");

		Point loc = c.getLocationOnScreen();				
		Robot r = getRobot(c);
		r.mouseMove(loc.x + xpixel, loc.y + ypixel);
				
		int toPress = 0;
		if(button == MouseEvent.BUTTON1)
			toPress = MouseEvent.BUTTON1_MASK;
		if(button == MouseEvent.BUTTON2)
			toPress = MouseEvent.BUTTON2_MASK;
		if(button == MouseEvent.BUTTON3)
			toPress = MouseEvent.BUTTON3_MASK;

		if(type == MouseEvent.MOUSE_PRESSED) {
			r.mousePress(toPress);
		}
		else if(type == MouseEvent.MOUSE_RELEASED) {
			r.mouseRelease(toPress);
		}
	}

	public String toString() {
		String ret = "";
		switch(type) {
		case MouseEvent.MOUSE_PRESSED:
			ret = "Mouse Press:";
			break;
		case MouseEvent.MOUSE_RELEASED:
			ret = "Mouse Release:";
			break;
		case MouseEvent.MOUSE_CLICKED:
			ret = "Mouse Click:";
			break;
		default:
			throw new IllegalStateException(String.valueOf(type));
		}
				
		return ret + " x=" + decimalNumberFormat.format(x) + " y=" + decimalNumberFormat.format(y) + " button=" + button + getAttribs();
	}

}
