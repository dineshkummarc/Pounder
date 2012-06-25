package com.mtp.pounder;

import com.mtp.pounder.Player;

public class JFramePlayerTest {

	  public static Player buildDefault(String script) throws Exception {
	    return new Player(script);
	  }

	  public static void main(String[] args) {
		try {
			Player player = buildDefault("/Users/zaparand/Desktop/pounder.txt");
			player.play();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
