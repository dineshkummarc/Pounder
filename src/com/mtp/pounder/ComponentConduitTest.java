package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**

Abstract class for testing ComponentConduit's.

@author Matthew Pekar

**/
public abstract class ComponentConduitTest extends TestCase {

	public ComponentConduitTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(ComponentConduitTest.class);
	}

	protected abstract ComponentConduit buildComponentConduit();

	public void testEquals() throws Exception {
		ComponentConduit b1 = buildComponentConduit();
		ComponentConduit b2 = buildComponentConduit();
		assertEquals(b1, b2);
	}

}
