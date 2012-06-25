package com.mtp.pounder;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Frame;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.mtp.gui.LayoutPrefs;

import com.mtp.i18n.Strings;

/**

Dialog for displaying a file's comment.

@author Matthew Pekar

**/
public class CommentDialog extends JDialog implements LayoutPrefs {

	public static Dimension DEFAULT_SIZE = new Dimension(400, 400);

	protected PounderModel model;

	public CommentDialog() {
		this(new PounderModel());
	}

	public CommentDialog(PounderModel model, Frame owner, boolean modal) {
		super(owner, modal);
		init(model);
	}
		
	public CommentDialog(PounderModel model, Dialog owner, boolean modal) {
		super(owner, modal);
		init(model);
	}

	public CommentDialog(PounderModel model) {
		init(model);
	}

	protected void init(PounderModel model) {
		this.model = model;
		setTitle(Strings.getString("ScriptComment"));
		setName("EditPounderFileCommentDialog");
		initLayout();
	}

	protected void initLayout() {
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());

		int bs = BORDER_SPACING;
		int cs = COMPONENT_SPACING;
				
		JTextArea ta = new JTextArea(model.getComment());
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		c.add(new JScrollPane(ta), new GridBagConstraints(0,0,2,1,1.0,1.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,new Insets(bs,bs,cs,bs),0,0));
				
		JButton doneButton = new JButton(Strings.getString("Done"));
		doneButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		c.add(new JPanel(), new GridBagConstraints(0,1,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,bs,bs,0),0,0));
		c.add(doneButton, new GridBagConstraints(1,1,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(0,cs,bs,bs),0,0));

		setSize(DEFAULT_SIZE);
	}
		
}
