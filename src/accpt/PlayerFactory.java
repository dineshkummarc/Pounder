package accpt;

import com.mtp.pounder.Player;

/**

Use this to manufacture the a specialized Player.

@author Matthew Pekar

**/
public class PlayerFactory {

		public static Player buildDefault(String script) throws Exception {
				return new Player(script);
		}

		public static Player buildSpeedy(String script) throws Exception {
				Player p = new Player(script);
				p.setItemDelayEnabled(false);
				p.setPlaybackAttempts(100);
				p.setFailedPlaybackDelay(50);
				return p;
		}

		public static Player buildErrorResistant(String script) throws Exception {
				Player p = new Player(script);
				p.setItemDelayEnabled(true);
				p.setPlaybackAttempts(100);
				p.setFailedPlaybackDelay(100);
				return p;
		}

}
