package com.mtp.model;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DocumentStringModelTest extends StringModelTest {

	public DocumentStringModelTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DocumentStringModelTest.class);
	}

	public StringModel buildInstance() {
		return new DocumentStringModel();
	}

}






