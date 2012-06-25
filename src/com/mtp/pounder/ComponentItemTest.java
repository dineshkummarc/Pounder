package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Iterator;

public abstract class ComponentItemTest extends RecordingItemTest {

	public ComponentItemTest(String name) {
		super(name);
	}

	public void testWindowIDAndComponentCompared() throws Exception {
		Iterator i = buildTestItems().iterator();
		Iterator i2 = buildTestItems().iterator();
		while(i.hasNext()) {
			ComponentItem item = (ComponentItem)i.next();
			ComponentItem item2 = (ComponentItem)i2.next();
			item.setWindowID(0);
			item2.setWindowID(1);
			assertTrue(! item.equals(item2));
			item2.setWindowID(0);

			item.setComponentIdentifier(new WindowIdentifier());
			item2.setComponentIdentifier(new NameIdentifier("Foo"));
			assertTrue(! item.equals(item2));
		}
	}

}






