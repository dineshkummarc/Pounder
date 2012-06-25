package com.mtp.pounder.assrt;

import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.RecordingRecord;

import com.mtp.model.IntegerModel;
import com.mtp.model.DefaultIntegerModel;
import com.mtp.model.DocumentStringModel;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import com.mtp.i18n.Strings;

/**

Presents a model for editing a WindowIdentifier.

@author Matthew Pekar

**/
public class WindowIdentifierPresenter {

	protected DocumentStringModel titleModel;
	protected IntegerModel idModel;
	protected AbstractAction setTitleAction, setIdAction, useTitleAction, useIdAction;

	public WindowIdentifierPresenter() {
		this.titleModel = new DocumentStringModel();
		this.idModel = new DefaultIntegerModel();
		initActions();
	}

	protected void initActions() {
		setTitleAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};
		setTitleAction.setEnabled(true);

		setIdAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				}
			};
		setIdAction.setEnabled(false);

		useTitleAction = new AbstractAction(Strings.getString("WindowTitle:")) {
				public void actionPerformed(ActionEvent e) {
					setTitleAction.setEnabled(true);
					setIdAction.setEnabled(false);
				}
			};

		useIdAction = new AbstractAction(Strings.getString("WindowID:")) {
				public void actionPerformed(ActionEvent e) {
					setIdAction.setEnabled(true);
					setTitleAction.setEnabled(false);
				}
			};
	}
		
	public AbstractAction getUseTitleAction() {
		return useTitleAction;
	}

	public AbstractAction getUseIdAction() {
		return useIdAction;
	}

	public DocumentStringModel getTitleModel() {
		return titleModel;
	}

	public IntegerModel getIdModel() {
		return idModel;
	}

	public AbstractAction getSetIdAction() {
		return setIdAction;
	}

	public AbstractAction getSetTitleAction() {
		return setTitleAction;
	}

}
