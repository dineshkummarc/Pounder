package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.File;

import com.mtp.pounder.*;

import com.mtp.gui.WindowUtilities;

import com.mtp.i18n.Strings;

/**

Action for displaying the about dialog.

@author Matthew Pekar

**/
public class AboutAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public AboutAction(PounderController c, PounderModel pm) {
		super(Strings.getString("About"));
		this.controller = c;
		this.model = pm;

		putValue(AbstractAction.SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/About16.gif"));
	}

	public void actionPerformed(ActionEvent e) {
		AboutDialog dialog = new AboutDialog(model, controller.getFrame(), true);
		WindowUtilities.center(dialog);
		dialog.setVisible(true);
	}

}
