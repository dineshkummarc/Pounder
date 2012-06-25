package com.mtp.pounder.assrt;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Frame;

import com.mtp.gui.LayoutPrefs;
import com.mtp.gui.WindowUtilities;

import com.mtp.gui.widget.StatusPanel;

/**

Dialog for adding an assertion to a RecordingRecord at a certain
index. 

@author Matthew Pekar

**/
public class AssertDialog extends JDialog implements LayoutPrefs {

	protected AssertPresenter presenter;
		
	public AssertDialog(JPanel assertPanel, AssertPresenter ap, Frame owner, boolean modal) {
		super(owner, modal);

		this.presenter = ap;

		initLayout(assertPanel);
		WindowUtilities.center(this, owner.getBounds());
	}

	protected void initLayout(JPanel assertPanel) {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridBagLayout());

		getContentPane().add(assertPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(BORDER_SPACING, BORDER_SPACING, 0, BORDER_SPACING), 0, 0));

		getContentPane().add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(COMPONENT_SPACING, BORDER_SPACING, 0, 0), 0, 0));
		AssertDialogDoneAction doneAction = new AssertDialogDoneAction(presenter, this);
		getContentPane().add(new JButton(doneAction), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(COMPONENT_SPACING, COMPONENT_SPACING, 0, BORDER_SPACING), 0, 0));

		getContentPane().add(new StatusPanel(presenter.getStatusModel()), new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(COMPONENT_SPACING, BORDER_SPACING, BORDER_SPACING, BORDER_SPACING), 0, 0));

		setTitle(presenter.getAssertTitle());

		pack();
	}

}
