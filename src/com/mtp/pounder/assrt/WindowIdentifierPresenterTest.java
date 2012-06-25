package com.mtp.pounder.assrt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WindowIdentifierPresenterTest extends TestCase {

	public WindowIdentifierPresenterTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(WindowIdentifierPresenterTest.class);
	}

	public void testConstructor() throws Exception {
		WindowIdentifierPresenter wip = new WindowIdentifierPresenter();
		assertTrue(wip.titleModel != null);
		assertTrue(wip.idModel != null);
		assertTrue(wip.setTitleAction != null);
		assertTrue(wip.setIdAction != null);
		assertTrue(wip.setTitleAction.isEnabled());
		assertTrue(! wip.setIdAction.isEnabled());
	}

	public void testUseIdAction() throws Exception {
		WindowIdentifierPresenter wip = new WindowIdentifierPresenter();

		wip.useIdAction.actionPerformed(null);

		assertTrue(wip.setIdAction.isEnabled());
		assertTrue(! wip.setTitleAction.isEnabled());
	}

	public void testUseTitleAction() throws Exception {
		WindowIdentifierPresenter wip = new WindowIdentifierPresenter();

		wip.useIdAction.actionPerformed(null);
		wip.useTitleAction.actionPerformed(null);

		assertTrue(wip.setTitleAction.isEnabled());
		assertTrue(! wip.setIdAction.isEnabled());
	}

}






