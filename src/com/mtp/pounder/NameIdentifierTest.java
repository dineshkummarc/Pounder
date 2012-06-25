package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import javax.swing.JFrame;

public class NameIdentifierTest extends ComponentIdentifierTest {

	public NameIdentifierTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(NameIdentifierTest.class);
	}

	protected ComponentIdentifier buildComponentIdentifier(Component c, JFrame f) throws Exception {
		return new NameIdentifier(c.getName());
		//				return new ComponentIdentifierFactory().buildNameIdentifier(c, f, ww);
	}

	public void testNullNameInConstructor() throws Exception {
		boolean caught = false;
		try {
			new NameIdentifier(null);
		}
		catch(IllegalArgumentException exc) {
			caught = true;
		}

		assertTrue(caught);
	}

}
