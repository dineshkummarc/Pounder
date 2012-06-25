package com.mtp.pounder;

import java.awt.Component;

/**

Conduit for accessing a Component.  Performs some extra setup
necessary before a test may be run.

@author Matthew Pekar

**/
public interface ComponentConduit {
		
	/** Create a new instance of the Component. 
      
  @return The Component created, or null. 
  **/
	public Component getComponent() throws Exception;

}
