package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Color;

public class StatusModelTest extends TestCase {

	public StatusModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(StatusModelTest.class);
	}

	public StatusModel buildInstance() {
		return new StatusModel();
	}

	public void testSetStatus() throws Exception {
		StatusModel sm = buildInstance();
		sm.setStatus("booboo");
		assertEquals("booboo", sm.getStatus());
		assertEquals(Color.BLACK, sm.getColor());
	}

	public void testDefaultConstructor() throws Exception {
		StatusModel sm = buildInstance();
		assertEquals("", sm.getStatus());
		assertEquals(Color.BLACK, sm.getColor());
		assertEquals(null, sm.getException());
	}

	public void testHandleExceptionWithNullMessage() throws Exception {
		StatusModel sm = buildInstance();
		sm.handleException(new NullPointerException());

		String status = sm.getStatus();
		assertNotNull(status);
		assertEquals("java.lang.NullPointerException", status);
		assertEquals(Color.RED, sm.getColor());
		assertTrue(sm.getException() != null);
	}

	public void testHandleExceptionWithEmptyMessage() throws Exception {
		StatusModel sm = buildInstance();
		sm.handleException(new NullPointerException(""));

		String status = sm.getStatus();
		assertNotNull(status);
		assertEquals("java.lang.NullPointerException", status);
		assertEquals(Color.RED, sm.getColor());
		assertTrue(sm.getException() != null);
	}

	public void testHandleException() throws Exception {
		StatusModel sm = buildInstance();
		sm.handleException(new Exception("Bluh"));

		assertEquals("Bluh", sm.getStatus());
		assertEquals(Color.RED, sm.getColor());
		assertTrue(sm.getException() != null);
	}

	public void testHandleExceptionWithHumanMessage() throws Exception {
		StatusModel sm = buildInstance();
		sm.handleException("Wootar", new Exception("Bluh"));

		assertEquals("Wootar", sm.getStatus());
		assertEquals(Color.RED, sm.getColor());
		assertTrue(sm.getException() != null);
	}

	protected boolean changed = false;
	public void testChangeFiredOnSetStatus() throws Exception {
		StatusModel sm = buildInstance();
		changed = false;
		StatusModelListener l = new StatusModelListener() {
				public void statusModelChanged(StatusModel sm) {
					changed = true;
				}
			};
		sm.getListeners().add(l);

		sm.setStatus("Foooo");
		assertTrue(changed);
	}

	public void testChangeFiredOnHandleException() throws Exception {
		StatusModel sm = buildInstance();
		changed = false;
		StatusModelListener l = new StatusModelListener() {
				public void statusModelChanged(StatusModel sm) {
					changed = true;
				}
			};
		sm.getListeners().add(l);

		sm.handleException(new Exception("Bluh"));
		assertTrue(changed);
	}

}
