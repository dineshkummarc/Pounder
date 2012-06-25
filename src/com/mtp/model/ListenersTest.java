package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Collection;
import java.util.Iterator;

import java.lang.ref.WeakReference;

public class ListenersTest extends TestCase {

	public ListenersTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(ListenersTest.class);
	}

	public void testGetFiringIteratorReturnsActualObjects() throws Exception {
		Listeners l = new Listeners();
		Object o1 = "Foo";
		l.add(o1);
				
		Iterator i = l.getFiringIterator();
		while(i.hasNext()) {
			assertTrue(! (i.next() instanceof WeakReference));
		}
	}

	public void testAddWhileChangeFiring() throws Exception {
		Listeners l = new Listeners();
		Object o1 = "Foo";
		Object o2 = "Bar";

		l.add(o1);

		Iterator i = l.getFiringIterator();
		while(i.hasNext()) {
			i.next();
			l.add(o2);
		}

		assertEquals(2, l.getSize());
		assertTrue(l.contains(o1));
		assertTrue(l.contains(o2));
	}

	public void testAdd() throws Exception {
		Listeners l = new Listeners();
		Object o1 = "Foo";
		l.add(o1);
		assertTrue(l.contains(o1));
	}
		
	public void testRemove() throws Exception {
		Listeners l = new Listeners();
		Object o1 = "Foo";
		l.add(o1);
		l.remove(o1);
		assertTrue(! l.contains(o1));
	}

}






