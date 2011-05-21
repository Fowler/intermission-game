package de.fuhlsfield;


import java.util.HashMap;
import java.util.Map;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.config.FiveBallsGameConfig;
import de.fuhlsfield.game.config.SixBallsGameConfig;

/**
 * Starter for intermission game.
 * 
 * @author juergen
 */
public class IntermissionGameStarter {

	private static final Game FIVE_BALLS = new Game(new FiveBallsGameConfig(), 10, 10, new Player("Jürgen"),
			new Player("Marcus"));
	private static final Game SIX_BALLS = new Game(new SixBallsGameConfig(), 15, 10, new Player("Jürgen"), new Player(
			"Marcus"));
	private static final Map<String, Game> GAMES = new HashMap<String, Game>();

	static {
		GAMES.put("5", FIVE_BALLS);
		GAMES.put("6", SIX_BALLS);
	}

	public static void main(String[] args) {
		new IntermissionGameGui().create(GAMES.get(args[0]));
	}

}
