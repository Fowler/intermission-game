package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.rule.PlayerSequenceDeterminer;
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
	private final PlayerSequenceDeterminer playerSequenceDeterminer;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers = new HashMap<Player, SeasonScoreKeeper>();
	private final Map<Player, Map<Ball, RuleCheckState>> ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();

	public Game(GameConfig gameConfig, int maxAttempts, int numberOfGames, Player... players) {
		this.gameConfig = gameConfig;
		this.maxAttempts = maxAttempts;
		this.numberOfGames = numberOfGames;
		this.players = Arrays.asList(players);
		this.ruleChecker = new RuleChecker(gameConfig, maxAttempts);
		this.playerSequenceDeterminer = new PlayerSequenceDeterminer(gameConfig, maxAttempts);
		for (Player player : this.players) {
			GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
			this.gameScoreKeepers.put(player, gameScoreKeeper);
			this.seasonScoreKeepers.put(player, new SeasonScoreKeeper());
			this.playerSequenceDeterminer.addPlayer(player, gameScoreKeeper);
			upateBallRuleCheckStates(player);
		}
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
		return this.gameConfig.getAllowedBalls();
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

	public void addAttempt(Player player, Attempt attempt) {
		if (isAttemptAllowed(player, attempt.getBall())) {
			this.gameScoreKeepers.get(player).addAttempt(attempt);
			upateBallRuleCheckStates(this.playerSequenceDeterminer.determinePreviousPlayer());
		}
	}

	public void undoLastAttempt() {
		if (isUndoLastAttemptPossible()) {
			this.gameScoreKeepers.get(this.playerSequenceDeterminer.determinePreviousPlayer()).undoLastAttempt();
			upateBallRuleCheckStates(this.playerSequenceDeterminer.determineNextPlayer());
		}
	}

	public boolean isUndoLastAttemptPossible() {
		return this.playerSequenceDeterminer.determinePreviousPlayer() != Player.NO_PLAYER;
	}

	public void finishGame() {
		if (!isSeasonFinished() && isGameFinished()) {
			for (Player player : this.players) {
				this.seasonScoreKeepers.get(player).addGameScoreKeeper(this.gameScoreKeepers.get(player));
				this.gameScoreKeepers.put(player, new GameScoreKeeper());
				upateBallRuleCheckStates(player);
			}
		}
	}

	public RuleCheckState getRuleCheckState(Player player, Ball ball) {
		return this.ballRuleCheckStates.get(player).get(ball);
	}

	public boolean isAttemptAllowed(Player player, Ball ball) {
		return !isSeasonFinished() && (this.playerSequenceDeterminer.determineNextPlayer() == player)
				&& this.ballRuleCheckStates.get(player).get(ball).isAllowed();
	}

	public boolean isGameFinished() {
		return this.playerSequenceDeterminer.isGameFinished();
	}

	private boolean isSeasonFinished() {
		return (this.seasonScoreKeepers.get(getPlayers().get(0)).getNumberOfGameScoreKeepers() >= this.numberOfGames);
	}

	private void upateBallRuleCheckStates(Player player) {
		this.ballRuleCheckStates.put(player, this.ruleChecker.determineRuleCheckStates(getGameScoreKeeper(player)));
	}

	public GameConfig getGameConfig() {
		return this.gameConfig;
	}

	public Map<Player, GameScoreKeeper> getGameScoreKeepers() {
		return this.gameScoreKeepers;
	}

	public Map<Player, SeasonScoreKeeper> getSeasonScoreKeepers() {
		return this.seasonScoreKeepers;
	}

}