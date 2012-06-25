package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.gui.WindowWatcher;

import java.applet.Applet;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JButton;

import javax.naming.TimeLimitExceededException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

public class TestInstanceFactoryTest extends TestCase {

	public TestInstanceFactoryTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestInstanceFactoryTest.class);
	}

	public static class AlreadyVisibleWindowConduit implements ComponentConduit {
		public Component getComponent() {
			JFrame ret = new JFrame();
			ret.setVisible(true);
			return ret;
		}
	}

	protected WindowWatcher windowWatcher;

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}

	public static class DummyComponentBuilder {
		public Component buildComponent() {
			return null;
		}

		public boolean equals(Object o) {
			return (o instanceof DummyComponentBuilder);
		}
	}

	public void testInstantiateInstance() throws Exception {
		TestInstanceFactory tif = new TestInstanceFactory("java.lang.String");
		Object o = tif.instantiateInstance(getClass().getClassLoader());
		assertTrue(o instanceof String);
	}

  protected Document buildDocument() throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    return db.newDocument();
  }

	public void testElementConstructor() throws Exception {
		TestInstanceFactory tif = new TestInstanceFactory("com.mtp.pounder.TestInstanceFactoryTest.DummyComponentBuilder");

		TestInstanceFactory tif2 = new TestInstanceFactory(tif.toXML(buildDocument()));
		assertEquals(tif, tif2);		
	}

	public void testInitializeDoesNotHideCurrentlyVisibleWindow() throws Exception {
		TestInstanceFactory tif = new TestInstanceFactory("com.mtp.pounder.TestInstanceFactoryTest$AlreadyVisibleWindowConduit");
		Object o = tif.instantiateInstance();
		Window w = tif.wrapInWindow(o, new PounderPrefs());
		windowWatcher.waitTillWindowPresent(w);

		assertNotNull(windowWatcher.getWindowByID(0));
		assertEquals(null, windowWatcher.getWindowByID(1));
	}

	public void testShowNewTestInstance() throws Exception {
		TestInstanceFactory si = new TestInstanceFactory("javax.swing.JButton");
		JButton b = (JButton)si.showNewTestInstance(new PounderPrefs(), windowWatcher);

		assertNotNull(b);
	}

	private boolean waitTillWindowPresentCalled = false;
	public void testShowNewTestInstanceWithWindowWatcher() throws Exception {
		TestInstanceFactory si = new TestInstanceFactory("javax.swing.JFrame");

		waitTillWindowPresentCalled = false;
		WindowWatcher ww = new WindowWatcher() {
				public void waitTillWindowPresent(Window w) throws TimeLimitExceededException, InterruptedException {
					waitTillWindowPresentCalled = true;
					super.waitTillWindowPresent(w);
				}
			};

		si.showNewTestInstance(new PounderPrefs(), ww);

		assertTrue("waitTillWindowPresent should have been called ", waitTillWindowPresentCalled);
	}

	public void testWrapInWindowWithNullObject() throws Exception {
		TestInstanceFactory si = new TestInstanceFactory("javax.swing.JFrame");
		assertEquals(null, si.wrapInWindow(null, new PounderPrefs()));
	}

	public void testWrapInWindowWithAppletReturnsAppletFrame() throws Exception {
		TestInstanceFactory si = new TestInstanceFactory("java.applet.Applet");
    assertTrue(si.wrapInWindow(new Applet(), new PounderPrefs()) instanceof AppletFrame);
	}

}






