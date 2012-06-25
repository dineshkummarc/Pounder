package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import java.io.File;

import com.mtp.pounder.*;

import com.mtp.gui.WindowUtilities;

import com.mtp.i18n.Strings;

/**

Action for editing the preferences.

@author Matthew Pekar

**/
public class EditPreferencesAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;
	protected PounderPrefsDialog dialog;

	public EditPreferencesAction(PounderController c, PounderModel pm) {
		super(Strings.getString("EditPreferences"));
		this.controller = c;
		this.model = pm;
				
		putValue(AbstractAction.SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/Preferences16.gif"));
		putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_SEMICOLON, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(AbstractAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
	}

	public void actionPerformed(ActionEvent e) {
		if(dialog == null) {
			dialog = new PounderPrefsDialog(controller.getFrame(), model.getPreferences());
			dialog.setModal(true);
			WindowUtilities.center(dialog);
		}

		dialog.setVisible(true);
	}

}
