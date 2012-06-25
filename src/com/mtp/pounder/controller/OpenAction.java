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

import com.mtp.i18n.Strings;

/**

Action for opening a script.

@author Matthew Pekar

**/
public class OpenAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public OpenAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Open"));
		this.controller = c;
		this.model = pm;

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/Open16.gif"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
	}

	/** Return true if it's OK to erase the script. **/
	protected boolean eraseScriptOK() {
		if(model.isSaveNeeded()) {
			String changeScriptConfirmation = Strings.getString("ChangeScriptConfirmation");
			String changeScriptConfirmationDialogTitle = Strings.getString("ChangeScriptConfirmationDialogTitle");
			int result = JOptionPane.showConfirmDialog(controller.getFrame(), changeScriptConfirmation, changeScriptConfirmationDialogTitle, JOptionPane.YES_NO_OPTION);
			return result == JOptionPane.YES_OPTION;
		}
				
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		if(! eraseScriptOK())
			return;

		File f = showOpenDialog();
		if(f != null) {
			try {
				String beginOpeningString = Strings.getString("BeginOpening");
				String endOpeningString = Strings.getString("EndOpening");
				model.getStatusModel().setStatus(beginOpeningString + f.getName());
				new PounderReader().readToModel(model, f);
				model.getStatusModel().setStatus(endOpeningString + f.getName());
			}
			catch(Exception exc) {
				String errorOpeningString = Strings.getString("ErrorOpening");
				model.getStatusModel().handleException(errorOpeningString, exc);
			}
		}

	}

	/** Show open dialog and return resulting file.  null if none selected. **/
	protected File showOpenDialog() {
		JFileChooser fileChooser = controller.getFileChooser();
		int res = fileChooser.showOpenDialog(controller.getFrame());
		switch(res) {
		case JFileChooser.APPROVE_OPTION:
			return fileChooser.getSelectedFile();
		case JFileChooser.CANCEL_OPTION:
			return null;
		}

		return null;
	}

}
