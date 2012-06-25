package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.event.KeyEvent;

import java.awt.Component;
import java.awt.Window;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.Component;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Represents a keyboard event.

@author Matthew Pekar

**/
public class KeyItem extends ComponentItem {
		
	protected int keyCode;
	protected char keyChar;
	/** Pressed, released, or typed. **/
	protected int type; 
	protected int modifiers;

	public KeyItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);
				
		if(! e.hasAttribute("type"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"type\"");
		if(! e.hasAttribute("modifiers"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"modifiers\"");
				
		this.type = Integer.valueOf(e.getAttribute("type")).intValue();
		if(type == KeyEvent.KEY_TYPED) {
			if(! e.hasAttribute("keyChar"))
				throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"keyChar\"");
			this.keyChar = (char)(Integer.valueOf(e.getAttribute("keyChar")).intValue());
			this.keyCode = KeyEvent.VK_UNDEFINED;
		}
		else {
			if(! e.hasAttribute("keyCode"))
				throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"keyCode\"");
			this.keyCode = Integer.valueOf(e.getAttribute("keyCode")).intValue();
			this.keyChar = KeyEvent.CHAR_UNDEFINED;
		}
		this.modifiers = Integer.valueOf(e.getAttribute("modifiers")).intValue();
	}

	public KeyItem(KeyEvent e, int windowID, long delay, ComponentIdentifierFactory f) {
		super(delay, windowID, f.build(e.getComponent()));
				
		if(e.getID() == KeyEvent.KEY_TYPED) {
			this.keyCode = KeyEvent.VK_UNDEFINED;
			this.keyChar = e.getKeyChar();
		}
		else {
			this.keyCode = e.getKeyCode();
			this.keyChar = KeyEvent.CHAR_UNDEFINED;
		}

		this.modifiers = e.getModifiersEx();
		this.type = e.getID();
	}

	public KeyItem(int windowID, ComponentIdentifier component, int keyCode, char keyChar, int type, int modifiers, long delay) {
		super(delay, windowID, component);

		this.keyCode = keyCode;
		this.keyChar = keyChar;
		this.type = type;
		this.modifiers = modifiers;
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.KeyItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		if(keyChar != KeyEvent.CHAR_UNDEFINED)
			e.setAttribute("keyChar", String.valueOf((int)keyChar));
		if(keyCode != KeyEvent.VK_UNDEFINED)
			e.setAttribute("keyCode", String.valueOf(keyCode));
		e.setAttribute("modifiers", String.valueOf(modifiers));
		e.setAttribute("type", String.valueOf(type));
	}

	public boolean equals(Object o) {
		KeyItem ki = (KeyItem)o;
		return super.equals(o) && 
			(keyCode == ki.keyCode) &&
			(keyChar == ki.keyChar) &&
			(type == ki.type) &&
			(modifiers == ki.modifiers);
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws PlaybackException {
		Component c = getComponent(ww);
		KeyEvent event = new KeyEvent(c, type, System.currentTimeMillis(), modifiers, keyCode, keyChar);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}

	protected String getTypeAsString(int type) {
		switch(type) {
		case KeyEvent.KEY_TYPED:
			return "Typed";
		case KeyEvent.KEY_PRESSED:
			return "Pressed";
		case KeyEvent.KEY_RELEASED:
			return "Released";
		default:
			return "Unknown";
		}
	}

	public String toString() {
		StringBuffer ret = new StringBuffer("Key ");
		ret.append(getTypeAsString(type) + ":");
		if(keyCode != KeyEvent.VK_UNDEFINED)
			ret.append(" code=" + KeyEvent.getKeyText(keyCode));
		else
			ret.append(" char=" + ((int)keyChar));
		ret.append(" modifiers=" + KeyEvent.getKeyModifiersText(modifiers));
		ret.append(super.getAttribs());
		return ret.toString();
	}

}
