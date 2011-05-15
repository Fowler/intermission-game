package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleChecker {

	private final Game game;
	private final List<RuleCheck> ruleChecks;

	public RuleChecker(Game game, List<RuleCheck> ruleChecks) {
		this.game = game;
		this.ruleChecks = ruleChecks;
	}

	public boolean isAttemptAllowedPreCheck(Ball ball, Player player) {
		return isBallTakesPart(ball) && determineNextPlayer().equals(player)
				&& isAttemptLeft(determineNextPlayer(), calculateMaxAttempts());
	}

	public RuleCheckState determineRuleCheckState(Ball ball, Player player) {
		GameScoreKeeper gameScoreKeeper = this.game.getGameScoreKeeper(player);
		if (gameScoreKeeper.getIndexOfLastAttempt() < 0) {
			// assumption that each ball can be used in first attempt to
			// reduce runtime
			return RuleCheckState.ALLOWED;
		}
		List<List<Ball>> possibleBallsLeft = determinePossibleBallsLeft(Arrays.asList(ball), gameScoreKeeper);
		for (RuleCheck ruleCheck : this.ruleChecks) {
			possibleBallsLeft = ruleCheck.selectPossibleAttempts(possibleBallsLeft, gameScoreKeeper);
		}
		if (possibleBallsLeft.isEmpty()) {
			return RuleCheckState.NOT_ALLOWED;
		}
		if ((possibleBallsLeft.size() == 1) && (possibleBallsLeft.get(0).size() == 1)) {
			return RuleCheckState.CHECKOUT;
		}
		return RuleCheckState.ALLOWED;
	}

	public boolean isGameFinished() {
		for (Player player : this.game.getPlayers()) {
			if (isAttemptLeft(player, calculateMaxAttempts())) {
				return false;
			}
		}
		return true;
	}

	private List<List<Ball>> determinePossibleBallsLeft(List<Ball> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleBallsLeft = new ArrayList<List<Ball>>();
		resultPossibleBallsLeft.add(possibleBallsLeft);
		int numberOfAttempts = gameScoreKeeper.getIndexOfLastAttempt() + 1 + possibleBallsLeft.size();
		if ((numberOfAttempts < this.game.getMaxAttempts())
				&& (this.game.getScoreCalculator().preCalculateScore(gameScoreKeeper, possibleBallsLeft) < this.game
						.getTargetPoints())) {
			resultPossibleBallsLeft.remove(possibleBallsLeft);
			for (Ball ball : this.game.getBalls()) {
				if ((possibleBallsLeft.size() == 1)
						|| (ball.compareTo(possibleBallsLeft.get(possibleBallsLeft.size() - 1)) <= 0)) {
					List<Ball> newPossibleBallsLeft = new ArrayList<Ball>(possibleBallsLeft);
					newPossibleBallsLeft.add(ball);
					resultPossibleBallsLeft.addAll(determinePossibleBallsLeft(newPossibleBallsLeft, gameScoreKeeper));
				}
			}
		}
		return resultPossibleBallsLeft;
	}

	private Player determineNextPlayer() {
		Player nextPlayer = Player.NO_PLAYER;
		int minIndex = -1;
		for (Player player : this.game.getPlayers()) {
			GameScoreKeeper gameScore = this.game.getGameScoreKeeper(player);
			int index = gameScore.getIndexOfLastAttempt();
			if (index < 0) {
				return player;
			} else if ((minIndex < 0) || (index < minIndex)) {
				minIndex = index;
				nextPlayer = player;
			}
		}
		return nextPlayer;
	}

	private int calculateMaxAttempts() {
		for (Player player : this.game.getPlayers()) {
			GameScoreKeeper gameScoreKeeper = this.game.getGameScoreKeeper(player);
			if (this.game.getScoreCalculator().calculateScore(gameScoreKeeper) >= this.game.getTargetPoints()) {
				return gameScoreKeeper.getIndexOfLastAttempt() + 1;
			}
		}
		return this.game.getMaxAttempts();
	}

	private boolean isAttemptLeft(Player player, int maxAttempts) {
		return this.game.getGameScoreKeeper(player).getIndexOfLastAttempt() + 1 < maxAttempts;
	}

	private boolean isBallTakesPart(Ball ball) {
		return this.game.getBalls().contains(ball);
	}

}