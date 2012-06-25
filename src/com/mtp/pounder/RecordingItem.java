package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.awt.Component;

import java.text.NumberFormat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

This represents a single action in a recording.  It can be played back
at a later time.

@author Matthew Pekar

**/
public abstract class RecordingItem {

	/** Little utility class for printing decimals in an easy-on-the-eye way. **/
	protected static NumberFormat decimalNumberFormat;

	static {
		decimalNumberFormat = NumberFormat.getNumberInstance();
		decimalNumberFormat.setMaximumFractionDigits(2);
		decimalNumberFormat.setMinimumFractionDigits(2);
	}

	/** Attempt to instantiate the given item given an XML Element. **/
	public static RecordingItem instantiate(Element xml, PounderPrefs prefs, ComponentIdentifierFactory f) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		Class c = Class.forName(xml.getNodeName());
		Class[] params = {Element.class, PounderPrefs.class, ComponentIdentifierFactory.class};
		Constructor cons = c.getConstructor(params);
		Object[] args = new Object[3];
		args[0] = xml;
		args[1] = prefs;
		args[2] = f;
		try {
			return (RecordingItem)cons.newInstance(args);
		}
		catch(InvocationTargetException exc) {
			InstantiationException ie = new InstantiationException("Error instantiating class " + xml.getNodeName() + ": " + exc.getCause());
      ie.initCause(exc);
      throw ie;
		}
	}

	protected long delay;

	public RecordingItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		if(! e.hasAttribute("delay"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"delay\"");

		this.delay = Long.valueOf(e.getAttribute("delay")).longValue();
	}

	public RecordingItem(long delay) {
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long d) {
		this.delay = d;
	}
		
	/** Subclasses should override buildXMLNode() and addXMLAttributes(). **/
	public final Element toXML(Document doc) {
		Element ret = buildXMLElement(doc);
		addXMLAttributes(ret, doc);
		return ret;
	}

	protected abstract Element buildXMLElement(Document doc);

	protected void addXMLAttributes(Element e, Document doc) {
		e.setAttribute("delay", String.valueOf(delay));
	}

	/** Playback on this Component. **/
	public abstract void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception;

	/** Should be called by all subclasses. **/
	public boolean equals(Object o) {
		if(o == null)
			return false;
		RecordingItem ri = (RecordingItem)o;
		return delay == ri.delay;
	}

	protected String getAttribs() {
		return " delay=" + delay;
	}

}
