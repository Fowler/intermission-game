package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.rule.RuleCheckState;
import de.fuhlsfield.game.rule.RuleChecker;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final int maxAttempts;
	private final int numberOfGames;
	private final RuleChecker ruleChecker;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers = new HashMap<Player, SeasonScoreKeeper>();
	private Map<Player, Map<Ball, RuleCheckState>> ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();

	public Game(GameConfig gameConfig, int maxAttempts, int numberOfGames, Player... players) {
		this.gameConfig = gameConfig;
		this.maxAttempts = maxAttempts;
		this.numberOfGames = numberOfGames;
		this.players = Arrays.asList(players);
		this.ruleChecker = new RuleChecker(this, gameConfig.getRuleChecks());
		for (Player player : this.players) {
			this.gameScoreKeepers.put(player, new GameScoreKeeper(gameConfig.getScoreCalculator()));
			this.seasonScoreKeepers.put(player, new SeasonScoreKeeper());
		}
		upateBallRuleCheckStates();
	}

	public int getMaxAttempts() {
		return this.maxAttempts;
	}

	public int getNumberOfGames() {
		return this.numberOfGames;
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

	public SeasonScoreKeeper getSeasonScoreKeeper(Player player) {
		return this.seasonScoreKeepers.get(player);
	}

	public int getTargetPoints() {
		return this.gameConfig.getTargetPoints();
	}

	public int getBallValue(Ball ball) {
		return this.gameConfig.getBallValueMapper().getValue(ball);
	}

	public void addAttempt(Player player, Attempt attempt) {
		if (!isSeasonFinished() && isAttemptAllowed(player, attempt.getBall())) {
			this.gameScoreKeepers.get(player).addAttempt(attempt);
			upateBallRuleCheckStates();
		}
	}

	public void undoLastAttempt() {
		if (this.gameScoreKeepers.get(this.ruleChecker.determinePreviousPlayer()).undoLastAttempt()) {
			upateBallRuleCheckStates();
		}
	}

	public void finishGame() {
		if (!isSeasonFinished() && this.ruleChecker.isGameFinished()) {
			for (Player player : this.players) {
				this.seasonScoreKeepers.get(player).addGameScoreKeeper(this.gameScoreKeepers.get(player));
				this.gameScoreKeepers.put(player, new GameScoreKeeper(this.gameConfig.getScoreCalculator()));
			}
		}
	}

	public RuleCheckState getRuleCheckState(Player player, Ball ball) {
		return this.ballRuleCheckStates.get(player).get(ball);
	}

	public boolean isAttemptAllowed(Player player, Ball ball) {
		return this.ruleChecker.isAttemptAllowedPreCheck(ball, player)
				&& this.ruleChecker.determineRuleCheckState(ball, player).isAllowed();
	}

	private boolean isSeasonFinished() {
		return !getPlayers().isEmpty()
				&& (this.seasonScoreKeepers.get(getPlayers().get(0)).getNumberOfGameScores() >= this.numberOfGames);
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
	}

}