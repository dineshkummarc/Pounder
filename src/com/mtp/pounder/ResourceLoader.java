package com.mtp.pounder;

import java.net.URL;

import java.awt.Toolkit;
import java.awt.Image;

import javax.swing.ImageIcon;

/**

Just loads icons for now.

@author Matthew Pekar

**/
public class ResourceLoader {

	public static ImageIcon getIcon(String key) {
		URL u = ResourceLoader.class.getResource(key);
		if(u == null)
			return null;
		Image img = Toolkit.getDefaultToolkit().getImage(u);
		return new ImageIcon(img);
	}

}
														 
