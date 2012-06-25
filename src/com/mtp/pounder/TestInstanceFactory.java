package com.mtp.pounder;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import java.applet.Applet;

import java.awt.Window;
import java.awt.Component;

import javax.swing.JFrame;

import java.lang.reflect.Modifier;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Manufactures an instance of the test object type for testing.

@author Matthew Pekar

**/
public class TestInstanceFactory {

	protected String setupClass;

	public TestInstanceFactory(Element e) throws Exception {
		//I keep the old tag name.  It's not pretty, but it doesn't hurt
		//anything, and I won't have to break compatibility
		if(! e.getTagName().equals("SetupInitializer"))
			throw new InstantiationException("Cannot build from tag with name: " + e.getTagName());

		if(! e.hasAttribute("setupClass"))
			throw new InstantiationException(Strings.getString("ElementMustContainAttribute:") + "\"setupClass\"");

		this.setupClass = e.getAttribute("setupClass");
	}

	public TestInstanceFactory(String setupClass) {
		if(setupClass == null)
			throw new IllegalArgumentException("'setupClass' cannot be null.");

		this.setupClass = setupClass;
	}

	public String getSetupClass() {
		return setupClass;
	}
	
	/** Instantiates a new test instance and wraps it in a Window at the
	 * location specified by the given PounderPrefs.  Uses the given
	 * WindowWatcher's waitTillWindowPresent method, to make sure the
	 * Window is present before returning. 

	 @return The test instance.
	**/
	public Object showNewTestInstance(PounderPrefs prefs, WindowWatcher ww) throws Exception {
		return showNewTestInstance(getClass().getClassLoader(), prefs, ww);
	}

	/** Instantiates a new test instance using the given ClassLoader and
	 * wraps it in a Window at the location specified by the given
	 * PounderPrefs.  Uses the given WindowWatcher's
	 * waitTillWindowPresent method, to make sure the Window is present
	 * before returning. 

	 @return The test instance.
	**/
	public Object showNewTestInstance(ClassLoader cl, PounderPrefs prefs, WindowWatcher ww) throws Exception {
		Object o = instantiateInstance(cl);
		Window w = wrapInWindow(o, prefs);
		if(w != null)
			ww.waitTillWindowPresent(w);
		return o;
	}

	/** Instantiates a new test instance using the given ClassLoader and
	 * wraps it in a Window at the location specified by the given
	 * PounderPrefs. **/
	public void showNewTestInstance(ClassLoader cl, PounderPrefs prefs) throws Exception {
		Object o = instantiateInstance(cl);
		wrapInWindow(o, prefs);
	}

	/** Instantiate an instance of 'setupClass' using my ClassLoader. **/
	public Object instantiateInstance() throws Exception {
		return instantiateInstance(getClass().getClassLoader());
	}

	/** Instantiate an instance of 'setupClass' using the given
	 * ClassLoader. **/
	protected Object instantiateInstance(ClassLoader cl) throws Exception {
		Class c = Class.forName(setupClass, true, cl);
		if(c == null)
			throw new ClassNotFoundException(Strings.getString("CouldNotFindTestClassData:") + setupClass);

		try {
			return c.newInstance();
		}
		catch(IllegalAccessException exc) {
			throw new IllegalAccessException(Strings.getString("TestClassDoesNotHavePublicConstructor"));
		}
		catch(InstantiationException exc) {
			if(c.isInterface())
				throw new InstantiationException(Strings.getString(Strings.getString("TestClassIsAnInterface")));
			if(c.isArray())
				throw new InstantiationException(Strings.getString("TestClassIsAnArray"));
			if(c.isPrimitive())
				throw new InstantiationException(Strings.getString("TestClassIsAPrimitive"));
			if((c.getModifiers() & Modifier.ABSTRACT) > 0)
				throw new InstantiationException(Strings.getString("TestClassIsAbstract"));

			throw new InstantiationException(Strings.getString("TestClassDoesNotHaveADefaultConstructor"));
		}
	}

	/** Wraps the given object in a Window, and places it at the
	 * location given in PounderPrefs.  

	 @arg object May be a Component, Window, or ComponentConduit.
   @arg prefs Will be used to obtain the window location if necessary.
	**/
	public Window wrapInWindow(Object object, PounderPrefs prefs) throws Exception {
		Window ret = null;

		if(object == null)
			return null;
		if(object instanceof Window) {
			ret = (Window)object;
		}
    else if(object instanceof Applet) {
      ret = new AppletFrame((Applet)object);
    }
		else if(object instanceof Component) {
			Component c = (Component)object;
			JFrame f = new JFrame();
			f.setName("PounderTestFrame");
			f.getContentPane().add(c);
			f.pack();
			f.setLocation(prefs.getDefaultTestWindowLocation().getValue());
			ret = f;
		}
		else if(object instanceof ComponentConduit) {
			Component c = ((ComponentConduit)object).getComponent();
			return wrapInWindow(c, prefs);
		}
		else {
			throw new InstantiationException(Strings.getString("InvalidTestClass"));
		}

		ret.setVisible(true);
		ret.transferFocus();
		return ret;
	}

	public String toString() {
		return "TestInstanceFactory; setupClass: " + setupClass;
	}

	/** XML representation of TestInstanceFactory.  **/
	public Element toXML(Document doc) {
		Element ret = doc.createElement("SetupInitializer"); //cruft, SetupInitializer was renamed to TestInstanceFactory
		ret.setAttribute("setupClass", setupClass);
		return ret;
	}

	/** Returns true if 'setupClass' is equal. **/
	public boolean equals(Object o) {
		if(o == null)
			return false;
		TestInstanceFactory i = (TestInstanceFactory)o;
		return i.setupClass.equals(setupClass);
	}

}
