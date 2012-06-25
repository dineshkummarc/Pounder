package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DefaultStringModelTest extends StringModelTest {

	public DefaultStringModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DefaultStringModelTest.class);
	}

	public StringModel buildInstance() {
		return new DefaultStringModel();
	}

}






