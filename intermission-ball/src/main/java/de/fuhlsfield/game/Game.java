package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.rule.RuleChecker;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final int maxRounds;
	private final Map<Player, GameScoreKeeper> gameScores = new HashMap<Player, GameScoreKeeper>();

	public Game(GameConfig gameConfig, int maxRounds, Player... players) {
		this.gameConfig = gameConfig;
		this.maxRounds = maxRounds;
		this.players = Arrays.asList(players);
		for (Player player : this.players) {
			this.gameScores.put(player, new GameScoreKeeper());
		}
	}

	public GameConfig getGameConfig() {
		return this.gameConfig;
	}

	public int getMaxRounds() {
		return this.maxRounds;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public GameScoreKeeper getGameScore(Player player) {
		return this.gameScores.get(player);
	}

	public int getBallValue(Ball ball) {
		return this.gameConfig.getBallValueMapper().getValue(ball);
	}

	public void check(Ball ball, Player player, boolean isSuccess) {
		Attempt attempt = new Attempt(player, ball);
		RuleChecker ruleChecker = new RuleChecker();
		if (ruleChecker.isAttemptPossible(attempt, this)) {
			this.gameScores.get(player).add(
					new AttemptResult(player, ball, isSuccess));
		}
		System.out.println(player.getName()
				+ ": "
				+ this.gameConfig.getScoreCalculator().calculateScore(
						this.getGameScore(player)));
	}

}
