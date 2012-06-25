package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.event.MouseWheelEvent;
import java.awt.event.InputEvent;

import java.awt.Component;
import java.awt.Window;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.Component;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Represents a keyboard event.

@author Matthew Pekar

**/
public class MouseWheelItem extends ComponentItem {

	protected int modifiers;
	protected double x, y;
	protected int clickCount;
	protected boolean popupTrigger;
	protected int type; 
	protected int scrollAmount;
	protected int wheelRotation;

	public MouseWheelItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);
				
		if(! e.hasAttribute("modifiers"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"modifiers\"");
		if(! e.hasAttribute("x"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"x\"");
		if(! e.hasAttribute("y"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"y\"");
		if(! e.hasAttribute("clickCount"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"clickCount\"");
		if(! e.hasAttribute("popupTrigger"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"popupTrigger\"");
		if(! e.hasAttribute("type"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"type\"");
		if(! e.hasAttribute("scrollAmount"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"scrollAmount\"");
		if(! e.hasAttribute("wheelRotation"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"wheelRotation\"");

		this.modifiers = Integer.valueOf(e.getAttribute("modifiers")).intValue();
		this.x = Double.valueOf(e.getAttribute("x")).doubleValue();
		this.y = Double.valueOf(e.getAttribute("y")).doubleValue();
		this.clickCount = Integer.valueOf(e.getAttribute("clickCount")).intValue();
		this.popupTrigger = e.getAttribute("popupTrigger").equals("true");
		this.type = Integer.valueOf(e.getAttribute("type")).intValue();
		this.scrollAmount = Integer.valueOf(e.getAttribute("scrollAmount")).intValue();
		this.wheelRotation = Integer.valueOf(e.getAttribute("wheelRotation")).intValue();
	}

	public MouseWheelItem(MouseWheelEvent e, int windowID, long delay, ComponentIdentifierFactory f) {
		super(delay, windowID, f.build(e.getComponent()));
				
		Dimension size = e.getComponent().getSize();
		Point p = e.getPoint();
		this.modifiers = e.getModifiersEx();
		this.x = p.getX() / size.getWidth();
		this.y = p.getY() / size.getHeight();
		this.clickCount = e.getClickCount();
		this.popupTrigger = e.isPopupTrigger();
		this.type = e.getScrollType();
		this.scrollAmount = e.getScrollAmount();
		this.wheelRotation = e.getWheelRotation();
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.MouseWheelItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("modifiers", String.valueOf(modifiers));				
		e.setAttribute("x", String.valueOf(x));
		e.setAttribute("y", String.valueOf(y));
		e.setAttribute("clickCount", String.valueOf(clickCount));
		e.setAttribute("popupTrigger", String.valueOf(popupTrigger));
		e.setAttribute("type", String.valueOf(type));
		e.setAttribute("scrollAmount", String.valueOf(scrollAmount));
		e.setAttribute("wheelRotation", String.valueOf(wheelRotation));
	}

	public boolean equals(Object o) {
		MouseWheelItem mwi = (MouseWheelItem)o;
		return super.equals(o) && 
			(modifiers == mwi.modifiers) &&
			(x == mwi.x) && (y == mwi.y) &&
			(clickCount == mwi.clickCount) &&
			(popupTrigger == mwi.popupTrigger) &&
			(type == mwi.type) &&
			(scrollAmount == mwi.scrollAmount) &&
			(wheelRotation == mwi.wheelRotation);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws PlaybackException {
		Component c = getComponent(ww);
		Dimension size = c.getSize();
		int xpixel = (int)(x * size.getWidth());
		int ypixel = (int)(y * size.getHeight());
		MouseWheelEvent event = new MouseWheelEvent(c, MouseWheelEvent.MOUSE_WHEEL, System.currentTimeMillis(), modifiers, xpixel, ypixel, clickCount, popupTrigger, type, scrollAmount, wheelRotation);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}

	public String toString() {
		StringBuffer ret = new StringBuffer("MouseWheelItem: ");
		ret.append(" scrollAmount=" + scrollAmount);
		ret.append(" wheelRotation=" + wheelRotation);
		ret.append(" modifiers=" + InputEvent.getModifiersExText(modifiers));
		ret.append(" type=" + type);
		ret.append(super.getAttribs());
		return ret.toString();
	}

}
