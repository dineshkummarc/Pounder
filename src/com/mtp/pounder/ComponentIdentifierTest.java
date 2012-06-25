package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**

Abstract class for testing ComponentIdentifier's.

@author Matthew Pekar

**/
public abstract class ComponentIdentifierTest extends TestCase {

	public ComponentIdentifierTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(ComponentIdentifierTest.class);
	}

	protected abstract ComponentIdentifier buildComponentIdentifier(Component c, JFrame f) throws Exception;

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAsString() throws Exception {
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setName("one");
		JPanel p2 = new JPanel();
		p2.setName("two");
		p.add(p2);
		f.getContentPane().add(p);

		ComponentIdentifier ci = buildComponentIdentifier(p2, f);
		String s = ci.asString();

		ComponentIdentifier ci2 = new ComponentIdentifierFactory().buildFromString(s);

		assertEquals(ci, ci2);
		assertEquals(p2, ci2.getComponent(f));
	}

	public void testGetComponent() throws Exception {
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setName("one");
		JPanel p2 = new JPanel();
		p2.setName("two");
		p.add(p2);
		f.getContentPane().add(p);

		ComponentIdentifier ci = buildComponentIdentifier(p2, f);
		assertEquals(p2, ci.getComponent(f));
	}
		
}
