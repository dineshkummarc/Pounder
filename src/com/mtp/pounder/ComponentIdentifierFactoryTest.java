package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComponentIdentifierFactoryTest extends TestCase {

	public ComponentIdentifierFactoryTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(ComponentIdentifierFactoryTest.class);
	}

	public void testBuildNameIdentifier() throws Exception {
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		p.setName("one");
		JPanel p2 = new JPanel();
		p2.setName("two");
		p.add(p2);
		f.getContentPane().add(p);

		ComponentIdentifier ni = new ComponentIdentifierFactory().buildNameIdentifier(p2);
		//NameIdentifier ni = new ComponentIdentifierFactory().buildNameIdentifier(p2);
		assertEquals(p2, ni.getComponent(f));
	}
		
}
