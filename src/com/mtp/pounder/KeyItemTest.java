package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javax.swing.JPanel;

import java.awt.Component;

import java.awt.event.KeyEvent;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.util.Collection;
import java.util.Vector;

public class KeyItemTest extends ComponentItemTest implements PounderConstants {

	public KeyItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(KeyItemTest.class);
	}

	protected KeyItem buildKeyItem() {
		KeyEvent event = new KeyEvent(getDummyComponent(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, 't');
		return buildKeyItem(event);
	}

	protected KeyItem buildKeyItem(KeyEvent event) {
		return new KeyItem(event, 0, 100, new ComponentIdentifierFactory());
	}

	public Collection buildTestItems() throws Exception {
		Vector ret = new Vector();
				
		KeyEvent event = new KeyEvent(getDummyComponent(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, 't');
		ret.add(buildKeyItem(event));

		event = new KeyEvent(getDummyFrame(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, 't');
		ret.add(buildKeyItem(event));

		event = new KeyEvent(getDummyComponent(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_O, '');
		ret.add(buildKeyItem(event));

		ret.add(new KeyItem(0, new NameIdentifier("Foo"), KeyEvent.VK_UNDEFINED, '', KeyEvent.KEY_TYPED, KeyEvent.CTRL_DOWN_MASK, 0));

		return ret;
	}

	public void testKeyReleasedJapaneseCharacter() throws Exception {
		String s = "\u00e9";
		assertEquals(1, s.length());

		char c = s.charAt(0);
		int code = (int)c;
		KeyEvent event = new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, 0, 0, code, KeyEvent.CHAR_UNDEFINED);

		KeyItem i1 = buildKeyItem(event);

		Element xml = i1.buildXMLElement(buildDocument());
		i1.addXMLAttributes(xml, buildDocument());
		KeyItem i2 = new KeyItem(xml, new PounderPrefs(), new ComponentIdentifierFactory());

		assertEquals(i1, i2);
	}

	public void testCtrlO() throws Exception {
		char original = '';
		int originalAsInt = (int)original;
		String s = String.valueOf(originalAsInt);
		Integer i = Integer.valueOf(s);
		char result = (char)i.intValue();
		assertEquals(original, result);
	}

  protected Document buildDocument() throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    return db.newDocument();
  }

	public void testBuildXMLNodeForKeyPressed() throws Exception {
		Component component = getDummyComponent();
		KeyEvent event = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, KeyEvent.CHAR_UNDEFINED);
		KeyItem i1 = buildKeyItem(event);
		Element node = i1.toXML(buildDocument());
		assertTrue(! node.hasAttribute("keyChar"));
		assertTrue(node.hasAttribute("keyCode"));
	}

	public void testBuildXMLNodeForKeyTyped() throws Exception {
		Component component = getDummyComponent();
		KeyEvent event = new KeyEvent(component, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 't');
		KeyItem i1 = buildKeyItem(event);
		Element node = i1.toXML(buildDocument());
		assertTrue(node.hasAttribute("keyChar"));
		assertTrue(! node.hasAttribute("keyCode"));
	}

}
