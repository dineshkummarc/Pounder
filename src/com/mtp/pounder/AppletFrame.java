package com.mtp.pounder;

import java.applet.Applet;
import java.applet.AppletStub;

import javax.swing.JFrame;

import java.awt.Component;

/**

Conduit for playing an Applet.

@author Matthew Pekar

**/
public class AppletFrame extends JFrame {

  public AppletFrame(Applet applet) {
    this(applet, null);
  }

  public AppletFrame(Applet applet, AppletStub stub) {
    getContentPane().add(applet);
    new AppletThread(applet, stub).start();
  }

}
