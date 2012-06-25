package com.mtp.pounder.assrt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mtp.i18n.Strings;

public class TextEqualsPresenterTest extends TestCase {

	public TextEqualsPresenterTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TextEqualsPresenterTest.class);
	}

	public void testConstructor() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		assertNotNull(tep.getWindowIdentifierPresenter());
		assertNotNull(tep.componentNameModel);
		assertNotNull(tep.desiredValueModel);
	}

	public void testGetAssertTitle() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		assertEquals(Strings.getString("AssertTextEquals"), tep.getAssertTitle());
	}

	public void testGetAssertionItemByWindowTitle() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		tep.getWindowIdentifierPresenter().getSetTitleAction().setEnabled(true);
		tep.getWindowIdentifierPresenter().getSetIdAction().setEnabled(false);
		tep.getWindowIdentifierPresenter().getTitleModel().setString("Indiana");
		tep.getComponentNameModel().setString("Gary");
		tep.getDesiredValueModel().setString("Stinkpit");

		assertEquals(new TextEqualsItem("Indiana", "Gary", "Stinkpit"), tep.getAssertionItem());
	}

	public void testGetAssertionItemByWindowID() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		tep.getWindowIdentifierPresenter().getSetTitleAction().setEnabled(false);
		tep.getWindowIdentifierPresenter().getSetIdAction().setEnabled(true);
		tep.getWindowIdentifierPresenter().getIdModel().setValue(32);
		tep.getComponentNameModel().setString("Gary");
		tep.getDesiredValueModel().setString("Stinkpit");

		assertEquals(new TextEqualsItem(32, "Gary", "Stinkpit"), tep.getAssertionItem());
	}

	public void testGetAssertionItemWithNullComponentNameThrowsException() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		tep.getWindowIdentifierPresenter().getSetTitleAction().setEnabled(true);
		tep.getWindowIdentifierPresenter().getSetIdAction().setEnabled(false);
		tep.getWindowIdentifierPresenter().getTitleModel().setString("Indiana");
		tep.getComponentNameModel().setString(null);
		tep.getDesiredValueModel().setString("Stinkpit");

		boolean thrown = false;
		try {
			tep.getAssertionItem();
		}
		catch(IllegalStateException exc) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testGetAssertionItemWithEmptyComponentNameThrowsException() throws Exception {
		TextEqualsPresenter tep = new TextEqualsPresenter();
		tep.getWindowIdentifierPresenter().getSetTitleAction().setEnabled(true);
		tep.getWindowIdentifierPresenter().getSetIdAction().setEnabled(false);
		tep.getWindowIdentifierPresenter().getTitleModel().setString("Indiana");
		tep.getComponentNameModel().setString("");
		tep.getDesiredValueModel().setString("Stinkpit");

		boolean thrown = false;
		try {
			tep.getAssertionItem();
		}
		catch(IllegalStateException exc) {
			thrown = true;
		}
		assertTrue(thrown);
	}

}
