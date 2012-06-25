package com.mtp.pounder.assrt;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.mtp.gui.LayoutPrefs;

import com.mtp.gui.widget.IntegerTextField;

/**

Panel for editing a WindowShowingPresenter.

@author Matthew Pekar

**/
public class WindowIdentifierPanel extends JPanel implements LayoutPrefs {

	protected WindowIdentifierPresenter presenter;
		
	public WindowIdentifierPanel() {
		this(new WindowIdentifierPresenter());
	}

	public WindowIdentifierPanel(WindowIdentifierPresenter p) {
		setName("WindowIdentifierPanel");
		setPresenter(p);
	}

	public void setPresenter(WindowIdentifierPresenter p) {
		this.presenter = p;
		initLayout();
	}

	protected void initLayout() {
		removeAll();
		setLayout(new GridBagLayout());
				
		ButtonGroup bg = new ButtonGroup();

		JRadioButton useTitleButton = new JRadioButton(presenter.getUseTitleAction());
		useTitleButton.setName("useTitleButton");
		useTitleButton.setSelected(presenter.getSetTitleAction().isEnabled());
		bg.add(useTitleButton);
		add(useTitleButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(COMPONENT_SPACING, 0, 0, 0), 0, 0));

		JRadioButton useIdButton = new JRadioButton(presenter.getUseIdAction());
		useIdButton.setName("useIdButton");
		useIdButton.setSelected(presenter.getSetIdAction().isEnabled());
		bg.add(useIdButton);
		add(useIdButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		JTextField titleField = new JTextField();
		titleField.setName("titleField");
		titleField.setAction(presenter.getSetTitleAction());
		titleField.setDocument(presenter.getTitleModel());
		titleField.setColumns(20);
		add(titleField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(COMPONENT_SPACING, LABEL_SPACING, 0, 0), 0, 0));

		IntegerTextField idField = new IntegerTextField(presenter.getIdModel());
		idField.setName("idField");
		idField.setAction(presenter.getSetIdAction());
		idField.setColumns(2);
		add(idField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, LABEL_SPACING, 0, 0), 0, 0));
	}
}
