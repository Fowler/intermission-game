package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleChecker {

	private final List<RuleCheck> ruleChecks;
	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;
	private final int maxAttempts;

	public RuleChecker(List<RuleCheck> ruleChecks, GameScoreCalculator gameScoreCalculator, int targetPoints,
			int maxAttempts) {
		this.ruleChecks = ruleChecks;
		this.gameScoreCalculator = gameScoreCalculator;
		this.targetPoints = targetPoints;
		this.maxAttempts = maxAttempts;

	}

	public RuleCheckState determineRuleCheckState(Ball ball, GameScoreKeeper gameScoreKeeper, List<Ball> allowedBalls) {
		int remainingPoints = this.targetPoints
				- this.gameScoreCalculator.calculateScore(gameScoreKeeper, Arrays.asList(ball));
		if (remainingPoints >= sumAllBallValues(allowedBalls)) {
			if (gameScoreKeeper.isBallSuccessfulPlayed(ball)) {
				return RuleCheckState.ALLOWED_AND_PLAYED;
			}
			return RuleCheckState.ALLOWED;
		}
		List<List<Ball>> possibleBallsLeft = determinePossibleBallsLeft(Arrays.asList(ball), gameScoreKeeper,
				allowedBalls);
		for (RuleCheck ruleCheck : this.ruleChecks) {
			possibleBallsLeft = ruleCheck.selectPossibleAttempts(possibleBallsLeft, gameScoreKeeper);
		}
		if (possibleBallsLeft.isEmpty()) {
			return RuleCheckState.NOT_ALLOWED;
		}
		if ((possibleBallsLeft.size() == 1) && (possibleBallsLeft.get(0).size() == 1)) {
			return RuleCheckState.CHECKOUT;
		}
		if (gameScoreKeeper.isBallSuccessfulPlayed(ball)) {
			return RuleCheckState.ALLOWED_AND_PLAYED;
		}
		return RuleCheckState.ALLOWED;
	}

	private List<List<Ball>> determinePossibleBallsLeft(List<Ball> possibleBallsLeft, GameScoreKeeper gameScoreKeeper,
			List<Ball> allowedBalls) {
		ArrayList<List<Ball>> resultPossibleBallsLeft = new ArrayList<List<Ball>>();
		resultPossibleBallsLeft.add(possibleBallsLeft);
		int numberOfAttempts = gameScoreKeeper.getNumberOfAttempts() + possibleBallsLeft.size();
		if ((numberOfAttempts < this.maxAttempts)
				&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, possibleBallsLeft) < this.targetPoints)) {
			resultPossibleBallsLeft.remove(possibleBallsLeft);
			for (Ball ball : allowedBalls) {
				if ((possibleBallsLeft.size() == 1)
						|| (ball.compareTo(possibleBallsLeft.get(possibleBallsLeft.size() - 1)) <= 0)) {
					List<Ball> newPossibleBallsLeft = new ArrayList<Ball>(possibleBallsLeft);
					newPossibleBallsLeft.add(ball);
					resultPossibleBallsLeft.addAll(determinePossibleBallsLeft(newPossibleBallsLeft, gameScoreKeeper,
							allowedBalls));
				}
			}
		}
		return resultPossibleBallsLeft;
	}

	private int sumAllBallValues(List<Ball> balls) {
		return this.gameScoreCalculator.calculateScore(balls);
	}

}