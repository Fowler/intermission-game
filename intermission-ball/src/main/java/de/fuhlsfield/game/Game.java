package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.rule.PlayerSequenceDeterminer;
import de.fuhlsfield.game.rule.RuleCheckState;
import de.fuhlsfield.game.rule.RuleCheckStateDeterminer;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final int maxAttempts;
	private final int numberOfGames;
	private final RuleCheckStateDeterminer ruleCheckStateDeterminer;
	private final PlayerSequenceDeterminer playerSequenceDeterminer;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers = new HashMap<Player, SeasonScoreKeeper>();
	private Map<Player, Map<Ball, RuleCheckState>> ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();

	public Game(GameConfig gameConfig, int maxAttempts, int numberOfGames, Player... players) {
		this.gameConfig = gameConfig;
		this.maxAttempts = maxAttempts;
		this.numberOfGames = numberOfGames;
		this.players = Arrays.asList(players);
		this.ruleCheckStateDeterminer = new RuleCheckStateDeterminer(gameConfig, maxAttempts);
		this.playerSequenceDeterminer = new PlayerSequenceDeterminer(gameConfig, maxAttempts);
		for (Player player : this.players) {
			GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
			this.gameScoreKeepers.put(player, gameScoreKeeper);
			this.seasonScoreKeepers.put(player, new SeasonScoreKeeper());
			this.playerSequenceDeterminer.addPlayer(player, gameScoreKeeper);
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
		if (isAttemptAllowed(player, attempt.getBall())) {
			this.gameScoreKeepers.get(player).addAttempt(attempt);
			upateBallRuleCheckStates();
		}
	}

	public void undoLastAttempt() {
		if (isUndoLastAttemptPossible()) {
			this.gameScoreKeepers.get(this.playerSequenceDeterminer.getPreviousPlayer()).undoLastAttempt();
			upateBallRuleCheckStates();
		}
	}

	public boolean isUndoLastAttemptPossible() {
		return this.gameScoreKeepers.get(this.playerSequenceDeterminer.getPreviousPlayer()).isUndoLastAttemptPossible();
	}

	public void finishGame() {
		if (!isSeasonFinished() && isGameFinished()) {
			for (Player player : this.players) {
				this.seasonScoreKeepers.get(player).addGameScoreKeeper(this.gameScoreKeepers.get(player));
				this.gameScoreKeepers.put(player, new GameScoreKeeper());
				upateBallRuleCheckStates();
			}
		}
	}

	public RuleCheckState getRuleCheckState(Player player, Ball ball) {
		return this.ballRuleCheckStates.get(player).get(ball);
	}

	public boolean isAttemptAllowed(Player player, Ball ball) {
		return !isSeasonFinished() && (this.playerSequenceDeterminer.getNextPlayer() != Player.NO_PLAYER)
				&& this.ballRuleCheckStates.get(player).get(ball).isAllowed();
	}

	public boolean isGameFinished() {
		return this.playerSequenceDeterminer.isGameFinished();
	}

	private boolean isSeasonFinished() {
		return !getPlayers().isEmpty()
				&& (this.seasonScoreKeepers.get(getPlayers().get(0)).getNumberOfGameScoreKeepers() >= this.numberOfGames);
	}

	private void upateBallRuleCheckStates() {
		this.ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();
		for (Player player : this.players) {
			this.ballRuleCheckStates.put(player, this.ruleCheckStateDeterminer
					.determineRuleCheckStates(getGameScoreKeeper(player)));
		}
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