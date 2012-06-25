package com.mtp.pounder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import java.lang.reflect.Method;

import com.mtp.i18n.Strings;

/**

Used to manufacture ComponentIdentifier's.  Can do so intelligently if
desired, producing the best ComponentIdentifier for a situation.

@author Matthew Pekar

**/
public class ComponentIdentifierFactory {

	protected PounderPrefs prefs;

	public ComponentIdentifierFactory() {
		this(new PounderPrefs());
	}

	public ComponentIdentifierFactory(PounderPrefs prefs) {
		this.prefs = prefs;
	}

	/** Build a ComponentIdentifier that best represents the given
	 * Component. **/ 
	public ComponentIdentifier build(Component c) {
		if(c instanceof Window) {
			return new WindowIdentifier();
		}

		String name = c.getName();
		if(name != null) {
			return buildNameIdentifier(c);
		}

		return buildKeyIdentifier(c);
	}

	public KeyIdentifier buildKeyIdentifier(Component c) {
		return new KeyIdentifier(c);
	}

	public ComponentIdentifier buildNameIdentifier(Component c) {
		final Component parent= c.getParent();
		if (parent instanceof Container) {
			final String toFind= c.getName();
			final Container newParent= (Container)parent;
			for (final Component child: newParent.getComponents()) {
				final String name= child.getName();
				if (name != null && toFind != null) {
					if (name.compareTo(toFind) == 0 && !c.equals(child)) {
						return new KeyIdentifier(c);
					}
				}
			}
		}

		return new NameIdentifier(c.getName());
	}

	public ComponentIdentifier buildFromString(String s) {
		try {
			int index = s.indexOf(':');
			String componentName = s.substring(0, index);
			Class c = Class.forName(componentName);
			Class[] params = {String.class};
			Method inst = c.getMethod("instantiate", params);

			Object[] args = {s};
			return (ComponentIdentifier)inst.invoke(null, args);
		}
		catch(Exception exc) {
			IllegalArgumentException iae = new IllegalArgumentException(Strings.getString("InvalidClassName:") + s);
			iae.initCause(exc);
			throw iae;
		}
	}

}
