package com.mtp.pounder.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import java.io.File;

import com.mtp.pounder.PounderModel;
import com.mtp.pounder.ResourceLoader;

import com.mtp.i18n.Strings;

/**

Action for pausing the current script.

@author Matthew Pekar

**/
public class PauseAction extends AbstractAction {

	protected PounderModel model;

	public PauseAction(PounderModel pm) {
		super(Strings.getString("Pause"));
		this.model = pm;

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/media/Pause16.gif"));
	}

	public void actionPerformed(ActionEvent e) {
		model.setPaused(true);
	}

}
