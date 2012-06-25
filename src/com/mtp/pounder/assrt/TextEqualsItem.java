package com.mtp.pounder.assrt;

import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.ComponentIdentifierFactory;
import com.mtp.pounder.PlaybackException;
import com.mtp.pounder.NameIdentifier;

import com.mtp.gui.WindowWatcher;

import javax.swing.text.JTextComponent;

import java.util.Iterator;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.Dialog;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mtp.i18n.Strings;

/**

Asserts that the text of a Component is a certain value.

@author Matthew Pekar

**/
public class TextEqualsItem extends WindowAssertItem {

	/** The name of the JTextComponent to check. **/
	protected String componentName;
	/** The desired text value of the JTextComponent. **/
	protected String desiredValue;

	public TextEqualsItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

		if(! e.hasAttribute("componentName"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") + "\"componentName\"");
		componentName = e.getAttribute("componentName");

		if(! e.hasAttribute("desiredValue"))
			throw new IllegalArgumentException(Strings.getString("ElementMustContainAttribute:") +  "\"desiredValue\"");
		desiredValue = e.getAttribute("desiredValue");
	}

	public TextEqualsItem(int windowID, String componentName, String desiredValue) {
		super(windowID);
		this.componentName = componentName;
		this.desiredValue = desiredValue;
	}

	public TextEqualsItem(String windowTitle, String componentName, String desiredValue) {
		super(windowTitle);
		this.componentName = componentName;
		this.desiredValue = desiredValue;
	}

	public boolean equals(Object o) {
		if(! super.equals(o))
			return false;

		TextEqualsItem wsi = (TextEqualsItem)o;

		if(desiredValue == null) {
			if(wsi.desiredValue != null)
				return false;
		}
		else if(! desiredValue.equals(wsi.desiredValue))
			return false;

		return componentName.equals(wsi.componentName);
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

		e.setAttribute("componentName", componentName);
		e.setAttribute("desiredValue", desiredValue);
	}

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.assrt.TextEqualsItem");
	}

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Window w = getWindow(ww);
		Component c = new NameIdentifier(componentName).getComponent(w);
		if(c == null)
			throw new PlaybackException(Strings.getString("AssertionFailedJTextComponentNotFound:") + componentName);

		if(! (c instanceof JTextComponent))
			throw new PlaybackException(Strings.getString("AssertionFailedJTextComponentNotFound:") + componentName);

		JTextComponent tc = (JTextComponent)c;
		if(desiredValue == null) {
			if(tc.getText() != null) {
				if(tc.getText().length() > 0)
					throw new PlaybackException(Strings.getString("AssertionFailedDesiredValueNotPresent:") + desiredValue);
			}
		}
		else if(! desiredValue.equals(tc.getText()))
			throw new PlaybackException(Strings.getString("AssertionFailedDesiredValueNotPresent:") + desiredValue);
	}

	public String toString() {
		return "Assert Text Equals:" + 
			" componentName=" + componentName +
			" desiredValue=" + desiredValue +
			super.getAttribs();
	}

}
