package com.mtp.pounder;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.HashSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**

Dynamically loads classes using classpath environment variables.

@author Matthew Pekar

**/
public class DynamicClassLoader extends ClassLoader {

	protected Collection paths;
	protected HashSet filteredPackages;

	public DynamicClassLoader() {
		init();
	}

	public DynamicClassLoader(ClassLoader parent) {
		super(parent);
		init();
	}

	protected void init() {
		this.paths = new Vector();
		this.filteredPackages = new HashSet();
		getPaths(System.getProperty("java.class.path"));
		addDefaultFilteredPackages();
	}

	protected void addDefaultFilteredPackages() {
		filteredPackages.add("java");
		filteredPackages.add("junit");
		filteredPackages.add("com.mtp.pounder.ComponentConduit");
	}

	protected void getPaths(String path) {
		StringTokenizer t = new StringTokenizer(path, System.getProperty("path.separator"));
		while(t.hasMoreTokens()) {
			paths.add(t.nextToken());
		}
	}

	protected boolean filtered(String name) {
		Iterator i = filteredPackages.iterator();
		while(i.hasNext()) {
			String pkg = (String)i.next();
			if(name.startsWith(pkg))
				return true;
		}

		return false;
	}

	/** Overridden so that findClass() is called before
	 * findLoadedClass() and loadClass(). **/
	protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if(filtered(name))
			return super.loadClass(name, resolve);

		Class c = findClass(name);

		if(c == null) {
			c = findLoadedClass(name);
		}

		if(c == null)
			return super.loadClass(name, resolve);

		if(resolve)
			resolveClass(c);
								
		return c;
	}

	protected Class findClass(String name) throws ClassNotFoundException {
		Iterator i = paths.iterator();
		String filename = name.replace('.', '/') + ".class";
		while(i.hasNext()) {
			String path = (String)i.next();
			File f = new File(path, filename);
			if(f.exists()) {
				Class c = loadClassFromFile(name, f);
				if(c != null) {
					return c;
				}
			}
		}

		return null;
	}

	protected Class loadClassFromFile(String name, File f) {
		byte[] bytes = new byte[4096];
		try {
			FileInputStream in = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			int read = 0;
			while(true) {
				read = in.read(bytes);
				if(read == -1)
					break;
				out.write(bytes, 0, read);
			}
			in.close();
			out.close();

			byte[] result = out.toByteArray();
			return defineClass(name, result, 0, result.length);
		}
		catch(IOException exc) {
			return null;
		}
	}

}
