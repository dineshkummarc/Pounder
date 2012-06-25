package com.mtp.pounder;

public class PounderPlayer {
		
	
	public static void main(String[] args) throws Exception {
	    if (args.length != 1) {
	    	System.out.println("Please, insert the path to recorded script");
	    	System.exit(1);
	    }
	    
		Player player = new Player(args[0]);
	    player.play();
	}

}
