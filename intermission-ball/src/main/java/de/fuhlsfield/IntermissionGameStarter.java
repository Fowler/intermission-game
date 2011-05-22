package de.fuhlsfield;

import de.fuhlsfield.game.config.FiveBallsGameConfig;
import de.fuhlsfield.game.config.SixBallsGameConfig;

/**
 * Starter for intermission game.
 * 
 * @author juergen
 */
public class IntermissionGameStarter {

	public static void main(String[] args) {
		if ("5".equals(args[0])) {
			new IntermissionGameGui(new FiveBallsGameConfig(), 10, 10, 2);
		} else if ("6".equals(args[0])) {
			new IntermissionGameGui(new SixBallsGameConfig(), 15, 10, 4);
		}
	}

}