package de.fuhlsfield.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.persistence.ScoreCsvExporter;
import de.fuhlsfield.game.persistence.ScoreCsvImporter;
import de.fuhlsfield.game.rule.PlayerSequenceDeterminer;
import de.fuhlsfield.game.rule.RuleCheckState;
import de.fuhlsfield.game.rule.RuleChecker;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreCalculator;
import de.fuhlsfield.game.score.SeasonScoreKeeper;
import de.fuhlsfield.game.score.StatisticKeeper;
import de.fuhlsfield.game.score.StatisticKeeperFactory;

public class Game {

	private final GameConfig gameConfig;
	private final List<Player> players;
	private final RuleChecker ruleChecker;
	private final PlayerSequenceDeterminer playerSequenceDeterminer;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers = new HashMap<Player, SeasonScoreKeeper>();
	private final Map<Player, Map<Ball, RuleCheckState>> ballRuleCheckStates = new HashMap<Player, Map<Ball, RuleCheckState>>();
	private final StatisticKeeperFactory statisticKeeperFactory = new StatisticKeeperFactory();
	private final Map<Player, StatisticKeeper> totalStatisticKeepers = new HashMap<Player, StatisticKeeper>();
	private final ScoreCsvExporter scoreCsvExporter;
	private final ScoreCsvImporter scoreCsvImporter;

	public Game(GameConfig gameConfig, List<Player> players) {
		this.gameConfig = gameConfig;
		this.players = players;
		this.ruleChecker = new RuleChecker(gameConfig);
		this.playerSequenceDeterminer = new PlayerSequenceDeterminer(gameConfig.getGameScoreCalculator(), gameConfig
				.getTargetPoints(), gameConfig.getMaxAttempts(), this.players);
		for (Player player : this.players) {
			this.gameScoreKeepers.put(player, new GameScoreKeeper());
			this.seasonScoreKeepers.put(player, new SeasonScoreKeeper());
			this.totalStatisticKeepers.put(player, this.statisticKeeperFactory.createStatisticKeeper());
			updateBallRuleCheckStates(player);
		}
		this.scoreCsvExporter = new ScoreCsvExporter(this.players, new SeasonScoreCalculator(gameConfig
				.getGameScoreCalculator(), gameConfig.getBonusPoints()), gameConfig);
		this.scoreCsvImporter = new ScoreCsvImporter(gameConfig);
	}

	public void reset () {
		for (Player player : this.players) {
			StatisticKeeper totalStatisticKeeper = this.totalStatisticKeepers.get(player);
			totalStatisticKeeper = this.statisticKeeperFactory.mergeStatisticKeeper(totalStatisticKeeper,
					this.statisticKeeperFactory.createStatisticKeeper(this.gameScoreKeepers.get(player)));
			totalStatisticKeeper = this.statisticKeeperFactory.mergeStatisticKeeper(totalStatisticKeeper,
					this.statisticKeeperFactory.createStatisticKeeper(this.seasonScoreKeepers.get(player)));
			this.totalStatisticKeepers.put(player, totalStatisticKeeper);
			this.gameScoreKeepers.put(player, new GameScoreKeeper());
			this.seasonScoreKeepers.put(player, new SeasonScoreKeeper());
			updateBallRuleCheckStates(player);
		}
	}

	public List<Player> getPlayers () {
		return this.players;
	}

	public int getMaxAttempts () {
		return this.gameConfig.getMaxAttempts();
	}

	public int getNumberOfGames () {
		return this.gameConfig.getNumberOfGames();
	}

	public int getBonusPoints () {
		return this.gameConfig.getBonusPoints();
	}

	public GameScoreCalculator getGameScoreCalculator () {
		return this.gameConfig.getGameScoreCalculator();
	}

	public List<Ball> getAllowedBalls () {
		return this.gameConfig.getAllowedBalls();
	}

	public GameScoreKeeper getGameScoreKeeper (Player player) {
		return this.gameScoreKeepers.get(player);
	}

	public SeasonScoreKeeper getSeasonScoreKeeper (Player player) {
		return this.seasonScoreKeepers.get(player);
	}

	public void addAttempt (Player player, Attempt attempt) {
		if (isAttemptAllowed(player, attempt.getBall())) {
			this.gameScoreKeepers.get(player).addAttempt(attempt);
			updateBallRuleCheckStates(this.playerSequenceDeterminer.determinePreviousPlayer(this.gameScoreKeepers));
		}
	}

