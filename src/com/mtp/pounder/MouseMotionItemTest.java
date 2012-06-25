package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import java.awt.event.MouseEvent;

import java.util.Collection;
import java.util.Vector;

public class MouseMotionItemTest extends ComponentItemTest {

	public MouseMotionItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(MouseMotionItemTest.class);
	}

	public Collection buildTestItems() throws Exception {
		Vector ret = new Vector();

		Component component = getDummyComponent();
		MouseEvent me = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, 50, 51, 1, true);
		ret.add(new MouseMotionItem(me, 0, 100, new ComponentIdentifierFactory()));

		return ret;
	}

}
