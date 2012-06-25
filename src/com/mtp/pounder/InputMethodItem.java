package com.mtp.pounder;

import java.awt.event.InputMethodEvent;

import java.awt.Window;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.w3c.dom.CDATASection;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

import org.Base64;

/**

Represents a mouse click.

@author Matthew Pekar

**/
public class InputMethodItem extends ComponentItem {

  /** The serialized InputMethodEvent, with source set to null. **/
  protected byte[] serializedEvent;

	public InputMethodItem(Element e, PounderPrefs prefs, ComponentIdentifierFactory f) {
		super(e, prefs, f);

    if(e.getChildNodes().getLength() != 1)
      throw new IllegalArgumentException("Element must contain serialized InputMethodEvent text child.");
    
    serializedEvent = Base64.decode(e.getChildNodes().item(0).getNodeValue().toCharArray());
	}

	public InputMethodItem(InputMethodEvent e, int windowID, long delay, ComponentIdentifierFactory f) {
		super(delay, windowID, f.build((Component)e.getSource()));

    try {
      this.serializedEvent = serializeEvent(e);
    }
    catch(IOException exc) {
      IllegalArgumentException iae = new IllegalArgumentException("InputMethodEvent could not be serialized");
      iae.initCause(exc);
      throw iae;
    }
	}

  protected byte[] serializeEvent(InputMethodEvent e) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    try {
      oos.writeObject(e);
      baos.close();
      oos.close();
      return baos.toByteArray();
    }
    finally {
      baos.close();
      oos.close();
    }
  }

	protected Element buildXMLElement(Document doc) {
		return doc.createElement("com.mtp.pounder.InputMethodItem");
	}

	protected void addXMLAttributes(Element e, Document doc) {
		super.addXMLAttributes(e, doc);

    String serializedEventString = new String(Base64.encode(serializedEvent));
    Text serializedEventData = doc.createTextNode(serializedEventString);

    e.appendChild(serializedEventData);
	}

  protected boolean serializedEventsEqual(byte[] event1, byte[] event2) {
    if(event1 == null && event2 != null ||
       event2 == null && event1 != null)
      return false;
    if(event1.length != event2.length)
      return false;

    for(int i=0;i < event1.length;i++) {
      if(event1[i] != event2[i])
        return false;
    }

    return true;
  }

	public boolean equals(Object o) {
		return super.equals(o) &&
      serializedEventsEqual(serializedEvent, ((InputMethodItem)o).serializedEvent);
	}

  protected InputMethodEvent restoreEvent(byte[] serialized) throws IOException, ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
    ObjectInputStream ois = new ObjectInputStream(bais);
    try {
      Object ret = ois.readObject();
      return (InputMethodEvent)ret;
    }
    finally {
      bais.close();
      ois.close();
    }
  }

	public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
		Component c = getComponent(ww);
    InputMethodEvent event = restoreEvent(serializedEvent);
    event.setSource(c);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}

	public String toString() {
		String ret = "InputMethodItem:";
		return ret + getAttribs();
	}

}
