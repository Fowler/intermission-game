package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.rule.RuleCheckState;
import de.fuhlsfield.game.rule.RuleChecker;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.ScoreCalculator;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final int maxAttempts;
	private final RuleChecker ruleChecker;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private Map<Player, Map<Ball, RuleCheckState>> ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();

	public Game(GameConfig gameConfig, int maxRounds, Player... players) {
		this.gameConfig = gameConfig;
		this.maxAttempts = maxRounds;
		this.players = Arrays.asList(players);
		this.ruleChecker = new RuleChecker(this, gameConfig.getRuleChecks());
		for (Player player : this.players) {
			this.gameScoreKeepers.put(player, new GameScoreKeeper());
		}
		upateBallRuleCheckStates();
	}

	public int getMaxAttempts() {
		return this.maxAttempts;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public List<Ball> getBalls() {
		return this.gameConfig.getBallValueMapper().getBalls();
	}

	public GameScoreKeeper getGameScoreKeeper(Player player) {
		return this.gameScoreKeepers.get(player);
	}

	public ScoreCalculator getScoreCalculator() {
		return this.gameConfig.getScoreCalculator();
	}

	public int getTargetPoints() {
		return this.gameConfig.getTargetPoints();
	}

	public int getBallValue(Ball ball) {
		return this.gameConfig.getBallValueMapper().getValue(ball);
	}

	public void addAttempt(Player player, Attempt attempt) {
		if (isAttemptAllowed(player, attempt.getBall())) {
			this.gameScoreKeepers.get(player).addAttempt(attempt);
			upateBallRuleCheckStates();
		}
	}

	public void undoLastAttempt() {
		if (this.gameScoreKeepers.get(this.ruleChecker.determinePreviousPlayer()).undoLastAttempt()) {
			upateBallRuleCheckStates();
		}
	}

	public RuleCheckState getRuleCheckState(Player player, Ball ball) {
		return this.ballRuleCheckStates.get(player).get(ball);
	}

	public boolean isAttemptAllowed(Player player, Ball ball) {
		return this.ruleChecker.isAttemptAllowedPreCheck(ball, player)
				&& this.ruleChecker.determineRuleCheckState(ball, player).isAllowed();
	}

	private void upateBallRuleCheckStates() {
		this.ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();
		for (Player player : this.players) {
			this.ballRuleCheckStates.put(player, new HashMap<Ball, RuleCheckState>());
			for (Ball ball : getBalls()) {
				this.ballRuleCheckStates.get(player).put(ball,
						new RuleChecker(this, this.gameConfig.getRuleChecks()).determineRuleCheckState(ball, player));
			}
		}
		System.out.println(this.ballRuleCheckStates);
	}

}