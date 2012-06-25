package com.mtp.pounder;

import java.io.IOException;

/**

Thrown when old or unknown version of a file is detected.

@author Matthew Pekar

**/
public class InvalidVersionException extends IOException {

	public InvalidVersionException(String msg) {
		super(msg);
	}

}
