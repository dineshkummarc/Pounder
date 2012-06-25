package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DefaultIntegerModelTest extends IntegerModelTest {

	public DefaultIntegerModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DefaultIntegerModelTest.class);
	}

	public IntegerModel buildInstance() {
		return new DefaultIntegerModel();
	}

	public void testIntConstructor() throws Exception {
		IntegerModel im = new DefaultIntegerModel(7);
				
		assertEquals(7, im.getValue());
	}

}
