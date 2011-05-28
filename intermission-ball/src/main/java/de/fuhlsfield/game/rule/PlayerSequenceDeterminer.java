package de.fuhlsfield.game.rule;

import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class PlayerSequenceDeterminer {

	private final GameScoreCalculator gameScoreCalculator;
	private final List<Player> players;
	private final int targetPoints;
	private final int maxAttempts;

	public PlayerSequenceDeterminer(GameScoreCalculator gameScoreCalculator, int targetPoints, int maxAttempts,
			List<Player> players) {
		this.gameScoreCalculator = gameScoreCalculator;
		this.targetPoints = targetPoints;
		this.maxAttempts = maxAttempts;
		this.players = players;
	}

	public Player determineNextPlayer(Map<Player, GameScoreKeeper> gameScoreKeepers) {
		Player nextPlayer = Player.NO_PLAYER;
		if (!isGameFinished(gameScoreKeepers)) {
			int minIndex = -1;
			for (Player player : this.players) {
				GameScoreKeeper gameScore = gameScoreKeepers.get(player);
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

	public Player determinePreviousPlayer(Map<Player, GameScoreKeeper> gameScoreKeepers) {
		if (isGameStarted(gameScoreKeepers)) {
			int index = this.players.indexOf(determineNextPlayer(gameScoreKeepers)) - 1;
			if (index < 0) {
				index = this.players.size() - 1;
			}
			return this.players.get(index);
		}
		return Player.NO_PLAYER;
	}

	public boolean isGameFinished(Map<Player, GameScoreKeeper> gameScoreKeepers) {
		for (Player player : this.players) {
			if (isAttemptLeft(gameScoreKeepers.get(player), calculateMaxAttempts(gameScoreKeepers))) {
				return false;
			}
		}
		return true;
	}

	private boolean isAttemptLeft(GameScoreKeeper gameScoreKeeper, int maxAttempts) {
		return gameScoreKeeper.getNumberOfAttempts() < maxAttempts;
	}

	private int calculateMaxAttempts(Map<Player, GameScoreKeeper> gameScoreKeepers) {
		for (Player player : this.players) {
			GameScoreKeeper gameScoreKeeper = gameScoreKeepers.get(player);
			if (this.gameScoreCalculator.calculateScore(gameScoreKeeper) >= this.targetPoints) {
				return gameScoreKeeper.getNumberOfAttempts();
			}
		}
		return this.maxAttempts;
	}

	private boolean isGameStarted(Map<Player, GameScoreKeeper> gameScoreKeepers) {
		for (Player player : this.players) {
			if (gameScoreKeepers.get(player).getNumberOfAttempts() > 0) {
				return true;
			}
		}
		return false;
	}

}