	public boolean isUndoLastAttemptPossible () {
		return this.playerSequenceDeterminer.determinePreviousPlayer(this.gameScoreKeepers) != Player.NO_PLAYER;
	}

	public void undoLastAttempt () {
		if (isUndoLastAttemptPossible()) {
			this.gameScoreKeepers.get(this.playerSequenceDeterminer.determinePreviousPlayer(this.gameScoreKeepers))
					.undoLastAttempt();
			updateBallRuleCheckStates(this.playerSequenceDeterminer.determineNextPlayer(this.gameScoreKeepers));
		}
	}

	public boolean isAttemptAllowed (Player player, Ball ball) {
		return !isSeasonFinished()
				&& (this.playerSequenceDeterminer.determineNextPlayer(this.gameScoreKeepers) == player)
				&& this.ballRuleCheckStates.get(player).get(ball).isAllowed();
	}

	public boolean isGameFinished () {
		return this.playerSequenceDeterminer.isGameFinished(this.gameScoreKeepers);
	}

	public void finishGame () {
		finishGame(false);
	}

	public void finishGameAndExport () {
		finishGame(true);
	}

	public RuleCheckState getRuleCheckState (Player player, Ball ball) {
		return this.ballRuleCheckStates.get(player).get(ball);
	}

	public void updateBallRuleCheckStates (Player player) {
		this.ballRuleCheckStates.put(player, this.ruleChecker.determineRuleCheckStates(getGameScoreKeeper(player)));
	}

	private boolean isSeasonFinished () {
		return (this.seasonScoreKeepers.get(getPlayers().get(0)).getNumberOfGameScoreKeepers() >= this.gameConfig
				.getNumberOfGames());
	}

	public Map<Player, GameScoreKeeper> getGameScoreKeepers () {
		return this.gameScoreKeepers;
	}

	public Map<Player, SeasonScoreKeeper> getSeasonScoreKeepers () {
		return this.seasonScoreKeepers;
	}

	public void exportScore () {
		this.scoreCsvExporter.exportScoreAndConfig(this.seasonScoreKeepers, this.gameScoreKeepers,
				getTotalStatisticKeepers());
	}

	public Game importScore () {
		return this.scoreCsvImporter.importScoreAndConfig();
	}

	public StatisticKeeper getSeasonStatisticKeeper (Player player) {
		StatisticKeeper seasonsStatisticKeeper = this.statisticKeeperFactory
				.createStatisticKeeper(this.seasonScoreKeepers.get(player));
		StatisticKeeper gameStatisticKeeper = this.statisticKeeperFactory.createStatisticKeeper(this.gameScoreKeepers
				.get(player));
		return this.statisticKeeperFactory.mergeStatisticKeeper(seasonsStatisticKeeper, gameStatisticKeeper);
	}

	public StatisticKeeper getTotalStatisticKeeper (Player player) {
		return this.statisticKeeperFactory.mergeStatisticKeeper(getSeasonStatisticKeeper(player),
				this.totalStatisticKeepers.get(player));
	}

	public void setTotalStatisticKeepers (Map<Player, StatisticKeeper> statisticKeepers) {
		for (Player player : statisticKeepers.keySet()) {
			this.totalStatisticKeepers.put(player, this.statisticKeeperFactory.removeStatisticKeeper(statisticKeepers
					.get(player), getSeasonStatisticKeeper(player)));
		}
	}

	private void finishGame (boolean export) {
		if (!isSeasonFinished() && isGameFinished()) {
			for (Player player : this.players) {
				this.seasonScoreKeepers.get(player).addGameScoreKeeper(this.gameScoreKeepers.get(player));
				this.gameScoreKeepers.put(player, new GameScoreKeeper());
				updateBallRuleCheckStates(player);
			}
			if (export) {
				exportScore();
			}
		}
	}

	private Map<Player, StatisticKeeper> getTotalStatisticKeepers () {
		HashMap<Player, StatisticKeeper> statisticKeepers = new HashMap<Player, StatisticKeeper>();
		for (Player player : this.players) {
			statisticKeepers.put(player, getTotalStatisticKeeper(player));
		}
		return statisticKeepers;
	}

}