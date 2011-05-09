package de.fuhlsfield.game;

import java.util.HashMap;
import java.util.Map;

import de.fuhlsfield.game.score.GameScoreKeeper;

public class Game {

	private int pointLimit = 14;

	private Map<Player, GameScoreKeeper> gameScores = new HashMap<Player, GameScoreKeeper>();

	private Player firstPlayer;
	private Player secondPlayer;

	public Game(Player firsrtPlayer, Player secondPlayer) {
		this.firstPlayer = firsrtPlayer;
		this.secondPlayer = secondPlayer;
		gameScores.put(firsrtPlayer, new GameScoreKeeper());
		gameScores.put(secondPlayer, new GameScoreKeeper());
	}

	public Object[][] check(Ball ball, Player player) {
		gameScores.get(player).add(ball);

		Object[][] result = new Object[10][3];

		GameScoreKeeper first = this.gameScores.get(firstPlayer);
		GameScoreKeeper second = this.gameScores.get(secondPlayer);

		for (int i = 0; i < 10; i++) {

			result[i] = new String[] { String.valueOf(i + 1),
					first.getIndexed(i).getName(),
					second.getIndexed(i).getName() };
		}

		return result;

	}

}
