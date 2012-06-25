package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Point;

public class PointModelTest extends TestCase {

	public PointModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PointModelTest.class);
	}

	public void testEquals() throws Exception {
		PointModel pm1 = new PointModel(1, 77);
		PointModel pm2 = new PointModel(1, 77);
		assertEquals(pm1, pm2);

		pm1 = new PointModel(1, 77);
		pm2 = new PointModel(1, 78);
		assertTrue(! pm1.equals(pm2));

		pm1 = new PointModel(88, 77);
		pm2 = new PointModel(1, 77);
		assertTrue(! pm1.equals(pm2));
	}

	public void testDefaultConstructor() throws Exception {
		PointModel pm = new PointModel();
		assertEquals(new Point(0,0), pm.getValue());
		assertEquals(0, pm.getX());
		assertEquals(0, pm.getY());
	}

}
