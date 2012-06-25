package com.mtp.gui.widget;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Action;

import java.awt.Window;
import java.awt.Frame;
import java.awt.Dialog;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import com.mtp.model.StatusModel;
import com.mtp.model.StatusModelListener;

import com.mtp.gui.WindowUtilities;

import com.mtp.i18n.Strings;

/**

Panel for displaying the status of an application.  Better than a
label because it can allow exception stack traces to be viewd fully
with a double click. 

@author Matthew Pekar

**/
public class StatusPanel extends JLabel implements StatusModelListener {

	protected StatusModel model;
	protected Action action;

	public StatusPanel() {
		this(new StatusModel());
	}

	public StatusPanel(StatusModel sm) {
		setModel(sm);
		addDoubleClickListener();
	}

	public void setAction(Action a) {
		this.action = a;
	}

	protected void addStackTrace(Throwable t, StringBuffer buf) {
		if(t == null)
			return;

		StackTraceElement[] st = t.getStackTrace();
		for(int i=0;i < st.length;i++) {
			buf.append(st[i].toString());
			buf.append('\n');
		}
		
		if(t.getCause() != null) {
			buf.append(Strings.getString("CausedBy:") + t.getCause() + "\n");
			addStackTrace(t.getCause(), buf);
		}
	}

	protected void displayStackTraceRequested() {
		if(model == null)
			return;
		if(model.getException() == null)
			return;
		if(action != null) {
			if(! action.isEnabled())
				return;
		}

		//ridiculous...
		Window parent = SwingUtilities.windowForComponent(this);
		JDialog dialog;
		if(parent instanceof Dialog)
			dialog = new JDialog((Dialog)parent, Strings.getString("StackTrace"), true);
		else
			dialog = new JDialog((Frame)parent, Strings.getString("StackTrace"), true);
		dialog.setTitle(model.getException().getClass().getName() + ": " + model.getException().getMessage());

		//not so smooth either...
		StringBuffer text = new StringBuffer();
		addStackTrace(model.getException(), text);

		JTextArea textArea = new JTextArea(text.toString());
		dialog.getContentPane().add(new JScrollPane(textArea));
		dialog.setSize(500, 700);
		WindowUtilities.center(dialog);
		dialog.setVisible(true);
	}

	protected void addDoubleClickListener() {
		addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2)
						displayStackTraceRequested();
				}
			});
	}

	public void statusModelChanged(StatusModel sm) {
		setText(sm.getStatus());
		setForeground(sm.getColor());
	}

	public void setModel(StatusModel sm) {
		if(this.model != null)
			this.model.getListeners().remove(this);

		this.model = sm;
		if(this.model != null) {
			this.model.getListeners().add(this);
			statusModelChanged(this.model);
		}
	}

}
