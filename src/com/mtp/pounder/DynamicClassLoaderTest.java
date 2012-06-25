package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class DynamicClassLoaderTest extends TestCase {

	public DynamicClassLoaderTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DynamicClassLoaderTest.class);
	}

	protected class CheckedDynamicClassLoader extends DynamicClassLoader {
		public boolean loadClassFromFileCalled = false;

		public CheckedDynamicClassLoader() {
						
		}

		protected Class loadClassFromFile(String name, File f) {
			loadClassFromFileCalled = true;
			return super.loadClassFromFile(name, f);
		}
				
	}

	public void testFilters() throws Exception {
		CheckedDynamicClassLoader cl = new CheckedDynamicClassLoader();
		cl.loadClass("javax.swing.JButton");
		cl.loadClass("java.awt.Button");
		assertTrue(! cl.loadClassFromFileCalled);
	}

	/** Important to filter ComponentConduit because it is
	 * instantiated for testing. **/
	public void testComponentConduitFiltered() throws Exception {
		CheckedDynamicClassLoader cl = new CheckedDynamicClassLoader();
		cl.loadClass("com.mtp.pounder.ComponentConduit");
		assertTrue(! cl.loadClassFromFileCalled);
	}

}






