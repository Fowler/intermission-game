package de.fuhlsfield.game.rule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class PlayerSequenceDeterminer {

	private final GameScoreCalculator gameScoreCalculator;
	private final List<Player> players = new LinkedList<Player>();
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final int targetPoints;
	private final int maxAttempts;

	public PlayerSequenceDeterminer(GameConfig gameConfig, int maxAttempts) {
		this.gameScoreCalculator = gameConfig.getScoreCalculator();
		this.targetPoints = gameConfig.getTargetPoints();
		this.maxAttempts = maxAttempts;
	}

	public void addPlayer(Player player, GameScoreKeeper gameScoreKeeper) {
		this.players.add(player);
		this.gameScoreKeepers.put(player, gameScoreKeeper);
	}

	public Player determineNextPlayer() {
		Player nextPlayer = Player.NO_PLAYER;
		if (!isGameFinished()) {
			int minIndex = -1;
			for (Player player : this.players) {
				GameScoreKeeper gameScore = this.gameScoreKeepers.get(player);
				int index = gameScore.getNumberOfAttempts() - 1;
				if (index < 0) {
					return player;
				} else if ((minIndex < 0) || (index < minIndex)) {
					minIndex = index;
					nextPlayer = player;
				}
			}
		}
		return nextPlayer;
	}

	public Player determinePreviousPlayer() {
		if (isGameStarted()) {
			int index = this.players.indexOf(determineNextPlayer()) - 1;
			if (index < 0) {
				index = this.players.size() - 1;
			}
			return this.players.get(index);
		}
		return Player.NO_PLAYER;
	}

	public boolean isGameFinished() {
		for (Player player : this.players) {
			if (isAttemptLeft(player, calculateMaxAttempts())) {
				return false;
			}
		}
		return true;
	}

	private boolean isAttemptLeft(Player player, int maxAttempts) {
		return this.gameScoreKeepers.get(player).getNumberOfAttempts() < maxAttempts;
	}

	private int calculateMaxAttempts() {
		for (Player player : this.players) {
			GameScoreKeeper gameScoreKeeper = this.gameScoreKeepers.get(player);
			if (this.gameScoreCalculator.calculateScore(gameScoreKeeper) >= this.targetPoints) {
				return gameScoreKeeper.getNumberOfAttempts();
			}
		}
		return this.maxAttempts;
	}

	private boolean isGameStarted() {
		for (Player player : this.players) {
			if (this.gameScoreKeepers.get(player).getNumberOfAttempts() > 0) {
				return true;
			}
		}
		return false;
	}
}