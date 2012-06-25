package com.mtp.pounder.assrt;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import com.mtp.pounder.RecordingItem;

import com.mtp.i18n.Strings;

/**

Action for the dialog that is used for the "done" button.

@author Matthew Pekar

**/
public class AssertDialogDoneAction extends AbstractAction {
		
	protected AssertPresenter presenter;
	protected AssertDialog dialog;

	public AssertDialogDoneAction(AssertPresenter presenter, AssertDialog dialog) {
		super(Strings.getString("Done"));
		this.presenter = presenter;
		this.dialog = dialog;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			RecordingItem ri = presenter.getAssertionItem();
			presenter.getRecord().addAssertion(ri);
			dialog.setVisible(false);
			dialog.dispose();
		}
		catch(Exception exc) {
			presenter.getStatusModel().handleException(exc);
		}
	}
		
}
