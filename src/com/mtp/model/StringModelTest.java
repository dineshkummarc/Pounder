package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public abstract class StringModelTest extends TestCase {

	public StringModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(StringModelTest.class);
	}

	public abstract StringModel buildInstance();

	public void testGetSetString() throws Exception {
		StringModel sm = buildInstance();
		sm.setString("FooBar");
		assertEquals("FooBar", sm.getString());
	}

	protected boolean changeFired;

	public void testAddListener() throws Exception {
		StringModel sm = buildInstance();
		changeFired = false;
		sm.addListener(new StringModelListener() {
				public void stringModelChanged(StringModel source) {
					changeFired = true;
				}
			});

		sm.setString("FooBar");
		assertTrue(changeFired);
	}

	public void testRemoveListener() throws Exception {
		StringModel sm = buildInstance();
		changeFired = false;
		StringModelListener l = new StringModelListener() {
				public void stringModelChanged(StringModel source) {
					changeFired = true;
				}
			};

		sm.addListener(l);
		sm.removeListener(l);

		sm.setString("FooBar");
		assertTrue(! changeFired);
	}
}






