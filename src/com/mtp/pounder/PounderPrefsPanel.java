package com.mtp.pounder;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.AbstractAction;

import javax.swing.border.EtchedBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.ActionEvent;

import com.mtp.gui.widget.KeyEventSelector;
import com.mtp.gui.widget.LookAndFeelComboBox;
import com.mtp.gui.LayoutPrefs;

import com.mtp.i18n.Strings;

/**

Panel for editing a PounderPrefs.

@author Matthew Pekar

**/
public class PounderPrefsPanel extends JPanel implements LayoutPrefs {

	protected PounderPrefs prefs;
	protected AbstractAction setEndEventAction;
	protected KeyEventSelector keyEventSelector;

	public PounderPrefsPanel() {
		this(new PounderPrefs());
	}

	public PounderPrefsPanel(PounderPrefs prefs) {
		if(prefs == null)
			throw new IllegalArgumentException("Prefs cannot be null.");
		setName("PounderPrefsPanel");
		this.prefs = prefs;
		initActions();
		initLayout();
	}

	public void setPrefs(PounderPrefs prefs) {
		this.prefs = prefs;
		keyEventSelector.setKeyEvent(prefs.getEventDetector().getEndEvent());
	}

	protected void initActions() {
		setEndEventAction = new AbstractAction(Strings.getString("Done")) {
				public void actionPerformed(ActionEvent e) {
					KeyEventSelector src = (KeyEventSelector)e.getSource();
					prefs.getEventDetector().setEndEvent(src.getKeyEvent());
				}
			};
	}

	protected void initLayout() {
		setLayout(new GridBagLayout());

		int cs = COMPONENT_SPACING;
		int ls = LABEL_SPACING;

		add(new JLabel(Strings.getString("StopShortcut:")), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0,0,0, ls), 0, 0));

		keyEventSelector = new KeyEventSelector(prefs.getEventDetector().getEndEvent());
		keyEventSelector.addActionListener(setEndEventAction);
		keyEventSelector.setBorder(new EtchedBorder());
		add(keyEventSelector, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Strings.getString("UITheme:")), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(cs,0,0, ls), 0, 0));
		add(new LookAndFeelComboBox(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(cs, 0, 0, 0), 0, 0));
	}

}
