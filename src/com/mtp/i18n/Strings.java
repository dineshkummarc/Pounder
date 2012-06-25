package com.mtp.i18n;

import java.util.ResourceBundle;
import java.util.Locale;

/**

Simple class for obtaining internationalization String's.

@author Matthew Pekar

**/
public class Strings {

	/** The ResourceBundle where the strings be. **/
	protected static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("StringsBundle", Locale.getDefault());
		if(bundle == null)
			bundle = ResourceBundle.getBundle("StringsBundle", new Locale("en", "US"));
	}

	public static String getString(String key) {
		return bundle.getString(key);
	}

}
