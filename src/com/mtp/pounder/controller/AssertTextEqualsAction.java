package com.mtp.pounder.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import com.mtp.pounder.*;

import java.awt.Frame;
import java.awt.Component;

import com.mtp.pounder.assrt.AssertDialog;
import com.mtp.pounder.assrt.TextEqualsPresenter;
import com.mtp.pounder.assrt.TextEqualsPanel;

import com.mtp.i18n.Strings;

/**

Action for closing the main frame.

@author Matthew Pekar

**/
public class AssertTextEqualsAction extends AbstractAction {

	protected PounderModel model;
	protected	TextEqualsPresenter presenter;
	protected TextEqualsPanel panel;
	protected Frame frame;

	public AssertTextEqualsAction(PounderModel pm, Frame pounderFrame) {
		super(Strings.getString("TextEquals"));

		this.frame = pounderFrame;
		this.presenter = new TextEqualsPresenter(pm.getRecord());
		this.panel = new TextEqualsPanel(presenter);
	}

	public void actionPerformed(ActionEvent e) {
		AssertDialog dialog = new AssertDialog(panel, presenter, frame, true);
		dialog.setVisible(true);
	}

}
