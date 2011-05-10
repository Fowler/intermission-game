package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.score.GameScoreKeeper;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final Map<Player, GameScoreKeeper> gameScores = new HashMap<Player, GameScoreKeeper>();

	public Game (GameConfig gameConfig, Player... players) {
		this.gameConfig = gameConfig;
		this.players = Arrays.asList(players);
		for (Player player : this.players) {
			gameScores.put(player, new GameScoreKeeper());			
		}
	}
	
	public GameConfig getGameConfig () {
		return this.gameConfig;
	}
	
	public List<Player> getPlayers () {
		return this.players;
	}
	
	public GameScoreKeeper getGameScore (Player player) {
		return gameScores.get(player);
	}

	public Object[][] check(Ball ball, Player player, boolean isSuccess) {
		gameScores.get(player).add(new AttemptResult(player, ball, isSuccess));

		Object[][] result = new Object[gameConfig.getMaxRounds()][players.size()];

		//GameScoreKeeper first = this.gameScores.get(firstPlayer);
		//GameScoreKeeper second = this.gameScores.get(secondPlayer);

		for (int i = 0; i < gameConfig.getMaxRounds(); i++) {
			
//			result[i] = new String[] { String.valueOf(i + 1),
//					first.getIndexed(i).getName(),
//					second.getIndexed(i).getName() };
		}

		return result;

	}

}
