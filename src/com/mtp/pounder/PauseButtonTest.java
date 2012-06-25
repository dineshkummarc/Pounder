package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.AbstractAction;

import com.mtp.pounder.controller.PauseAction;
import com.mtp.pounder.controller.ResumeAction;

public class PauseButtonTest extends TestCase {

	public PauseButtonTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PauseButtonTest.class);
	}

  public void testConstructor() throws Exception {
    PounderModel pm = new PounderModel();
    PauseAction pa = new PauseAction(pm);
    ResumeAction upa = new ResumeAction(pm);
    pa.setEnabled(true);
    upa.setEnabled(false);

    PauseButton pb = new PauseButton(pa, upa);
    assertSame(pa, pb.pauseAction);
    assertSame(upa, pb.resumeAction);
    assertEquals(pa.getValue(AbstractAction.NAME), pb.getText());
    assertTrue(pb.isEnabled());
  }

  public void testResumeActionBecomesEanbled() throws Exception {
    PounderModel pm = new PounderModel();
    PauseAction pa = new PauseAction(pm);
    ResumeAction upa = new ResumeAction(pm);
    pa.setEnabled(true);
    upa.setEnabled(false);

    PauseButton pb = new PauseButton(pa, upa);

    upa.setEnabled(true);
    pa.setEnabled(false);

    assertEquals(upa.getValue(AbstractAction.NAME), pb.getText());
    assertTrue(pb.isEnabled());
  }

}






