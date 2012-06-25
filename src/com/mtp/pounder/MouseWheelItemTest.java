package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import java.awt.event.MouseWheelEvent;

import org.w3c.dom.Element;

import java.util.Collection;
import java.util.Vector;

public class MouseWheelItemTest extends ComponentItemTest implements PounderConstants {

	public MouseWheelItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(MouseWheelItemTest.class);
	}

	public Collection buildTestItems() throws Exception {
		Vector ret = new Vector();
		Component c = getDummyComponent();
		MouseWheelEvent mwe = new MouseWheelEvent(c, MouseWheelEvent.MOUSE_WHEEL, System.currentTimeMillis(), 0, 0, 0, 1, false, MouseWheelEvent.WHEEL_BLOCK_SCROLL, 1, 1);
		return ret;
	}


}
