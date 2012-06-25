package com.mtp.pounder;

import javax.swing.JDialog;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BorderFactory;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.ActionEvent;

import com.mtp.gui.LayoutPrefs;

import com.mtp.i18n.Strings;

/**

Dialog for editing a PounderPrefs.

@author Matthew Pekar

**/
public class PounderPrefsDialog extends JDialog implements LayoutPrefs {

	protected AbstractAction doneAction;
	protected PounderPrefs prefs;
	protected PounderPrefsPanel prefsPanel;

	public PounderPrefsDialog(JFrame parent, PounderPrefs prefs) {
		super(parent, true);
		this.prefs = prefs;
		this.prefsPanel = null;

		setTitle(Strings.getString("PounderPreferences"));
		setName("PounderPrefsDialog");

		initActions();
		initLayout();

		pack();
		setResizable(false);
	}

	public void setVisible(boolean whether) {
		if(whether)
			retrievePrefsData();
		super.setVisible(whether);
	}

	protected void retrievePrefsData() {
		prefsPanel.setPrefs(prefs);
	}

	protected void initActions() {
		doneAction = new AbstractAction(Strings.getString("Done")) {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			};
	}

	protected void initLayout() {
		int cs = COMPONENT_SPACING;
		int bs = BORDER_SPACING;
		int ibs = INNER_BORDER_SPACING;

		getContentPane().setLayout(new GridBagLayout());
		prefsPanel = new PounderPrefsPanel(prefs);
		prefsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("General")), BorderFactory.createEmptyBorder(ibs,ibs,ibs,ibs)));
		getContentPane().add(prefsPanel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(bs, bs, cs, bs), 0, 0));

		VerbatimRecordingOptionsPanel vrop = new VerbatimRecordingOptionsPanel(prefs.getVerbatimRecordingOptions());
		vrop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("Recording")), BorderFactory.createEmptyBorder(ibs,ibs,ibs,ibs)));
		getContentPane().add(vrop, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(cs, bs, cs, bs), 0, 0));


		//filler to left of button
		getContentPane().add(new JPanel(), new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(cs, 0, 0, 0), 0, 0));

		JButton doneButton = new JButton(doneAction);
		getContentPane().add(doneButton, new GridBagConstraints(1, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(cs, 0, bs, bs), 0, 0));
		doneButton.requestFocus();
	}
}
