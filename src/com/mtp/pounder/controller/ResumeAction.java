package com.mtp.pounder.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mtp.pounder.PounderModel;
import com.mtp.pounder.ResourceLoader;

import com.mtp.i18n.Strings;

/**

Action for unpausing the current script.

@author Matthew Pekar

**/
public class ResumeAction extends AbstractAction {

	protected PounderModel model;

	public ResumeAction(PounderModel pm) {
		super(Strings.getString("Resume"));
		this.model = pm;

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/Refresh16.gif"));

	}

	public void actionPerformed(ActionEvent e) {
		model.setPaused(false);
	}

}
