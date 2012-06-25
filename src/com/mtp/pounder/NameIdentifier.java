package com.mtp.pounder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import com.mtp.i18n.Strings;

/**

Finds Component's using their name.

@author Matthew Pekar

**/
public class NameIdentifier implements ComponentIdentifier {
	
	public static NameIdentifier instantiate(String s) {
		int index = s.indexOf(':') + 1;
		String data = s.substring(index, s.length());				
		return new NameIdentifier(data);
	}

	protected String name;

	public NameIdentifier(String name) {
		if(name == null)
			throw new IllegalArgumentException(Strings.getString("NameCannotBeNull"));
 
		this.name = name;
	}
		
	public Component getComponent(Window w) {
		return findComponentNamed(w, name);
	}

	public String asString() {
		return "com.mtp.pounder.NameIdentifier:" + name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object o) {
		NameIdentifier ni = (NameIdentifier)o;
		return ni.name.equals(name);
	}

	protected Component findComponentNamed(Component c, String s) {
		if(s.equals(c.getName()))
			return c;
				
		if(c instanceof Container) {
			Container cont = (Container)c;
			Component[] children = cont.getComponents();
			for(int i=0;i < children.length;i++) {
				Component possibleRet = findComponentNamed(children[i], s);
				if(possibleRet != null)
					return possibleRet;
			}
		}

		return null;
	}

}
