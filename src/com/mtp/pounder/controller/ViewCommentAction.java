package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.mtp.pounder.*;

import com.mtp.gui.WindowUtilities;

import com.mtp.i18n.Strings;

/**

Action for closing the main frame.

@author Matthew Pekar

**/
public class ViewCommentAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public ViewCommentAction(PounderController c, PounderModel pm) {
		super(Strings.getString("ViewComment"));
		this.controller = c;
		this.model = pm;

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/About16.gif"));
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
	}

	public void actionPerformed(ActionEvent e) {
		CommentDialog dialog = new CommentDialog(model, controller.getFrame(), true);
		WindowUtilities.center(dialog);
		dialog.setVisible(true);
	}

}
