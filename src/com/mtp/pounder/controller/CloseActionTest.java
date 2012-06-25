package com.mtp.pounder.controller;

import com.mtp.pounder.*;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;

public class CloseActionTest extends TestCase implements PounderConstants {

	public CloseActionTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(CloseActionTest.class);
	}

	private boolean closeRequestedCalled = false;
	public void testClose() throws Exception {
		PounderModel pm = new PounderModel();
		PounderController pc = new PounderController(pm);
		PounderFrame pf = new PounderFrame(pc) {
				public void closeRequested() {
					closeRequestedCalled = true;
				}
			};
		CloseAction ca = new CloseAction(pc, pm);

		ca.actionPerformed(null);
		assertTrue(closeRequestedCalled);
	}

}
