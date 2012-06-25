package com.mtp.pounder;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.mtp.gui.LayoutPrefs;

import com.mtp.i18n.Strings;

/**

Dialog for editing a VerbatimRecordingOptions.

@author Matthew Pekar

**/
public class VerbatimRecordingOptionsDialog extends JDialog implements LayoutPrefs {

	protected VerbatimRecordingOptions options;
	protected VerbatimRecordingOptionsPanel optionsPanel;

	public VerbatimRecordingOptionsDialog(Frame owner, boolean modal, VerbatimRecordingOptions vro) {
		super(owner, modal);

		if(vro == null)
			throw new IllegalArgumentException("VerbatimRecordingOptions cannot be null");
		this.options = vro;
		setTitle(Strings.getString("RecordingOptions"));
		setName("VerbatimRecordingOptionsDialog");

		initComponents();
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
		optionsPanel.retrieveResults();
	}

	public void dispose() {
		super.dispose();
		optionsPanel.retrieveResults();
	}

	protected void initComponents() {
		getContentPane().setLayout(new GridBagLayout());
		int bs = BORDER_SPACING;
		int cs = COMPONENT_SPACING;

		optionsPanel = new VerbatimRecordingOptionsPanel(options);
		getContentPane().add(optionsPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(bs,bs,cs,bs), 0, 0));

		getContentPane().add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,bs,bs,cs), 0, 0));
		JButton doneButton = new JButton(Strings.getString("Done"));
		doneButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		getContentPane().add(doneButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,bs,bs), 0, 0));

		pack();
	}

}
