package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.text.PlainDocument;

import java.io.File;

import java.awt.Point;

import java.awt.event.KeyEvent;

import org.w3c.dom.Element;

public class PounderReaderWriterTest extends TestCase implements PounderConstants {

	public PounderReaderWriterTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PounderReaderWriterTest.class);
	}

	public void testReadToModel() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.getRecord().addItem(new DummyRecordingItem());
		File tempFile = File.createTempFile("dummy", FILE_EXTENSION);
		new PounderWriter().write(pm, tempFile);

		PounderModel pm2 = new PounderModel();
		new PounderReader().readToModel(pm2, tempFile);

		assertEquals(pm, pm2);
		assertEquals(tempFile, pm2.getFileModel().getFile());
	}

	//should work
	public void testWriteWithNoSetupInitializer() throws Exception {
		PounderModel pm = new PounderModel();
		File tempFile = File.createTempFile("dummy", FILE_EXTENSION);
		new PounderWriter().write(pm, tempFile);

		PounderModel pm2 = new PounderReader().readModel(tempFile);
		assertEquals(pm, pm2);
	}

	public void testWithIllegalXMLCharacter() throws Exception {
		//that crazy ^0 pasted dirctly from outputted XML file
		KeyItem item = new KeyItem(0, new NameIdentifier("Foo"), KeyEvent.VK_UNDEFINED, '', KeyEvent.KEY_TYPED, KeyEvent.CTRL_DOWN_MASK, 0);
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.getRecord().addItem(item);

		File file = File.createTempFile("testWithIllegalXMLCharacter", FILE_EXTENSION);
		file.deleteOnExit();
		new PounderWriter().write(pm, file);

		PounderModel pm2 = new PounderReader().readModel(file);
		assertEquals(pm, pm2);
	}

	public void testVersionIncluded() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		Element xml = new PounderWriter().buildXML(pm);
		assertEquals(FILE_FORMAT_VERSION, xml.getAttribute("version"));
	}

	public void testWillNotReadOldVersion() throws Exception {
		boolean caught = false;

		try {
			File f = new File("testFiles/oldFileFormat.pnd");
			new PounderReader().readModel(f);
		}
		catch(InvalidVersionException exc) {
			caught = true;
		}

		assertTrue(caught);
	}

	public void testStraightWrite() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		new PounderWriter().write(pm, File.createTempFile("foooo", FILE_EXTENSION));
	}

	public void testWriteRead() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		pm.getRecord().addItem(new DummyRecordingItem());
		PlainDocument comment = pm.getComment();
		comment.remove(0, comment.getLength());
		comment.insertString(0, "Foo", null);

		File f = File.createTempFile("foooo", FILE_EXTENSION);
		new PounderWriter().write(pm, f);

		PounderModel pm2 = new PounderReader().readModel(f);

		assertEquals(pm, pm2);
	}

}
