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

	public Game(GameConfig gameConfig, Player... players) {
		this.gameConfig = gameConfig;
		this.players = Arrays.asList(players);
		for (Player player : this.players) {
			gameScores.put(player, new GameScoreKeeper(this.gameConfig
					.getMaxRounds()));
		}
	}

	public GameConfig getGameConfig() {
		return this.gameConfig;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public GameScoreKeeper getGameScore(Player player) {
		return gameScores.get(player);
	}

	public void check(Ball ball, Player player, boolean isSuccess) {
		gameScores.get(player).add(new AttemptResult(player, ball, isSuccess));

	}

}
