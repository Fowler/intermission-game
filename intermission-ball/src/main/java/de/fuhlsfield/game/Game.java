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
	private final Map<Player, GameScoreKeeper> gameScores = new HashMap<Player, GameScoreKeeper>();

	public Game(GameConfig gameConfig, Player... players) {
		this.gameConfig = gameConfig;
		this.players = Arrays.asList(players);
		for (Player player : this.players) {
			this.gameScores.put(player, new GameScoreKeeper(this.gameConfig
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
		return this.gameScores.get(player);
	}
	
	public BallValue getBallValue (Ball ball) {
	 for (BallValue ballValue : this.gameConfig.getBallValues()) {
		 if (ballValue.getBall() == ball) {
			 return ballValue;
		 }
	 }
	 return null;
	}

	public void check(Ball ball, Player player, boolean isSuccess) {
		Attempt attempt = new Attempt(player, ball);
		RuleChecker ruleChecker = new RuleChecker();
		if (ruleChecker.isAttemptPossible(attempt, this)) {
			this.gameScores.get(player).add(new AttemptResult(player, ball, isSuccess));
		}
		System.out.println(player.getName() + ": " + this.gameConfig.getScoreCalculator().calculateScore(this, player));
	}

}
