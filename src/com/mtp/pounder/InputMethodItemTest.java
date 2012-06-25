package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JTextField;

import java.awt.Component;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

import javax.imageio.metadata.IIOMetadataNode;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.awt.event.InputMethodEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.Base64;

public class InputMethodItemTest extends ComponentItemTest {

	public InputMethodItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(InputMethodItemTest.class);
	}

  protected InputMethodEvent readEvent(File f) throws Exception {
    FileInputStream fin = new FileInputStream(f);
    ObjectInputStream oin = new ObjectInputStream(fin);
    InputMethodEvent ret = (InputMethodEvent)oin.readObject();
    ret.setSource(new JTextField());
    fin.close();
    oin.close();
    return ret;
  }

	public void testEncodeDecodeSerializedEvent() throws Exception {
    InputMethodEvent e = readEvent(new File("testFiles/SerializedInputMethodEvent1"));
    InputMethodItem item = new InputMethodItem(e, 0, 0, new ComponentIdentifierFactory());
    byte[] serialized = item.serializeEvent(e);
    char[] encoded = Base64.encode(serialized);
    String temp = new String(encoded);
    byte[] decoded = Base64.decode(temp.toCharArray());

    assertTrue(item.serializedEventsEqual(serialized, decoded));
  }

	public Collection buildTestItems() throws Exception {
		Vector ret = new Vector();

    PounderPrefs prefs = new PounderPrefs();
    ComponentIdentifierFactory factory = new ComponentIdentifierFactory();

    ret.add(new InputMethodItem(readEvent(new File("testFiles/SerializedInputMethodEvent1")), 0, 0, factory));
    ret.add(new InputMethodItem(readEvent(new File("testFiles/SerializedInputMethodEvent2")), 0, 0, factory));

		return ret;
	}

}
