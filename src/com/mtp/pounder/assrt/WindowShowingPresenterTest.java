package com.mtp.pounder.assrt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mtp.i18n.Strings;

public class WindowShowingPresenterTest extends TestCase {

	public WindowShowingPresenterTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(WindowShowingPresenterTest.class);
	}

	public void testConstructor() throws Exception {
		WindowShowingPresenter wsp = new WindowShowingPresenter();
		assertNotNull(wsp.getWindowIdentifierPresenter());
	}

	public void testGetAssertTitle() throws Exception {
		WindowShowingPresenter wsp = new WindowShowingPresenter();
		assertEquals(Strings.getString("AssertWindowShowing"), wsp.getAssertTitle());
	}

}






