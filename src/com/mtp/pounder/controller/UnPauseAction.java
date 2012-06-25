package com.mtp.pounder.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mtp.pounder.PounderModel;

/**

Action for unpausing the current script.

@author Matthew Pekar

**/
public class UnPauseAction extends AbstractAction {

	protected PounderModel model;

	public UnPauseAction(PounderModel pm) {
		super("UnPause");
		this.model = pm;
	}

	public void actionPerformed(ActionEvent e) {
		model.setPaused(false);
	}

}
