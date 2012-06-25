package com.mtp.pounder.assrt;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.mtp.gui.LayoutPrefs;

import com.mtp.i18n.Strings;

/**

Panel for editing a TextEqualsPresenter.

@author Matthew Pekar

**/
public class TextEqualsPanel extends JPanel implements LayoutPrefs {

	protected TextEqualsPresenter presenter;
		
	public TextEqualsPanel() {
		this(new TextEqualsPresenter());
	}

	public TextEqualsPanel(TextEqualsPresenter p) {
		setName("TextEqualsPanel");
		setPresenter(p);
	}

	public void setPresenter(TextEqualsPresenter p) {
		this.presenter = p;
		initLayout();
	}

	protected JPanel buildTextPanel() {
		int IBS = INNER_BORDER_SPACING;
		int LS = LABEL_SPACING;

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("TextComponent")), BorderFactory.createEmptyBorder(IBS,IBS,IBS,IBS)));

		JLabel nameLabel = new JLabel(Strings.getString("Name:"));
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		textPanel.add(nameLabel, new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));

		JTextField nameTextField = new JTextField();
		nameTextField.setColumns(20);
		nameTextField.setDocument(presenter.getComponentNameModel());
		textPanel.add(nameTextField, new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,LS,0,0),0,0));

		JLabel desiredValueLabel = new JLabel(Strings.getString("DesiredValue:"));
		desiredValueLabel.setHorizontalAlignment(JLabel.LEFT);
		textPanel.add(desiredValueLabel, new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));

		JTextField desiredValueTextField = new JTextField();
		desiredValueTextField.setColumns(20);
		desiredValueTextField.setDocument(presenter.getDesiredValueModel());
		textPanel.add(desiredValueTextField, new GridBagConstraints(1,1,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,LS,0,0),0,0));

		return textPanel;
	}

	protected void initLayout() {
		removeAll();
		setLayout(new GridBagLayout());

		int IBS = INNER_BORDER_SPACING;
		int CS = COMPONENT_SPACING;
		
		WindowIdentifierPanel wip = new WindowIdentifierPanel(presenter.getWindowIdentifierPresenter());
		wip.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("Window")), BorderFactory.createEmptyBorder(IBS,IBS,IBS,IBS)));
		add(wip, new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));

		add(buildTextPanel(), new GridBagConstraints(0,1,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(CS,0,0,0),0,0));
	}
}
