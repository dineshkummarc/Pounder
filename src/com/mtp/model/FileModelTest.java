package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class FileModelTest extends TestCase {

	public static Test suite() {
		return new TestSuite(FileModelTest.class);
	}

	public FileModelTest(String name) {
		super(name);
	}

	public void testValue() throws Exception {
		File file = File.createTempFile("foo", "tmp");
		FileModel fileModel = new FileModel();
		assertTrue(fileModel.getFile() == null);
		fileModel.setFile(file);
		assertTrue(fileModel.getFile() == file);
		fileModel.setFile(null);
		assertTrue(fileModel.getFile() == null);
		fileModel = new FileModel(file);
		assertTrue(fileModel.getFile() == file);
	}

	public void testEquals() {
		FileModel m1 = new FileModel();
		FileModel m2 = new FileModel();
		assertEquals(m1, m2);

		m1.setFile(new File("foo/bar"));
		m2.setFile(new File("foo/bar"));
		assertEquals(m1, m2);
	}    

	public void testFilesDifferentEquals() {
		FileModel m1 = new FileModel();
		FileModel m2 = new FileModel();
		m1.setFile(new File("foo/bar"));

		assertTrue(! m1.equals(m2));
	}

}



