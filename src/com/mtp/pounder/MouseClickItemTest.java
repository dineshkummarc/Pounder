package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import java.awt.event.MouseEvent;

import java.util.Collection;
import java.util.Vector;

public class MouseClickItemTest extends ComponentItemTest {

	public MouseClickItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(MouseClickItemTest.class);
	}

	public Collection buildTestItems() throws Exception {
		Vector ret = new Vector();

		Component component = getDummyComponent();
		MouseEvent me = new MouseEvent(component, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 50, 51, 1, true);
		ret.add(new MouseClickItem(me, 0, 100, new ComponentIdentifierFactory()));

		return ret;
	}

}
