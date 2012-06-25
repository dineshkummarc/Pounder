package com.mtp.pounder;

import java.applet.Applet;
import java.applet.AppletStub;

/**

Just plays the applet.

@author Matthew Pekar

**/
public class AppletThread extends Thread {

  protected Applet applet;
  protected AppletStub stub;

  public AppletThread(Applet applet, AppletStub stub) {
    this.applet = applet;
  }
  
  public void run() {
    if(stub != null)
      applet.setStub(stub);

    applet.init();
    applet.start();
  }

}
