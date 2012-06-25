package com.mtp.pounder;

import java.awt.event.MouseEvent;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.Window;
import java.awt.Robot;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Represents mouse motion.

@author Matthew Pekar

**/
public class MouseMotionItem extends ComponentItem {

	/** Coordinates of pointer relative to parent Window. **/
	protected double x, y;
	protected int modifiers;
	protected int type; 
	protected int count;

	public MouseMotionItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("x"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"x\"");
		if(! e.hasAttribute("y"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"y\"");
		if(! e.hasAttribute("type"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"type\"");
		if(! e.hasAttribute("modifiers"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"modifiers\"");
		if(! e.hasAttribute("count"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"count\"");

		this.x = Double.valueOf(e.getAttribute("x")).doubleValue();
		this.y = Double.valueOf(e.getAttribute("y")).doubleValue();
		this.type = Integer.valueOf(e.getAttribute("type")).intValue();
		this.modifiers = Integer.valueOf(e.getAttribute("modifiers")).intValue();
		this.count = Integer.valueOf(e.getAttribute("count")).intValue();
	}

	public MouseMotionItem(MouseEvent e, int windowID, long delay, ComponentIdentifierFactory f) {
		super(delay, windowID, f.build(e.getComponent()));

		Component c = e.getComponent();
		Point p = e.getPoint();
		this.x = p.getX() / c.getWidth();
		this.y = p.getY() / c.getHeight();
		this.modifiers = e.getModifiersEx();
		this.type = e.getID();
		this.count = e.getClickCount();
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.MouseMotionItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("x", String.valueOf(x));
		e.setAttribute("y", String.valueOf(y));
		e.setAttribute("count", String.valueOf(count));
		e.setAttribute("modifiers", String.valueOf(modifiers));
		e.setAttribute("type", String.valueOf(type));
	}

	public boolean equals(Object o) {
		MouseMotionItem mmi = (MouseMotionItem)o;
		return super.equals(o) && 
			(x == mmi.x) && (y == mmi.y) &&
			(modifiers == mmi.modifiers) &&
			(type == mmi.type) &&
			(count == mmi.count);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Component c = getComponent(ww);
		int xpixel = (int)(x * c.getWidth());
		int ypixel = (int)(y * c.getHeight());

		//boolean flag= false;
		/** Only use Robot for a drag, probably has some expensive
		 * creation setup.  Also moves the mouse pointer which is very
		 * annoying. **/
		if(type == MouseEvent.MOUSE_DRAGGED) {
			Robot r = getRobot(c);
			if(! c.isShowing())
				throw new IllegalStateException("Component must be showing");

			Point loc = c.getLocationOnScreen();
			System.out.println("Robot.mouseMove("+(loc.x+xpixel)+", "+(loc.y+ypixel)+")");
			r.mouseMove(loc.x + xpixel, loc.y + ypixel);
		}
		else if (type == MouseEvent.MOUSE_MOVED) {
			Robot r = getRobot(c);
			if(! c.isShowing()) {
				//System.out.println("we would have an error here for component "+c.getName()+" - "+c.getBounds().toString()+" - "+c.getClass().toString());
				c.setVisible(true);
				//flag= true;
			}
				//{throw new IllegalStateException("Component must be showing");}

			Point loc = c.getLocationOnScreen();
			System.out.println("Robot.mouseMove("+(loc.x+xpixel)+", "+(loc.y+ypixel)+")");
			r.mouseMove(loc.x + xpixel, loc.y + ypixel);
			//if (flag) System.out.println("mouse at "+(loc.x + xpixel) +" - "+ (loc.y + ypixel));
		}
		//else {
			
			//MouseEvent event = new MouseEvent(c, type, System.currentTimeMillis(), modifiers, xpixel, ypixel, count, false);
			//System.out.println("postMouseEvent("+xpixel+", "+ypixel+")");
			//Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
			
		//}
	}

	public String toString() {
		String ret = "";
		switch(type) {
		case MouseEvent.MOUSE_MOVED:
			ret = "Mouse Moved:";
			break;
		case MouseEvent.MOUSE_ENTERED:
			ret = "Mouse Entered:";
			break;
		case MouseEvent.MOUSE_EXITED:
			ret = "Mouse Exited:";
			break;
		case MouseEvent.MOUSE_DRAGGED:
			ret = "Mouse Dragged:";
			break;
		default:
			throw new IllegalStateException(String.valueOf(type));
		}
				
		return ret + " x=" + decimalNumberFormat.format(x) + " y=" + decimalNumberFormat.format(y) + getAttribs();
	}

}
