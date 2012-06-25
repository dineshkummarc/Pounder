package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public abstract class IntegerModelTest extends TestCase {

	public IntegerModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(IntegerModelTest.class);
	}

	public abstract IntegerModel buildInstance();

	public void testDefaultConstructor() throws Exception {
		IntegerModel im = buildInstance();
		assertEquals(0, im.getValue());
	}

	public void testEquals() throws Exception {
		assertEquals(buildInstance(), buildInstance());

		IntegerModel im1 = buildInstance();
		im1.setValue(22);
		IntegerModel im2 = buildInstance();
		im2.setValue(22);
		assertEquals(im1, im2);
				
		im2.setValue(23);
		assertTrue(! im1.equals(im2));
	}

	public void testIntConstructor() throws Exception {
		IntegerModel im = buildInstance();
				
		assertEquals(7, im.getValue());
	}

	public void testGetSet() throws Exception {
		IntegerModel im = buildInstance();

		im.setValue(88);
		assertEquals(88, im.getValue());
	}

}
