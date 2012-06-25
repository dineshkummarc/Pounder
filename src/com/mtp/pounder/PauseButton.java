package com.mtp.pounder;

import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**

Button whose actions changes based on whether the script is paused or
not.

@author Matthwe Pekar

**/
public class PauseButton extends JButton implements PropertyChangeListener {

  protected AbstractAction pauseAction, resumeAction;

  public PauseButton(AbstractAction pauseAction, AbstractAction resumeAction) {
    this.pauseAction = pauseAction;
    this.resumeAction = resumeAction;

    this.pauseAction.addPropertyChangeListener(this);
    this.resumeAction.addPropertyChangeListener(this);

    updateFromEnabledAction();
  }

  protected void updateFromEnabledAction() {
    if(pauseAction.isEnabled() && pauseAction != getAction())
      setAction(pauseAction);
    else if(resumeAction.isEnabled() && resumeAction != getAction())
      setAction(resumeAction);
    else if(getAction() == null)
      setAction(pauseAction);
  }

  public void propertyChange(PropertyChangeEvent e) {
    if(e.getPropertyName().equals("enabled"))
      updateFromEnabledAction();
  }

}
