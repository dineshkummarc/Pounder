package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Collection;
import java.util.Iterator;

public abstract class RecordingItemTest extends TestCase {

	public RecordingItemTest(String name) {
		super(name);
	}

	protected Component dummyComponent;
	protected JFrame frame;

	public Component getDummyComponent() {
		return dummyComponent;
	}

	public JFrame getDummyFrame() {
		return frame;
	}

	public void setUp() throws Exception {
		super.setUp();
		frame = new JFrame();
		dummyComponent = new JPanel();
		dummyComponent.setSize(100, 100);
		dummyComponent.setName("Dummy");
		frame.getContentPane().add(dummyComponent);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		frame.dispose();
		frame = null;
	}

	public abstract Collection buildTestItems() throws Exception;

	public void testEquals() throws Exception {
		Iterator i = buildTestItems().iterator();
		Iterator i2 = buildTestItems().iterator();
		while(i.hasNext()) {
			RecordingItem item = (RecordingItem)i.next();
			RecordingItem item2 = (RecordingItem)i2.next();
			assertEquals(item, item2);
		}
	}

	public void testDelaysCompared() throws Exception {
		Iterator i = buildTestItems().iterator();
		Iterator i2 = buildTestItems().iterator();
		while(i.hasNext()) {
			RecordingItem item = (RecordingItem)i.next();
			RecordingItem item2 = (RecordingItem)i2.next();
			item.setDelay(0);
			item2.setDelay(1);
			assertTrue(! item.equals(item2));
		}
	}

	public void testXMLWriteRead() throws Exception {
		Iterator i = buildTestItems().iterator();
		while(i.hasNext()) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			RecordingItem item = (RecordingItem)i.next();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			Element xml = item.toXML(doc);

			DOMSource source = new DOMSource(xml);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(stream);
			t.transform(source, result);

			stream.flush();
			byte[] bytes = stream.toByteArray();

      doc = db.parse(new ByteArrayInputStream(bytes));
						
			PounderPrefs prefs = new PounderPrefs();
			ComponentIdentifierFactory cif = new ComponentIdentifierFactory(prefs);
			assertEquals(item, RecordingItem.instantiate(doc.getDocumentElement(), prefs, cif));
		}
	}

}







